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

@WebServlet(name = "UpdateUserServlet", value = "/update")
public class UpdateStudentServlet extends HttpServlet {
    private StudentDAO studentDAO = new StudentDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String no = java.net.URLDecoder.decode(request.getParameter("no"), "UTF-8").replace(" ", "+");
        try {
            Student student=studentDAO.getStudentByNo(no);
            request.setAttribute("student", student);
            request.getRequestDispatcher("/editstudent.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "学生信息加载失败: " + e.getMessage());
            request.getRequestDispatcher("/listStudent").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String originalNo = java.net.URLDecoder.decode(request.getParameter("originalNo"), "UTF-8").replace(" ", "+");
        String newNo = java.net.URLDecoder.decode(request.getParameter("no"), "UTF-8").replace(" ", "+");

        try {
            if(!originalNo.equals(newNo) && studentDAO.checkNoExists(newNo)) {
                request.setAttribute("error", "用户名已存在");
                request.getRequestDispatcher("/updatestudent.jsp").forward(request, response);
                return;
            }
        } catch (SQLException e) {
            request.setAttribute("error", "数据库查询异常");
            request.getRequestDispatcher("/updatestudent.jsp").forward(request, response);
            return;
        }
        String no=originalNo;
        String name = request.getParameter("name");
        int age = Integer.parseInt(request.getParameter("age"));

        Student student = new Student(newNo,name,age);

        try {
            studentDAO.updateStudent(originalNo, student);
            response.sendRedirect(request.getContextPath() + "/liststudent");
        } catch (Exception e) {
            request.setAttribute("error", "学生更新失败: " + e.getMessage());
            request.getRequestDispatcher("/updatestudent.jsp").forward(request, response);
        }
    }
}