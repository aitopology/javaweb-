package com.example.servlet;

import com.example.dao.TeacherDAO;
import com.example.entity.Teacher;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;


public class ListTeacherServlet extends HttpServlet {
    private TeacherDAO teacherDAO = new TeacherDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int page = 1;
        int pageSize = 5;
        if(request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }

        try {
            List<Teacher> teachers = teacherDAO.getAllTeachers(page, pageSize);
            int total = teacherDAO.getTotalCount();
            int totalPages = (int) Math.ceil((double)total / pageSize);

            request.setAttribute("teachers", teachers);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);
            request.getRequestDispatcher("/listteacher.jsp").forward(request, response);//跳转到listteacher.jsp页面
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}