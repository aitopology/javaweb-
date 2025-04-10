package com.example.servlet;

import com.example.dao.StudentDAO;
import com.example.dao.UserDAO;
import com.example.entity.Student;
import com.example.entity.User;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;


public class AddStudentServlet extends HttpServlet {
    private StudentDAO studentDAO = new StudentDAO();
    @Override

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String no = request.getParameter("no");
        int age=Integer.parseInt(request.getParameter("age"));
        String name = java.net.URLDecoder.decode(request.getParameter("name"), "UTF-8").replace(" ", "+");
        try {
            if(studentDAO.checkNoExists(no)) {
                request.setAttribute("error", "学号已存在");
                request.getRequestDispatcher("/addstudent.jsp").forward(request, response);
                return;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Student student = new Student(no,name,age);
        try {
            studentDAO.addStudent(student);
            response.sendRedirect(request.getContextPath() + "/liststudent");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}