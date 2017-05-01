package org.ship.core.vo.sys;

/**
 * Created by wx on 2017/5/1.
 */
public class ManAddr {
    private int id;
    private String nic_name;
    private String ip;
    private String mask;
    private String gateway;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNic_name() {
        return nic_name;
    }

    public void setNic_name(String nic_name) {
        this.nic_name = nic_name;
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

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    @Override
    public String toString() {
        return "ManAddr{" +
                "id=" + id +
                ", nic_name='" + nic_name + '\'' +
                ", ip='" + ip + '\'' +
                ", mask='" + mask + '\'' +
                ", gateway='" + gateway + '\'' +
                '}';
    }
}
