package com.example.dao;

import com.example.entity.User;
import com.example.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    
    public boolean checkUsernameExists(String username) throws SQLException {
        String sql = "SELECT COUNT(*) FROM udata WHERE username = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, username);
            ResultSet rs = pst.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        }
    }

    public boolean checkUser(String username, String password) throws SQLException {
        String sql = "SELECT COUNT(*) FROM udata WHERE username = ? AND password = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, username);
            pst.setString(2, password);
            ResultSet rs = pst.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        }
    }

    public void addUser(User user) throws SQLException {
        String sql = "INSERT INTO udata(username, password) VALUES(?,?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, user.getUsername());
            pst.setString(2, user.getPassword());
            pst.executeUpdate();
        }
    }

    public void deleteUser(String username) throws SQLException {
        String sql = "DELETE FROM udata WHERE username=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, username);
            pst.executeUpdate();
        }
    }

    public void updateUser(String originalUsername, User newUser) throws SQLException {
        String sql = "UPDATE udata SET username=?, password=? WHERE username=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, newUser.getUsername());
            pst.setString(2, newUser.getPassword());
            pst.setString(3, originalUsername);
            pst.executeUpdate();
        }
    }

    public List<User> getAllUsers(int page, int pageSize) throws SQLException {
        String sql = "SELECT * FROM (SELECT a.*, ROWNUM rn FROM (SELECT * FROM udata ORDER BY username) a) WHERE rn >= ? AND rn <= ?";
        List<User> users = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            int start = (page - 1) * pageSize + 1;
            int end = page * pageSize;
            pst.setInt(1, start);
            pst.setInt(2, end);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                users.add(new User(rs.getString("username"), rs.getString("password")));
            }
        }
        return users;
    }

    public int getTotalCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM udata";
        try (Connection conn = DBUtil.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }
    
    public User getUserByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM udata WHERE username = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, username);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return new User(rs.getString("username"), rs.getString("password"));
            }
        }
        return null;
    }

    public boolean isUserExists(String username) {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM udata WHERE username = ?")) {
            stmt.setString(1, username);
            return stmt.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean insertUser(User user) {
        if (isUserExists(user.getUsername())) {
            return false;
        }
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO udata(username, password) VALUES(?,?)")) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}