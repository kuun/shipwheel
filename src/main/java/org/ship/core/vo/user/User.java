package org.ship.core.vo.user;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by wx on 2017/4/29.
 */
public class User {
    private int id;
    private String name;
    @JsonIgnore
    private String password;
    private boolean is_admin;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isIs_admin() {
        return is_admin;
    }

    public void setIs_admin(boolean isAdmin) {
        this.is_admin = isAdmin;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", is_admin=" + is_admin +
                '}';
    }
}
