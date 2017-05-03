package org.ship.core.vo.node;

/**
 * Created by wx on 2017/5/1.
 */
public class ConnRule {
    private int id;
    private RuleType rule_type;
    private int direct;
    private IpAddress listen_addr;
    private int listen_port;
    private String dst_addr;
    private int dst_port;
    private IpAddress send_addr;
    private boolean status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RuleType getRule_type() {
        return rule_type;
    }

    public void setRule_type(RuleType rule_type) {
        this.rule_type = rule_type;
    }

    public int getDirect() {
        return direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }

    public IpAddress getListen_addr() {
        return listen_addr;
    }

    public void setListen_addr(IpAddress listen_addr) {
        this.listen_addr = listen_addr;
    }

    public int getListen_port() {
        return listen_port;
    }

    public void setListen_port(int listen_port) {
        this.listen_port = listen_port;
    }

    public String getDst_addr() {
        return dst_addr;
    }

    public void setDst_addr(String dst_addr) {
        this.dst_addr = dst_addr;
    }

    public int getDst_port() {
        return dst_port;
    }

    public void setDst_port(int dst_port) {
        this.dst_port = dst_port;
    }

    public IpAddress getSend_addr() {
        return send_addr;
    }

    public void setSend_addr(IpAddress send_addr) {
        this.send_addr = send_addr;
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
                ", rule_type=" + rule_type +
                ", direct=" + direct +
                ", listen_addr=" + listen_addr +
                ", listen_port=" + listen_port +
                ", dst_addr='" + dst_addr + '\'' +
                ", dst_port=" + dst_port +
                ", send_addr=" + send_addr +
                ", status=" + status +
                '}';
    }
}
