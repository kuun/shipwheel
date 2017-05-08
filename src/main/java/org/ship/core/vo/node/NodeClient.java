package org.ship.core.vo.node;

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
public class NodeClient {
    private static final Logger log = LoggerFactory.getLogger(NodeClient.class);

    private final ManagedChannel channel;
    private DatashipGrpc.DatashipBlockingStub blockingStub;

    /**
     * Construct client connecting to HelloWorld server at {@code host:port}.
     * */
    public NodeClient(String host, int port) {
        this(ManagedChannelBuilder.forAddress(host, port)
                // Channels are secure by default (via SSL/TLS). For the example we disable TLS to avoid
                // needing certificates.
                .usePlaintext(true));
    }

    /**
     * Construct client for accessing RouteGuide server using the existing channel.
     */
    NodeClient(ManagedChannelBuilder<?> channelBuilder) {
        channel = channelBuilder.build();
        blockingStub = DatashipGrpc.newBlockingStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public Rpc.OpResult addRule(ConnRule rule) {
        Rpc.PbRule pbRule = Rpc.PbRule.newBuilder().setId(rule.getId())
                .setType(Rpc.RuleType.forNumber(rule.getRule_type().getValue()))
                .setListenAddr(rule.getListen_addr().getIp())
                .setListenPort(rule.getListen_port())
                .setDstAddr(rule.getDst_addr())
                .setDstPort(rule.getDst_port())
                .setSendAddr(rule.getSend_addr().getIp())
                .build();
        return blockingStub.addRule(pbRule);
    }

    public Rpc.OpResult delRule(ConnRule rule) {
        Rpc.PbRule pbRule = Rpc.PbRule.newBuilder().setId(rule.getId())
                .setType(Rpc.RuleType.forNumber(rule.getRule_type().getValue()))
                .setListenAddr(rule.getListen_addr().getIp())
                .setListenPort(rule.getListen_port())
                .setDstAddr(rule.getDst_addr())
                .setDstPort(rule.getDst_port())
                .setSendAddr(rule.getSend_addr().getIp())
                .build();
        return blockingStub.delRule(pbRule);
    }

    public Rpc.OpResult addAddr(IpAddress ipAddress) {
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
        int dst_mask = Utils.shiftMask(route.getDst_mask());
        Rpc.PbRoute pbRoute = Rpc.PbRoute.newBuilder()
                .setDstNet(route.getDst_net())
                .setDstMask(dst_mask)
                .setIface(route.getIfaceName())
                .setGateway(route.getGateway())
                .build();
        return blockingStub.addRoute(pbRoute);
    }

    public Rpc.OpResult modRoute(Route oldRoute, Route newRoute) {
        int old_dst_mask = Utils.shiftMask(oldRoute.getDst_mask());
        int new_dst_mask = Utils.shiftMask(newRoute.getDst_mask());
        Rpc.PbRoute old_pb_route = Rpc.PbRoute.newBuilder()
                .setDstNet(oldRoute.getDst_net())
                .setDstMask(old_dst_mask)
                .setIface(oldRoute.getIfaceName())
                .setGateway(oldRoute.getGateway())
                .build();
        Rpc.PbRoute new_pb_route = Rpc.PbRoute.newBuilder()
                .setDstNet(newRoute.getDst_net())
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
        int dst_mask = Utils.shiftMask(route.getDst_mask());
        Rpc.PbRoute pbRoute = Rpc.PbRoute.newBuilder()
                .setDstNet(route.getDst_net())
                .setDstMask(dst_mask)
                .setIface(route.getIfaceName())
                .setGateway(route.getGateway())
                .build();
        return blockingStub.delRoute(pbRoute);
    }
}
