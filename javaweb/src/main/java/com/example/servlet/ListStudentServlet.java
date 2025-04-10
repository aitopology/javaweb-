package com.example.servlet;

import com.example.dao.StudentDAO;
import com.example.dao.UserDAO;
import com.example.entity.Student;
import com.example.entity.User;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;


public class ListStudentServlet extends HttpServlet {
    private StudentDAO studentDAO = new StudentDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int page = 1;
        int pageSize = 5;
        if(request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }

        try {
            List<Student> students = studentDAO.getAllStudent(page, pageSize);
            int total = studentDAO.getTotalCount();
            int totalPages = (int) Math.ceil((double)total / pageSize);

            request.setAttribute("students", students);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);
            request.getRequestDispatcher("/liststudent.jsp").forward(request, response);//跳转到list.jsp页面
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}