package com.example.servlet;

import com.example.dao.UserDAO;
import com.example.entity.User;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

public class UpdateUserServlet extends HttpServlet {
    private UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = java.net.URLDecoder.decode(request.getParameter("username"), "UTF-8").replace(" ", "+");
        try {
            User user = userDAO.getUserByUsername(username);
            request.setAttribute("user", user);
            request.getRequestDispatcher("/edit.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "用户信息加载失败: " + e.getMessage());
            request.getRequestDispatcher("/list").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String originalUsername = java.net.URLDecoder.decode(request.getParameter("originalUsername"), "UTF-8").replace(" ", "+");
        String newUsername = java.net.URLDecoder.decode(request.getParameter("username"), "UTF-8").replace(" ", "+");

        try {
            if(!originalUsername.equals(newUsername) && userDAO.checkUsernameExists(newUsername)) {
                request.setAttribute("error", "用户名已存在");
                request.getRequestDispatcher("/edit.jsp").forward(request, response);
                return;
            }
        } catch (SQLException e) {
            request.setAttribute("error", "数据库查询异常");
            request.getRequestDispatcher("/edit.jsp").forward(request, response);
            return;
        }

        String password = request.getParameter("password");
        User user = new User(newUsername, password);

        try {
            userDAO.updateUser(originalUsername, user);
            response.sendRedirect(request.getContextPath() + "/list");
        } catch (Exception e) {
            request.setAttribute("error", "用户更新失败: " + e.getMessage());
            request.getRequestDispatcher("/edit.jsp").forward(request, response);
        }
    }
}