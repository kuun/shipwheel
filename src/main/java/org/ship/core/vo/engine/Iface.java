package org.ship.core.vo.engine;

/**
 * Created by wx on 2017/4/29.
 */
public class Iface {
    private int id;
    private String name;
    private int engineId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEngineId() {
        return engineId;
    }

    public void setEngineId(int engineId) {
        this.engineId = engineId;
    }

    @Override
    public String toString() {
        return "Iface{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", engineId=" + engineId +
                '}';
    }
}
