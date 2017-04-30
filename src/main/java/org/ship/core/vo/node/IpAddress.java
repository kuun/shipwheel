package org.ship.core.vo.node;

/**
 * Created by wx on 2017/4/30.
 */
public class IpAddress {
    private int id;
    private int nic_id;
    private int node_id;
    private String ip;
    private String mask;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNic_id() {
        return nic_id;
    }

    public void setNic_id(int nic_id) {
        this.nic_id = nic_id;
    }

    public int getNode_id() {
        return node_id;
    }

    public void setNode_id(int node_id) {
        this.node_id = node_id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMask() {
        return mask;
    }

    public void setMask(String mask) {
        this.mask = mask;
    }

    @Override
    public String toString() {
        return "IpAddress{" +
                "id=" + id +
                ", nic_id=" + nic_id +
                ", ip='" + ip + '\'' +
                ", mask='" + mask + '\'' +
                '}';
    }
}
