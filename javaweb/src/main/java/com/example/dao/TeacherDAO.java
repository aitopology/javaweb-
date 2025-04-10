package com.example.dao;

import com.example.entity.Teacher;
import com.example.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeacherDAO {

    public boolean checkNameExists(String name) throws SQLException {
        String sql = "SELECT COUNT(*) FROM t_teacher WHERE name = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, name);
            ResultSet rs = pst.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        }
    }

    public boolean checkTeacher(String name, String phone,String subject) throws SQLException {
        String sql = "SELECT COUNT(*) FROM t_teacher WHERE name = ? AND phone = ?AND subject=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, name);
            pst.setString(2, phone);
            pst.setString(3, subject);
            ResultSet rs = pst.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        }
    }

    public void addTeacher(Teacher teacher) throws SQLException {
        String sql = "INSERT INTO t_teacher(name, phone,subject) VALUES(?,?,?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, teacher.getName());
            pst.setString(2, teacher.getPhone());
            pst.setString(3, teacher.getSubject());
            pst.executeUpdate();
        }
    }

    public void deleteTeacher(String name) throws SQLException {
        String sql = "DELETE FROM t_teacher WHERE name=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, name);
            pst.executeUpdate();
        }
    }

    public void updateTeacher(String originalName, Teacher newTeacher) throws SQLException {
        String sql = "UPDATE t_teacher SET name=?, phone=? ,subject=? WHERE name=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, newTeacher.getName());
            pst.setString(2, newTeacher.getPhone());
            pst.setString(3, newTeacher.getSubject());
            pst.setString(4, originalName);
            pst.executeUpdate();
        }
    }

    public List<Teacher> getAllTeachers(int page, int pageSize) throws SQLException {
        String sql = "SELECT * FROM (SELECT a.*, ROWNUM rn FROM (SELECT * FROM t_teacher ORDER BY name) a) WHERE rn >= ? AND rn <= ?";
        List<Teacher> teachers = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            int start = (page - 1) * pageSize + 1;
            int end = page * pageSize;
            pst.setInt(1, start);
            pst.setInt(2, end);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                teachers.add(new Teacher(rs.getString("name"), rs.getString("phone"), rs.getString("subject")));
            }
        }
        return teachers;
    }

    public int getTotalCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM t_teacher";
        try (Connection conn = DBUtil.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    public Teacher getTeacherByName(String name) throws SQLException {
        String sql = "SELECT * FROM t_teacher WHERE name = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, name);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return new Teacher(rs.getString("name"), rs.getString("phone"), rs.getString("subject"));
            }
        }
        return null;
    }

    public boolean isUserExists(String name) {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM t_teacher WHERE name = ?")) {
            stmt.setString(1, name);
            return stmt.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean insertTeacher(Teacher teacher) {
        if (isUserExists(teacher.getName())) {
            return false;
        }

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO t_teacher(name, phone,subject) VALUES(?,?,?)")) {
            stmt.setString(1, teacher.getName());
            stmt.setString(2, teacher.getPhone());
            stmt.setString(3, teacher.getSubject());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}