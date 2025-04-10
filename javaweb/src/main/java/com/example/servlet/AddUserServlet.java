package com.example.servlet;

import com.example.dao.UserDAO;
import com.example.entity.User;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;


public class AddUserServlet extends HttpServlet {
    private UserDAO userDAO = new UserDAO();
    @Override

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String username = java.net.URLDecoder.decode(request.getParameter("username"), "UTF-8").replace(" ", "+");
        String password = request.getParameter("password");
        try {
            if(userDAO.checkUsernameExists(username)) {
                request.setAttribute("error", "用户名已存在");
                request.getRequestDispatcher("/add.jsp").forward(request, response);
                return;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        User user = new User(username, password);
        try {
            userDAO.addUser(user);
            response.sendRedirect(request.getContextPath() + "/list");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}