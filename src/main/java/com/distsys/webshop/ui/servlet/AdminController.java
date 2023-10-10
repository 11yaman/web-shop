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

@WebServlet(name = "AdminController", value = {"/admin", "/admin/editUser", "/admin/saveUser"})
public class AdminController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getRequestURI();
        HttpSession session = request.getSession();

        ViewUser admin = (ViewUser) session.getAttribute("user");
        if (admin == null || admin.getRole() != UserRole.ADMIN) {
            response.sendRedirect(request.getContextPath() + "/user/login");
            return;
        }

        switch (action) {
            case "/admin":
                listAllUsers(request, response, session);
                break;
            case "/admin/editUser":
                editUser(request, response, session);
                break;
            case "/admin/saveUser":
                saveUser(request, response, session);
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

        List<ViewUser> allUsers = UserHandler.handleGetAllUsers();
        request.setAttribute("allUsers", allUsers);

        String page = request.getParameter("page");
        request.setAttribute("currentPage", page != null ? Integer.parseInt(page) : 1);
        request.getRequestDispatcher(request.getContextPath() + "/admin.jsp").forward(request, response);
    }

    private void editUser(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws IOException, ServletException {
        String userId = request.getParameter("id");

        if (userId != null) {
            ViewUser userToEdit = UserHandler.getUserById(userId);
            if (userToEdit != null) {
                request.setAttribute("userToEdit", userToEdit);
                request.getRequestDispatcher("/edit_user.jsp").forward(request, response);
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/admin");
        }
    }

    private void saveUser(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws IOException, ServletException {

        String userId = request.getParameter("userId");
        UserRole newRole = UserRole.valueOf(request.getParameter("userRole"));
        String newFirstName = request.getParameter("firstName");
        String newLastName = request.getParameter("lastName");

        ViewUser user = new ViewUser(userId, newFirstName, newLastName, newRole);
        if (UserHandler.handleEditUser(user)) {
            response.sendRedirect(request.getContextPath() + "/admin");
        } else {
            request.getRequestDispatcher(request.getContextPath() + "/admin/editUser?id=" + userId);
        }
    }

}