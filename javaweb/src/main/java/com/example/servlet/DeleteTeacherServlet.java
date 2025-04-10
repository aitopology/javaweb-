package com.example.servlet;

import com.example.dao.TeacherDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;


public class DeleteTeacherServlet extends HttpServlet {
    private TeacherDAO teacherDAO=new TeacherDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String name = java.net.URLDecoder.decode(request.getParameter("name"), "UTF-8").replace(" ", "+");

        try {
            teacherDAO.deleteTeacher(name);
            response.sendRedirect(request.getContextPath() + "/listteacher");// 重定向到用户列表页面
        } catch (Exception e) {
            request.setAttribute("error", "教师删除失败: " + e.getMessage());
            request.getRequestDispatcher("/listteacher").forward(request, response);
        }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}