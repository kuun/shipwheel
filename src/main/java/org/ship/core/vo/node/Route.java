package org.ship.core.vo.node;

/**
 * Created by wx on 2017/4/30.
 */
public class Route {
    private int id;
    private String subnet;
    private String mask;
    private int nic_id;
    private int node_id;
    private String gateway;
    private String nicName;

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

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    public String getNicName() {
        return nicName;
    }

    public void setNicName(String nicName) {
        this.nicName = nicName;
    }

    @Override
    public String toString() {
        return "Route{" +
                "id=" + id +
                ", subnet='" + subnet + '\'' +
                ", mask='" + mask + '\'' +
                ", nic_id=" + nic_id +
                ", node_id=" + node_id +
                ", gateway='" + gateway + '\'' +
                ", nicName='" + nicName + '\'' +
                '}';
    }
}
