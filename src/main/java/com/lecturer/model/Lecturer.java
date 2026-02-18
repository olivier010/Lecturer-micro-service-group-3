package com.lecturer.model;

/**
 * This is the Model class.
 * It represents the data structure of a Lecturer.
 */
public class Lecturer {
    private int id;
    private String staffId;
    private String fullName;
    private String email;
    private String department;
    private String phone;
    private java.sql.Timestamp createdAt;

    // 1. Default constructor (required for some frameworks)
    public Lecturer() {
    }

    // 2. Constructor for adding new lecturers (we don't pass 'id' and 'createdAt'
    // because DB generates them)
    public Lecturer(String staffId, String fullName, String email, String department, String phone) {
        this.staffId = staffId;
        this.fullName = fullName;
        this.email = email;
        this.department = department;
        this.phone = phone;
    }

    // 3. Constructor for retrieving lecturers (includes all fields from DB)
    public Lecturer(int id, String staffId, String fullName, String email, String department, String phone,
            java.sql.Timestamp createdAt) {
        this.id = id;
        this.staffId = staffId;
        this.fullName = fullName;
        this.email = email;
        this.department = department;
        this.phone = phone;
        this.createdAt = createdAt;
    }

    // 4. Getters and Setters (Crucial: These must be PUBLIC)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public java.sql.Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(java.sql.Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}