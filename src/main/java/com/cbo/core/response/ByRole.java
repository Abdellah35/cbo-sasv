package com.cbo.core.response;

public class ByRole {

    int admin;

    int director;

    int nuser;

    public ByRole() {
    }

    public ByRole(int admin, int director, int nuser) {
        this.admin = admin;
        this.director = director;
        this.nuser = nuser;
    }

    public int getAdmin() {
        return admin;
    }

    public void setAdmin(int admin) {
        this.admin = admin;
    }

    public int getDirector() {
        return director;
    }

    public void setDirector(int director) {
        this.director = director;
    }

    public int getNuser() {
        return nuser;
    }

    public void setNuser(int nuser) {
        this.nuser = nuser;
    }
}
