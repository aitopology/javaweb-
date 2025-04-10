package com.example.servlet;

import com.example.dao.StudentDAO;
import com.example.dao.UserDAO;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;


public class DeleteStudentServlet extends HttpServlet {
    private StudentDAO studentDAO = new StudentDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String name = java.net.URLDecoder.decode(request.getParameter("name"), "UTF-8").replace(" ", "+");

        String no=java.net.URLDecoder.decode(request.getParameter("no"), "UTF-8").replace(" ", "+");
        int age=Integer.parseInt(request.getParameter("age"));
        try {
            studentDAO.deleteStudent(no);
            response.sendRedirect(request.getContextPath() + "/liststudent");// 重定向到用户列表页面
        } catch (Exception e) {
            request.setAttribute("error", "学生删除失败: " + e.getMessage());
            request.getRequestDispatcher("/liststudent").forward(request, response);
        }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}