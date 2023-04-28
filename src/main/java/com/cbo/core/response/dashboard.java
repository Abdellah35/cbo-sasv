package com.cbo.core.response;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class dashboard {

    List<Integer> newUsers;
    Integer allUsers;
    Integer activeAuth;
    Integer allAuth;
    Set<RecentUsers> recentUsers;

    ByRole roleVisit;
    Integer todayLogin;
    Integer pageViewToday;
    Integer femaleUsers;
    Integer allEmployee;

    Integer allDivision;



    public dashboard() {
    }

    public dashboard(ByRole roleVisit, List<Integer> newUsers, Integer allUsers, Integer activeAuth, Integer allAuth, Set<RecentUsers> recentUsers, Integer todayLogin, Integer pageViewToday, Integer femaleUsers, Integer allEmployee, Integer allDivision) {
        this.newUsers = newUsers;
        this.allUsers = allUsers;
        this.activeAuth = activeAuth;
        this.allAuth = allAuth;
        this.recentUsers = recentUsers;
        this.todayLogin = todayLogin;
        this.pageViewToday = pageViewToday;
        this.femaleUsers = femaleUsers;
        this.allEmployee = allEmployee;
        this.allDivision = allDivision;
        this.roleVisit = roleVisit;
    }

    public ByRole getRoleVisit() {
        return roleVisit;
    }

    public void setRoleVisit(ByRole roleVisit) {
        this.roleVisit = roleVisit;
    }

    public void setNewUsers(List<Integer> newUsers) {
        this.newUsers = newUsers;
    }

    public List<Integer> getNewUsers() {
        return newUsers;
    }

    public Integer getAllDivision() {
        return allDivision;
    }

    public void setAllDivision(Integer allDivision) {
        this.allDivision = allDivision;
    }

    public Integer getAllEmployee() {
        return allEmployee;
    }

    public void setAllEmployee(Integer allEmployee) {
        this.allEmployee = allEmployee;
    }

    public Integer getAllUsers() {
        return allUsers;
    }

    public void setAllUsers(Integer allUsers) {
        this.allUsers = allUsers;
    }

    public Integer getActiveAuth() {
        return activeAuth;
    }

    public void setActiveAuth(Integer activeAuth) {
        this.activeAuth = activeAuth;
    }

    public Integer getAllAuth() {
        return allAuth;
    }

    public void setAllAuth(Integer allAuth) {
        this.allAuth = allAuth;
    }

    public Set<RecentUsers> getRecentUsers() {
        return recentUsers;
    }

    public void setRecentUsers(Set<RecentUsers> recentUsers) {
        this.recentUsers = recentUsers;
    }

    public Integer getTodayLogin() {
        return todayLogin;
    }

    public void setTodayLogin(Integer todayLogin) {
        this.todayLogin = todayLogin;
    }

    public Integer getPageViewToday() {
        return pageViewToday;
    }

    public void setPageViewToday(Integer pageViewToday) {
        this.pageViewToday = pageViewToday;
    }

    public Integer getFemaleUsers() {
        return femaleUsers;
    }

    public void setFemaleUsers(Integer femaleUsers) {
        this.femaleUsers = femaleUsers;
    }
}
