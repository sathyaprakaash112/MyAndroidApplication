package com.example.attendenceapplication;

public class UserHelperClass {
    String staffid,email,collegeName,password;

    public UserHelperClass() {

    }

    public UserHelperClass(String staffid, String email, String collegeName, String password) {
        this.staffid = staffid;
        this.email = email;
        this.collegeName = collegeName;
        this.password = password;
    }

    public String getStaffid() {
        return staffid;
    }

    public void setStaffid(String staffid) {
        this.staffid = staffid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
