package com.cbo.core.response;

public class RecentUsers {

    String name;
    String lastlogin;

    String role;

    public RecentUsers() {
    }

    public RecentUsers(String name, String lastlogin, String role) {
        this.name = name;
        this.lastlogin = lastlogin;
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastlogin() {
        return lastlogin;
    }

    public void setLastlogin(String lastlogin) {
        this.lastlogin = lastlogin;
    }
}
