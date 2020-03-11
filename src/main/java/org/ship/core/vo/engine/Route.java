package org.ship.core.vo.engine;

/**
 * Created by wx on 2017/4/30.
 */
public class Route {
    private int id;
    private String dstNet;
    private String dstMask;
    private int ifaceId;
    private int engineId;
    private String gateway;
    private String ifaceName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDstNet() {
        return dstNet;
    }

    public void setDstNet(String dstNet) {
        this.dstNet = dstNet;
    }

    public String getDstMask() {
        return dstMask;
    }

    public void setDstMask(String dstMask) {
        this.dstMask = dstMask;
    }

    public int getIfaceId() {
        return ifaceId;
    }

    public void setIfaceId(int ifaceId) {
        this.ifaceId = ifaceId;
    }

    public int getEngineId() {
        return engineId;
    }

    public Route setEngineId(int engineId) {
        this.engineId = engineId;
        return this;
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
                ", dstNet='" + dstNet + '\'' +
                ", dstMask='" + dstMask + '\'' +
                ", ifaceId=" + ifaceId +
                ", engineId=" + engineId +
                ", gateway='" + gateway + '\'' +
                ", ifaceName='" + ifaceName + '\'' +
                '}';
    }
}
