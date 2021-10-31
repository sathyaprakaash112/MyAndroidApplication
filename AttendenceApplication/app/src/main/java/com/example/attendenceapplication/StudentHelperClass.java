package com.example.attendenceapplication;

import android.widget.CheckBox;

public class StudentHelperClass {
    String registernumber;
    CheckBox present,absent;
    public StudentHelperClass() {
    }

    public StudentHelperClass(String registernumber,CheckBox present,CheckBox absent) {
        this.registernumber = registernumber;
        this.present = present;
        this.absent = absent;
    }

    public CheckBox getPresent() {
        return present;
    }

    public void setPresent(CheckBox present) {
        this.present = present;
    }

    public CheckBox getAbsent() {
        return absent;
    }

    public void setAbsent(CheckBox absent) {
        this.absent = absent;
    }

    public String getRegisternumber() {
        return registernumber;
    }

    public void setRegisternumber(String registernumber) {
        this.registernumber = registernumber;
    }
}
