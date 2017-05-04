package org.ship.core.vo.node;

/**
 * Created by wx on 2017/4/30.
 */
public class Route {
    private int id;
    private String subnet;
    private String mask;
    private int iface_id;
    private int node_id;
    private String gateway;
    private String ifaceName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubnet() {
        return subnet;
    }

    public void setSubnet(String subnet) {
        this.subnet = subnet;
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

    public int getNode_id() {
        return node_id;
    }

    public void setNode_id(int node_id) {
        this.node_id = node_id;
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    public String getIfaceName() {
        return ifaceName;
    }

    public void setIfaceName(String ifaceName) {
        this.ifaceName = ifaceName;
    }

    @Override
    public String toString() {
        return "Route{" +
                "id=" + id +
                ", subnet='" + subnet + '\'' +
                ", mask='" + mask + '\'' +
                ", iface_id=" + iface_id +
                ", node_id=" + node_id +
                ", gateway='" + gateway + '\'' +
                ", ifaceName='" + ifaceName + '\'' +
                '}';
    }
}
