package com.distsys.webshop.ui.servlet;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/order/confirm")
public class OrderConfirmationFilter implements Filter {
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession();
        Boolean checkoutVisited = (Boolean) session.getAttribute("checkoutVisited");

        if (checkoutVisited != null && checkoutVisited) {
            // User is confirming the order, proceed with the request
            chain.doFilter(request, response);
        } else {
            // Redirect the user to the checkout page or an error page
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/checkout?error=confirm_error");
        }
    }
}
