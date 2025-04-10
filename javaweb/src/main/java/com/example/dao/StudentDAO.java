package com.example.dao;

import com.example.entity.Student;
import com.example.entity.User;
import com.example.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    public boolean checkNoExists(String no) throws SQLException {
        String sql = "SELECT COUNT(*) FROM t_student WHERE no = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, no);
            ResultSet rs = pst.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        }
    }

    public boolean checkStudent(String no,String name,int age) throws SQLException {
        String sql = "SELECT COUNT(*) FROM t_studnet WHERE no = ? AND name = ?AND age = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, no);
            pst.setString(2, name);
            pst.setInt(3, age);
            ResultSet rs = pst.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        }
    }

    public void addStudent(Student student) throws SQLException {
        String sql = "INSERT INTO t_student(no, name,age) VALUES(?,?,?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, student.getNo());
            pst.setString(2, student.getName());
            pst.setInt(3, student.getAge());
            pst.executeUpdate();
        }
    }

    public void deleteStudent(String no) throws SQLException {
        String sql = "DELETE FROM t_student WHERE no=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, no);
            pst.executeUpdate();
        }
    }

    public void updateStudent(String originalNo, Student newStudent) throws SQLException {
        String sql = "UPDATE t_student SET no=?, name=?,age=? WHERE no=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, newStudent.getNo());
            pst.setString(2, newStudent.getName());
            pst.setInt(3, newStudent.getAge());
            pst.setString(4, originalNo);
            pst.executeUpdate();
        }
    }

    public List<Student> getAllStudent(int page, int pageSize) throws SQLException {
        String sql = "SELECT * FROM (SELECT a.*, ROWNUM rn FROM (SELECT * FROM t_student ORDER BY no) a) WHERE rn >= ? AND rn <= ?";
        List<Student> students= new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            int start = (page - 1) * pageSize + 1;
            int end = page * pageSize;
            pst.setInt(1, start);
            pst.setInt(2, end);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                students.add(new Student(rs.getString("no"), rs.getString("name"), rs.getInt("age")));
            }
        }
        return students;
    }

    public int getTotalCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM t_student";
        try (Connection conn = DBUtil.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    public Student getStudentByNo(String no) throws SQLException {
        String sql = "SELECT * FROM t_student WHERE no = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, no);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return new Student(rs.getString("no"), rs.getString("name"),rs.getInt("age"));
            }
        }
        return null;
    }

    public boolean isStudentExists(String no) {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM t_student WHERE no = ?")) {
            stmt.setString(1, no);
            return stmt.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean insertStudent(Student student) {
        if (isStudentExists(student.getNo())) {
            return false;
        }

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO t_student(no,name,age) VALUES(?,?,?)")) {
            stmt.setString(1, student.getNo());
            stmt.setString(2, student.getName());
            stmt.setInt(3, student.getAge());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}