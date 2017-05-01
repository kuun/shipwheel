package org.ship.core.vo.node;

/**
 * Created by wx on 2017/5/1.
 */
public class ConnRule {
    private int id;
    private ConnType conn_type;
    private int direction;
    private int listen_ip_id;
    private int listen_port;
    private String dst_ip;
    private int dst_port;
    private int conn_ip_id;
    private boolean status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ConnType getConn_type() {
        return conn_type;
    }

    public void setConn_type(ConnType conn_type) {
        this.conn_type = conn_type;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getListen_ip_id() {
        return listen_ip_id;
    }

    public void setListen_ip_id(int listen_ip_id) {
        this.listen_ip_id = listen_ip_id;
    }

    public int getListen_port() {
        return listen_port;
    }

    public void setListen_port(int listen_port) {
        this.listen_port = listen_port;
    }

    public String getDst_ip() {
        return dst_ip;
    }

    public void setDst_ip(String dst_ip) {
        this.dst_ip = dst_ip;
    }

    public int getDst_port() {
        return dst_port;
    }

    public void setDst_port(int dst_port) {
        this.dst_port = dst_port;
    }

    public int getConn_ip_id() {
        return conn_ip_id;
    }

    public void setConn_ip_id(int conn_ip_id) {
        this.conn_ip_id = conn_ip_id;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ConnRule{" +
                "id=" + id +
                ", conn_type=" + conn_type +
                ", direction=" + direction +
                ", listen_ip_id=" + listen_ip_id +
                ", listen_port=" + listen_port +
                ", dst_ip='" + dst_ip + '\'' +
                ", dst_port=" + dst_port +
                ", conn_ip_id=" + conn_ip_id +
                ", status=" + status +
                '}';
    }
}
