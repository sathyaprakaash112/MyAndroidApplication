package com.example.attendenceapplication;

public class Classes {
    String Batch,currentYear,currentSemester,DepartmentName;

    public Classes(){

    }
    public Classes(String batch, String currentYear, String currentSemester, String departmentName) {
        Batch = batch;
        this.currentYear = currentYear;
        this.currentSemester = currentSemester;
        DepartmentName = departmentName;
    }

    public String getBatch() {
        return Batch;
    }

    public void setBatch(String batch) {
        Batch = batch;
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

    public String getDepartmentName() {
        return DepartmentName;
    }

    public void setDepartmentName(String departmentName) {
        DepartmentName = departmentName;
    }
}
