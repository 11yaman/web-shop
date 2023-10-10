package com.distsys.webshop.ui.servlet;

import com.distsys.webshop.bo.handlers.UserHandler;
import com.distsys.webshop.bo.model.enums.UserRole;
import com.distsys.webshop.ui.view_model.ViewUser;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "AdminController", value = {"/admin", "/admin/editUser"})
public class AdminController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getRequestURI();
        HttpSession session = request.getSession();

        switch (action) {
            case "/admin":
                listAllUsers(request, response, session);
                break;
            case "/admin/editUser":
                break;
            default:
                break;
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request,response);
    }

    private void listAllUsers(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws IOException, ServletException {
        ViewUser adminUser = (ViewUser) session.getAttribute("user");

        if (adminUser != null && adminUser.getRole() == UserRole.ADMIN) {
            List<ViewUser> allUsers = UserHandler.handleGetAllUsers();

            int currentPage = 1;
            String page = request.getParameter("page");
            if (page != null)
                currentPage = Integer.parseInt(page);

            request.setAttribute("allUsers", allUsers);
            request.setAttribute("currentPage", currentPage);
            request.getRequestDispatcher(request.getContextPath() + "/admin.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/user/login");
        }
    }
}