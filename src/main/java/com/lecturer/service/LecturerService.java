package com.lecturer.service;

import com.lecturer.dao.LecturerDAO;
import com.lecturer.model.Lecturer;
import java.sql.SQLException;
import java.util.List;

public class LecturerService {
    private LecturerDAO lecturerDAO;

    public LecturerService() {
        this.lecturerDAO = new LecturerDAO();
    }

    // Business logic for adding lecturer
    public boolean addLecturer(Lecturer lecturer) throws SQLException {
        // Validate business rules
        if (lecturer.getStaffId() == null || lecturer.getStaffId().trim().isEmpty()) {
            throw new IllegalArgumentException("Staff ID is required");
        }
        if (lecturer.getFullName() == null || lecturer.getFullName().trim().isEmpty()) {
            throw new IllegalArgumentException("Full name is required");
        }
        if (lecturer.getEmail() == null || !lecturer.getEmail().contains("@")) {
            throw new IllegalArgumentException("Valid email is required");
        }
        
        return lecturerDAO.addLecturer(lecturer);
    }

    // Business logic for updating lecturer
    public boolean updateLecturer(Lecturer lecturer) throws SQLException {
        // Validate business rules
        if (lecturer.getId() <= 0) {
            throw new IllegalArgumentException("Valid lecturer ID is required");
        }
        if (lecturer.getFullName() == null || lecturer.getFullName().trim().isEmpty()) {
            throw new IllegalArgumentException("Full name is required");
        }
        if (lecturer.getEmail() == null || !lecturer.getEmail().contains("@")) {
            throw new IllegalArgumentException("Valid email is required");
        }
        
        return lecturerDAO.updateLecturer(lecturer);
    }

    // Business logic for deleting lecturer
    public boolean deleteLecturer(int id) throws SQLException {
        if (id <= 0) {
            throw new IllegalArgumentException("Valid lecturer ID is required");
        }
        return lecturerDAO.deleteLecturer(id);
    }

    // Get lecturer by ID
    public Lecturer getLecturerById(int id) throws SQLException {
        if (id <= 0) {
            throw new IllegalArgumentException("Valid lecturer ID is required");
        }
        return lecturerDAO.getLecturerById(id);
    }

    // Get all lecturers
    public List<Lecturer> getAllLecturers() throws SQLException {
        return lecturerDAO.getAllLecturers();
    }

    // Get lecturers by department
    public List<Lecturer> getLecturersByDepartment(String department) throws SQLException {
        if (department == null || department.trim().isEmpty()) {
            throw new IllegalArgumentException("Department is required");
        }
        return lecturerDAO.getLecturersByDepartment(department);
    }

    // Check if lecturer exists (for other services)
    public boolean lecturerExists(int id) throws SQLException {
        Lecturer lecturer = lecturerDAO.getLecturerById(id);
        return lecturer != null;
    }

    // Get lecturer basic info for other services
    public Lecturer getLecturerBasicInfo(int id) throws SQLException {
        Lecturer lecturer = getLecturerById(id);
        if (lecturer != null) {
            // Return only basic info needed by other services
            Lecturer basicInfo = new Lecturer();
            basicInfo.setId(lecturer.getId());
            basicInfo.setStaffId(lecturer.getStaffId());
            basicInfo.setFullName(lecturer.getFullName());
            basicInfo.setDepartment(lecturer.getDepartment());
            return basicInfo;
        }
        return null;
    }
}
