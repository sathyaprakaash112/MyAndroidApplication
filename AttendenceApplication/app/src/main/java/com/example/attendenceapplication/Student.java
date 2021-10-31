package com.example.attendenceapplication;

public class Student {
    String registernumber;

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
