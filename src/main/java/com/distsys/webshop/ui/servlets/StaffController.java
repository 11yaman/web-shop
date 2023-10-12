package com.distsys.webshop.ui.servlets;

import com.distsys.webshop.bo.handlers.OrderHandler;
import com.distsys.webshop.bo.enums.UserRole;
import com.distsys.webshop.ui.viewmodel.OrderDto;
import com.distsys.webshop.ui.viewmodel.UserDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "StaffController", value = {"/staff/profile", "/staff/packOrder"})
public class StaffController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getRequestURI();
        HttpSession session = request.getSession();

        UserDto admin = (UserDto) session.getAttribute("user");
        if (admin == null || admin.getRole() != UserRole.STAFF) {
            response.sendRedirect(request.getContextPath() + "/user/login");
            return;
        }

        switch (action) {
            case "/staff/profile":
                listUncompletedOrders(request, response, session);
                break;
            case "/staff/packOrder":
                packOrder(request, response, session);
                break;
            default:
                break;
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    private void listUncompletedOrders(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws ServletException, IOException {
        List<OrderDto> uncompletedOrders = OrderHandler.handleGetUncompletedOrders();
        request.setAttribute("uncompletedOrders", uncompletedOrders);
        request.getRequestDispatcher(request.getContextPath() +"/staff.jsp").forward(request, response);
    }

    private void packOrder(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws IOException {
        String orderId = request.getParameter("id");

        if (orderId != null)
             OrderHandler.handlePackOrder(Integer.parseInt(orderId));
        response.sendRedirect(request.getContextPath() + "/staff/profile");
    }
}