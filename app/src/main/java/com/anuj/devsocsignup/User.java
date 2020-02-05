package com.anuj.devsocsignup;

public class User {
    private String Name;
    private Double PhNo;
    private String RegNo;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Double getPhNo() {
        return PhNo;
    }

    public void setPhNo(Double phNo) {
        PhNo = phNo;
    }

    public String getRegNo() {
        return RegNo;
    }

    public void setRegNo(String regNo) {
        RegNo = regNo;
    }

    public User(String name, Double phNo, String regNo) {
        Name = name;
        PhNo = phNo;
        RegNo = regNo;
    }
}
