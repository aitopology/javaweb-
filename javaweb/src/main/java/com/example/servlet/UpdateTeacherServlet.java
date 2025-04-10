package com.example.servlet;

import com.example.dao.TeacherDAO;
import com.example.dao.UserDAO;
import com.example.entity.Teacher;
import com.example.entity.User;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

public class UpdateTeacherServlet extends HttpServlet {
    private TeacherDAO teacherDAO = new TeacherDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = java.net.URLDecoder.decode(request.getParameter("name"), "UTF-8").replace(" ", "+");
        String phone= java.net.URLDecoder.decode(request.getParameter("phone"), "UTF-8");
        String subject = java.net.URLDecoder.decode(request.getParameter("subject"), "UTF-8").replace(" ", "+");
        try {
            Teacher teacher = teacherDAO.getTeacherByName(name);
            request.setAttribute("teacher", teacher);
            request.getRequestDispatcher("/editteacher.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "教师信息加载失败: " + e.getMessage());
            request.getRequestDispatcher("/listteacher").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String originalName = java.net.URLDecoder.decode(request.getParameter("originalName"), "UTF-8").replace(" ", "+");
        String newName = java.net.URLDecoder.decode(request.getParameter("name"), "UTF-8").replace(" ", "+");

        try {
            if(!originalName.equals(newName) && teacherDAO.checkNameExists(newName)) {
                request.setAttribute("error", "教师名已存在");
                request.getRequestDispatcher("/editteacher.jsp").forward(request, response);
                return;
            }
        } catch (SQLException e) {
            request.setAttribute("error", "数据库查询异常");
            request.getRequestDispatcher("/editteacher.jsp").forward(request, response);
            return;
        }

        String name = java.net.URLDecoder.decode(request.getParameter("name"), "UTF-8").replace(" ", "+");
        String phone= java.net.URLDecoder.decode(request.getParameter("phone"), "UTF-8");
        String subject = java.net.URLDecoder.decode(request.getParameter("subject"), "UTF-8");
        Teacher teacher = new Teacher(name, phone, subject);

        try {
            teacherDAO.updateTeacher(originalName, teacher);
            response.sendRedirect(request.getContextPath() + "/listteacher");
        } catch (Exception e) {
            request.setAttribute("error", "用户更新失败: " + e.getMessage());
            request.getRequestDispatcher("/editteacher.jsp").forward(request, response);
        }
    }
}