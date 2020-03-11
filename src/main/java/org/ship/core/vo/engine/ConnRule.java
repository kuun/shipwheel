package org.ship.core.vo.engine;

/**
 * Created by wx on 2017/5/1.
 */
public class ConnRule {
    private int id;
    private RuleType ruleType;
    private int direct;
    private IpAddress listenAddr;
    private int listenPort;
    private String dstAddr;
    private int dstPort;
    private IpAddress sendAddr;
    private boolean status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RuleType getRuleType() {
        return ruleType;
    }

    public void setRuleType(RuleType ruleType) {
        this.ruleType = ruleType;
    }

    public int getDirect() {
        return direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }

    public IpAddress getListenAddr() {
        return listenAddr;
    }

    public void setListenAddr(IpAddress listenAddr) {
        this.listenAddr = listenAddr;
    }

    public int getListenPort() {
        return listenPort;
    }

    public void setListenPort(int listenPort) {
        this.listenPort = listenPort;
    }

    public String getDstAddr() {
        return dstAddr;
    }

    public void setDstAddr(String dstAddr) {
        this.dstAddr = dstAddr;
    }

    public int getDstPort() {
        return dstPort;
    }

    public void setDstPort(int dstPort) {
        this.dstPort = dstPort;
    }

    public IpAddress getSendAddr() {
        return sendAddr;
    }

    public void setSendAddr(IpAddress sendAddr) {
        this.sendAddr = sendAddr;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ConnRule{" +
                "id=" + id +
                ", ruleType=" + ruleType +
                ", direct=" + direct +
                ", listenAddr=" + listenAddr +
                ", listenPort=" + listenPort +
                ", dstAddr='" + dstAddr + '\'' +
                ", dstPort=" + dstPort +
                ", sendAddr=" + sendAddr +
                ", status=" + status +
                '}';
    }
}
