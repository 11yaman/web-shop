package com.distsys.webshop.ui.servlet;

import com.distsys.webshop.bo.handlers.OrderHandler;
import com.distsys.webshop.ui.view_model.ViewCart;
import com.distsys.webshop.ui.view_model.ViewOrder;
import com.distsys.webshop.ui.view_model.ViewUser;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "OrderController", value = {"/checkout", "/order","/order/confirm"})
public class OrderController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getRequestURI();
        HttpSession session = request.getSession();
        ViewCart cart = (ViewCart) session.getAttribute("cart");

        switch (action) {
            case "/checkout":
                checkout(request, response, session, cart);
                break;
            case "/order/confirm":
                confirmOrder(request, response, session, cart);
                break;
            default:
                break;
        }
    }

    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request,response);
    }

    private void checkout(HttpServletRequest request, HttpServletResponse response, HttpSession session, ViewCart cart)
            throws IOException, ServletException {
        request.setAttribute("cart", cart);
        session.setAttribute("checkoutVisited", true);
        request.getRequestDispatcher(request.getContextPath() + "/checkout.jsp").forward(request, response);
    }

    private void confirmOrder(HttpServletRequest request, HttpServletResponse response, HttpSession session,
                              ViewCart cart)
            throws IOException, ServletException {
        ViewUser user = (ViewUser) session.getAttribute("user");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String streetName = request.getParameter("streetName");
        String zipCode = request.getParameter("zipCode");
        String city = request.getParameter("city");

        if(cart==null || cart.getItems().isEmpty() || firstName==null ||lastName==null) {
            request.getRequestDispatcher(request.getContextPath() + "/checkout.jsp?error=confirm_error")
                    .forward(request, response);
            return;
        }

        ViewOrder newOrder;
        Map<Integer,Integer> itemIdsAndQuantity = new HashMap<>(cart.getIdQuantityMap());
        if (user!=null)
            newOrder = new ViewOrder(user.getUserId(), itemIdsAndQuantity, firstName, lastName, streetName, zipCode, city);
        else
            newOrder = new ViewOrder(itemIdsAndQuantity, firstName, lastName, streetName, zipCode, city);

        int orderId = OrderHandler.handleNewOrder(newOrder);

        if (orderId > 0) {
            session.removeAttribute("cart");
            request.setAttribute("orderId", orderId);
            request.getRequestDispatcher(request.getContextPath() + "/order_confirmation.jsp")
                    .forward(request, response);
        } else {
            request.getRequestDispatcher(request.getContextPath() + "/checkout.jsp?error=confirm_error")
                    .forward(request, response);
        }
    }
}