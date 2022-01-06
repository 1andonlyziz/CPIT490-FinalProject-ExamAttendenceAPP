package com.example.kauexam;

import com.google.firebase.database.Exclude;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ExamHistory {

    private String courseCode;
    private String location;
    private String examDate;
    private String examTime;

    // default constructor for retrieving datasnapshot
    public ExamHistory(){};

    public ExamHistory(String courseCode, String location, String examDate, String examTime) {
        this.courseCode = courseCode;
        this.location = location;
        this.examDate = examDate;
        this.examTime = examTime;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getExamDate() {
        return examDate;
    }

    public void setExamDate(String examDate) {
        this.examDate = examDate;
    }

    public String getExamTime() {
        return examTime;
    }

    public void setExamTime(String examTime) {
        this.examTime = examTime;
    }

    @Override
    public String toString() {
        return "ExamHistory{" +
                "courseCode='" + courseCode + '\'' +
                ", location='" + location + '\'' +
                ", examDate='" + examDate + '\'' +
                ", examTime='" + examTime + '\'' +
                '}';
    }
}
