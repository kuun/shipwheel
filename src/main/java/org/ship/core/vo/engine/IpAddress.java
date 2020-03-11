package org.ship.core.vo.engine;

/**
 * Created by wx on 2017/4/30.
 */
public class IpAddress {
    private int id;
    private int ifaceId;
    private int engineId;
    private String ip;
    private String mask;
    private String ifaceName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIfaceId() {
        return ifaceId;
    }

    public int getEngineId() {
        return engineId;
    }

    public IpAddress setEngineId(int engineId) {
        this.engineId = engineId;
        return this;
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
                ", ifaceId=" + ifaceId +
                ", engineId=" + engineId +
                ", ip='" + ip + '\'' +
                ", mask='" + mask + '\'' +
                ", ifaceName='" + ifaceName + '\'' +
                '}';
    }
}
