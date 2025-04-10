package com.example.servlet;

import com.example.dao.TeacherDAO;
import com.example.entity.Teacher;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;


public class AddTeacherServlet extends HttpServlet {
    private  TeacherDAO teacherDAO = new TeacherDAO();
    @Override

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String name = java.net.URLDecoder.decode(request.getParameter("name"), "UTF-8").replace(" ", "+");
        String subject = java.net.URLDecoder.decode(request.getParameter("subject"), "UTF-8").replace(" ", "+");
        String phone = java.net.URLDecoder.decode(request.getParameter("phone"), "UTF-8").replace(" ", "+");
        try {
            if(teacherDAO.checkNameExists(name)) {
                request.setAttribute("error", "教师已存在");
                request.getRequestDispatcher("/addteacher.jsp").forward(request, response);
                return;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Teacher teacher = new Teacher(name, subject,phone);
        try {
            teacherDAO.addTeacher(teacher);
            response.sendRedirect(request.getContextPath() + "/listteacher");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}