package com.example.attendenceapplication;

public class UserDetails {

    String departmentName,batch,currentYear,currentSemester,collegeName;

    public UserDetails() {

    }

    public UserDetails(String departmentName, String batch, String currentYear, String currentSemester) {
        this.departmentName = departmentName;
        this.batch = batch;
        this.currentYear = currentYear;
        this.currentSemester = currentSemester;

    }


    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getCurrentYear() {
        return currentYear;
    }

    public void setCurrentYear(String currentYear) {
        this.currentYear = currentYear;
    }

    public String getCurrentSemester() {
        return currentSemester;
    }

    public void setCurrentSemester(String currentSemester) {
        this.currentSemester = currentSemester;
    }
}
