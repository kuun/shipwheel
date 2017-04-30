package org.ship.core.vo.node;

/**
 * Created by wx on 2017/4/29.
 */
public class Nic {
    private int id;
    private String name;
    private int node_id;

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

    public int getNode_id() {
        return node_id;
    }

    public void setNode_id(int node_id) {
        this.node_id = node_id;
    }

    @Override
    public String toString() {
        return "Nic{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", node_id=" + node_id +
                '}';
    }
}
