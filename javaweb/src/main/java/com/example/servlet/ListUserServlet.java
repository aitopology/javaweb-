package com.example.servlet;

import com.example.dao.UserDAO;
import com.example.entity.User;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;


public class ListUserServlet extends HttpServlet {
    private UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int page = 1;
        int pageSize = 5;
        if(request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }

        try {
            List<User> users = userDAO.getAllUsers(page, pageSize);
            int total = userDAO.getTotalCount();
            int totalPages = (int) Math.ceil((double)total / pageSize);

            request.setAttribute("users", users);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);
            request.getRequestDispatcher("/list.jsp").forward(request, response);//跳转到list.jsp页面
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}