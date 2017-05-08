package org.ship.core.vo.node;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.apache.commons.lang3.exception.ExceptionUtils;
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

    public NodeClient(String host, int port) {
        this(ManagedChannelBuilder.forAddress(host, port).usePlaintext(true));
    }

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

}
