package org.ship.core.vo.engine;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.dataship.rpc.DatashipGrpc;
import org.dataship.rpc.Rpc;
import org.ship.core.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Created by wx on 2017/5/8.
 */
public class EngineClient {
    private static final Logger log = LoggerFactory.getLogger(EngineClient.class);

    private final ManagedChannel channel;
    private final DatashipGrpc.DatashipBlockingStub blockingStub;

    /**
     * Construct client connecting to HelloWorld server at {@code host:port}.
     * */
    public EngineClient(String host, int port) {
        this(ManagedChannelBuilder.forAddress(host, port)
                // Channels are secure by default (via SSL/TLS). For the example we disable TLS to avoid
                // needing certificates.
                .usePlaintext());
    }

    /**
     * Construct client for accessing RouteGuide server using the existing channel.
     */
    EngineClient(ManagedChannelBuilder<?> channelBuilder) {
        channel = channelBuilder.build();
        blockingStub = DatashipGrpc.newBlockingStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public Rpc.OpResult addRule(ConnRule rule) {
        Rpc.PbRule pbRule = Rpc.PbRule.newBuilder().setId(rule.getId())
                .setType(Rpc.RuleType.forNumber(rule.getRuleType().getValue()))
                .setListenAddr(rule.getListenAddr().getIp())
                .setListenPort(rule.getListenPort())
                .setDstAddr(rule.getDstAddr())
                .setDstPort(rule.getDstPort())
                .setSendAddr(rule.getSendAddr().getIp())
                .build();
        return blockingStub.addRule(pbRule);
    }

    public Rpc.OpResult delRule(ConnRule rule) {
        Rpc.PbRule pbRule = Rpc.PbRule.newBuilder().setId(rule.getId())
                .setType(Rpc.RuleType.forNumber(rule.getRuleType().getValue()))
                .setListenAddr(rule.getListenAddr().getIp())
                .setListenPort(rule.getListenPort())
                .setDstAddr(rule.getDstAddr())
                .setDstPort(rule.getDstPort())
                .setSendAddr(rule.getSendAddr().getIp())
                .build();
        return blockingStub.delRule(pbRule);
    }

    public Rpc.OpResult addAddr(IpAddress ipAddress) {
        log.debug("ipaddr:{}", ipAddress);
        int mask = Utils.shiftMask(ipAddress.getMask());
        Rpc.PbAddr pbAddr = Rpc.PbAddr.newBuilder().setIface(ipAddress.getIfaceName())
                .setIp(ipAddress.getIp())
                .setMask(mask)
                .build();
        return blockingStub.addAddr(pbAddr);
    }

    public Rpc.OpResult modAddr(IpAddress oldAddr, IpAddress newAddr) {
        int old_mask = Utils.shiftMask(oldAddr.getMask());
        int new_mask = Utils.shiftMask(newAddr.getMask());
        Rpc.PbAddr odl_addr = Rpc.PbAddr.newBuilder()
                .setIface(oldAddr.getIfaceName())
                .setIp(oldAddr.getIp())
                .setMask(old_mask)
                .build();
        Rpc.PbAddr new_addr = Rpc.PbAddr.newBuilder()
                .setIface(newAddr.getIfaceName())
                .setIp(newAddr.getIp())
                .setMask(new_mask)
                .build();
        Rpc.PbAddrMod  pbAddrMod = Rpc.PbAddrMod.newBuilder()
                .setOld(odl_addr)
                .setNew(new_addr)
                .build();
        return blockingStub.modAddr(pbAddrMod);
    }

    public Rpc.OpResult delAddr(IpAddress ipAddress) {
        int mask = Utils.shiftMask(ipAddress.getMask());
        Rpc.PbAddr pbAddr = Rpc.PbAddr.newBuilder()
                .setIface(ipAddress.getIfaceName())
                .setIp(ipAddress.getIp())
                .setMask(mask)
                .build();
        return blockingStub.delAddr(pbAddr);
    }

    public Rpc.OpResult addRoute(Route route) {
        int dst_mask = Utils.shiftMask(route.getDstMask());
        Rpc.PbRoute pbRoute = Rpc.PbRoute.newBuilder()
                .setDstNet(route.getDstNet())
                .setDstMask(dst_mask)
                .setIface(route.getIfaceName())
                .setGateway(route.getGateway())
                .build();
        return blockingStub.addRoute(pbRoute);
    }

    public Rpc.OpResult modRoute(Route oldRoute, Route newRoute) {
        int old_dst_mask = Utils.shiftMask(oldRoute.getDstMask());
        int new_dst_mask = Utils.shiftMask(newRoute.getDstMask());
        Rpc.PbRoute old_pb_route = Rpc.PbRoute.newBuilder()
                .setDstNet(oldRoute.getDstNet())
                .setDstMask(old_dst_mask)
                .setIface(oldRoute.getIfaceName())
                .setGateway(oldRoute.getGateway())
                .build();
        Rpc.PbRoute new_pb_route = Rpc.PbRoute.newBuilder()
                .setDstNet(newRoute.getDstNet())
                .setDstMask(new_dst_mask)
                .setIface(newRoute.getIfaceName())
                .setGateway(newRoute.getGateway())
                .build();
        Rpc.PbRouteMod pbRouteMod = Rpc.PbRouteMod.newBuilder()
                .setOld(old_pb_route)
                .setNew(new_pb_route)
                .build();
        return blockingStub.modRoute(pbRouteMod);
    }

    public Rpc.OpResult delRoute(Route route) {
        int dst_mask = Utils.shiftMask(route.getDstMask());
        Rpc.PbRoute pbRoute = Rpc.PbRoute.newBuilder()
                .setDstNet(route.getDstNet())
                .setDstMask(dst_mask)
                .setIface(route.getIfaceName())
                .setGateway(route.getGateway())
                .build();
        return blockingStub.delRoute(pbRoute);
    }
}
