package org.ship.core.vo.node;

/**
 * Created by wx on 2017/4/30.
 */
public class IpAddress {
    private int id;
    private int iface_id;
    private int node_id;
    private String ip;
    private String mask;
    private String ifaceName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNic_id() {
        return iface_id;
    }

    public void setNic_id(int nic_id) {
        this.iface_id = nic_id;
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

    public int getIface_id() {
        return iface_id;
    }

    public void setIface_id(int iface_id) {
        this.iface_id = iface_id;
    }

    public String getIfaceName() {
        return ifaceName;
    }

    public void setIfaceName(String ifaceName) {
        this.ifaceName = ifaceName;
    }

    @Override
    public String toString() {
        return "IpAddress{" +
                "id=" + id +
                ", iface_id=" + iface_id +
                ", node_id=" + node_id +
                ", ip='" + ip + '\'' +
                ", mask='" + mask + '\'' +
                ", ifaceName='" + ifaceName + '\'' +
                '}';
    }
}
