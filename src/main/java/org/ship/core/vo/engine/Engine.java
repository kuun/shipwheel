package org.ship.core.vo.engine;

/**
 * Created by wx on 2017/4/30.
 */
public class Engine {
    private int id;
    private String ip;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public String toString() {
        return "Engine{" +
                "id=" + id +
                ", ip='" + ip + '\'' +
                '}';
    }
}
