package com.lecturer.dao;

import com.lecturer.model.Lecturer;
import com.lecturer.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LecturerDAO {

    public boolean addLecturer(Lecturer lecturer) throws SQLException {
        String sql = "INSERT INTO lecturers (staff_id, full_name, email, department, phone) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, lecturer.getStaffId());
            ps.setString(2, lecturer.getFullName());
            ps.setString(3, lecturer.getEmail());
            ps.setString(4, lecturer.getDepartment());
            ps.setString(5, lecturer.getPhone());
            return ps.executeUpdate() > 0;
        }
    }

    public List<Lecturer> getAllLecturers() throws SQLException {
        List<Lecturer> lecturers = new ArrayList<>();
        String sql = "SELECT * FROM lecturers ORDER BY created_at DESC";
        try (Connection conn = DBConnection.getConnection();
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Lecturer l = new Lecturer();
                l.setId(rs.getInt("id"));
                l.setStaffId(rs.getString("staff_id"));
                l.setFullName(rs.getString("full_name"));
                l.setEmail(rs.getString("email"));
                l.setDepartment(rs.getString("department"));
                l.setPhone(rs.getString("phone"));
                l.setCreatedAt(rs.getTimestamp("created_at"));
                lecturers.add(l);
            }
        }
        return lecturers;
    }

    public boolean updateLecturer(Lecturer lecturer) throws SQLException {
        String sql = "UPDATE lecturers SET full_name = ?, email = ?, department = ?, phone = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, lecturer.getFullName());
            ps.setString(2, lecturer.getEmail());
            ps.setString(3, lecturer.getDepartment());
            ps.setString(4, lecturer.getPhone());
            ps.setInt(5, lecturer.getId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean deleteLecturer(int id) throws SQLException {
        String sql = "DELETE FROM lecturers WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    public Lecturer getLecturerById(int id) throws SQLException {
        String sql = "SELECT * FROM lecturers WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Lecturer l = new Lecturer();
                l.setId(rs.getInt("id"));
                l.setStaffId(rs.getString("staff_id"));
                l.setFullName(rs.getString("full_name"));
                l.setEmail(rs.getString("email"));
                l.setDepartment(rs.getString("department"));
                l.setPhone(rs.getString("phone"));
                l.setCreatedAt(rs.getTimestamp("created_at"));
                return l;
            }
        }
        return null;
    }

    public List<Lecturer> getLecturersByDepartment(String department) throws SQLException {
        List<Lecturer> lecturers = new ArrayList<>();
        String sql = "SELECT * FROM lecturers WHERE department = ? ORDER BY full_name";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, department);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Lecturer l = new Lecturer();
                l.setId(rs.getInt("id"));
                l.setStaffId(rs.getString("staff_id"));
                l.setFullName(rs.getString("full_name"));
                l.setEmail(rs.getString("email"));
                l.setDepartment(rs.getString("department"));
                l.setPhone(rs.getString("phone"));
                l.setCreatedAt(rs.getTimestamp("created_at"));
                lecturers.add(l);
            }
        }
        return lecturers;
    }
}