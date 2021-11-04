package com.example.attendenceapplication;

import android.widget.CheckBox;

public class Student {
    String registernumber;
    String absent,present;
    String studentName,phoneNo;

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getAbsent() {
        return absent;
    }

    public void setAbsent(String absent) {
        this.absent = absent;
    }

    public String getPresent() {
        return present;
    }

    public void setPresent(String present) {
        this.present = present;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }







    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Student(){

    }
    public Student(String registernumber) {
        this.registernumber = registernumber;
    }

    public String getRegisternumber() {
        return registernumber;
    }

    public void setRegisternumber(String registernumber) {
        this.registernumber = registernumber;
    }
}
