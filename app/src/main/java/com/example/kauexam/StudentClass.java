package com.example.kauexam;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class StudentClass {

    private String id;
    private String fullName;
    private String section;

    public StudentClass() {}

    public StudentClass(String id, String fullName, String section) {

        this.id = id;
        this.fullName = fullName;
        this.section = section;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }


    @Override
    public String toString() {
        return "StudentClass{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", section='" + section + '\'' +
                '}';
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("fullName", fullName);
        result.put("section", section);



        return result;
    }

}
