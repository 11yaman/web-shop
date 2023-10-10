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

@WebServlet(name = "UserController", value = {"/user", "/user/login", "/user/logout", "/user/register"})
public class UserController extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getRequestURI();
        HttpSession session = request.getSession();

        switch (action) {
            case "/user":
                roleSpecificProfile(request, response, session);
                break;
            case "/user/login":
                login(request, response, session);
                break;
            case "/user/logout":
                logout(request, response, session);
                break;
            case "/user/register":
                register(request, response, session);
            default:
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    private void roleSpecificProfile(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws ServletException, IOException {

        ViewUser user = (ViewUser) session.getAttribute("user");
        if(user == null)
            request.getRequestDispatcher(request.getContextPath() +"/user/login").forward(request, response);
        else if (user.getRole() == UserRole.CUSTOMER)
            request.getRequestDispatcher(request.getContextPath() +"/customer.jsp").forward(request, response);
        else if (user.getRole() == UserRole.STAFF)
            request.getRequestDispatcher(request.getContextPath() +"/staff").forward(request, response);
        else if (user.getRole() == UserRole.ADMIN)
            request.getRequestDispatcher(request.getContextPath() +"/admin").forward(request, response);
    }

    private void login(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws IOException, ServletException {
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");

        if (password != null && userId != null) {
            ViewUser user = UserHandler.handleUserLogin(userId, password);

            if(user!=null) {
                session.setAttribute("user", user);
                response.sendRedirect(request.getContextPath() + "/user");
                return;
            }
        }
        request.getRequestDispatcher(request.getContextPath() +"/login.jsp").forward(request, response);
    }
    private void logout(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws IOException {
        if(session!=null)
            session.removeAttribute("user");
        response.sendRedirect(request.getContextPath() +"/user/login");
    }

    private void register(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws IOException, ServletException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");

        if (password != null && userId != null) {
            ViewUser user = UserHandler.handleUserRegister(
                            new ViewUser(userId, password, firstName, lastName, UserRole.CUSTOMER), password);
            System.out.println(user);
            session.setAttribute("user", user);
            response.sendRedirect(request.getContextPath() + "/user");
        } else {
            request.getRequestDispatcher(request.getContextPath() + "/register.jsp").forward(request, response);
        }
    }
}