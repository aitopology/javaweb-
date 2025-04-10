package com.example.servlet;

import com.example.dao.UserDAO;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;


public class DeleteUserServlet extends HttpServlet {
    private UserDAO userDAO = new UserDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String username = java.net.URLDecoder.decode(request.getParameter("username"), "UTF-8").replace(" ", "+");
        
        try {
            userDAO.deleteUser(username);
            response.sendRedirect(request.getContextPath() + "/list");// 重定向到用户列表页面
        } catch (Exception e) {
            request.setAttribute("error", "用户删除失败: " + e.getMessage());
            request.getRequestDispatcher("/list").forward(request, response);
        }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}