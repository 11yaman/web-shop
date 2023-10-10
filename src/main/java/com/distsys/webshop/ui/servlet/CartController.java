package com.distsys.webshop.ui.servlet;

import com.distsys.webshop.bo.handlers.CartHandler;
import com.distsys.webshop.bo.handlers.ItemHandler;
import com.distsys.webshop.ui.view_model.ViewCart;
import com.distsys.webshop.ui.view_model.ViewItem;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "CartController", value = {"/cart", "/cart/add", "/cart/remove"})
public class CartController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getRequestURI();
        HttpSession session = request.getSession();
        ViewCart cart = getSessionCart(session);

        switch (action){
            case "/cart":
                listCart(request, response, cart);
                break;
            case "/cart/add":
                addItem(request, response, cart, session);
                break;
            case "/cart/remove":
                removeItem(request, response, cart, session);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request,response);
    }
    private void listCart(HttpServletRequest request, HttpServletResponse response, ViewCart cart)
            throws ServletException, IOException {
        request.setAttribute("cart", cart);
        request.getRequestDispatcher("/cart.jsp").forward(request, response);
    }

    private void addItem(HttpServletRequest request, HttpServletResponse response, ViewCart cart, HttpSession session)
            throws IOException, ServletException {
        String itemId = request.getParameter("itemToAdd");

        if (itemId != null) {
            ViewItem itemToAdd = ItemHandler.handleGetItemById(Integer.parseInt(itemId));
            if (itemToAdd != null && moreItemsCanBeAdded(itemToAdd, cart))
                CartHandler.handleAddItem(cart, itemToAdd);
        }
        session.setAttribute("cart", cart);
        request.getRequestDispatcher(request.getContextPath() + "/allItems").forward(request, response);
    }

    private void removeItem(HttpServletRequest request, HttpServletResponse response, ViewCart cart, HttpSession session)
            throws IOException, ServletException {
        String itemId = request.getParameter("itemToRemove");

        if (itemId != null) {
            ViewItem itemToRemove = ItemHandler.handleGetItemById(Integer.parseInt(itemId));
            CartHandler.handleRemoveItem(cart, itemToRemove);
        }
        session.setAttribute("cart", cart);
        request.getRequestDispatcher("/cart").forward(request, response);
    }

    private ViewCart getSessionCart(HttpSession session) {
        ViewCart cart = (ViewCart) session.getAttribute("cart");
        if (cart == null) {
            cart = new ViewCart();
            session.setAttribute("cart", cart);
        }
        return cart;
    }

    private boolean moreItemsCanBeAdded(ViewItem itemToAdd, ViewCart cart) {
        System.out.println("getItemQuantityInCart " + cart.getItemQuantityInCart(itemToAdd));
        return cart.getItemQuantityInCart(itemToAdd) < itemToAdd.getStockQuantity();
    }

}