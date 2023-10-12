package com.distsys.webshop.ui.servlets;

import com.distsys.webshop.bo.handlers.CartHandler;
import com.distsys.webshop.bo.handlers.ItemHandler;
import com.distsys.webshop.ui.viewmodel.CartDto;
import com.distsys.webshop.ui.viewmodel.ItemDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "CartController", value = {"/cart/list", "/cart/add", "/cart/remove"})
public class CartController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getRequestURI();
        HttpSession session = request.getSession();
        CartDto cart = getSessionCart(session);

        switch (action){
            case "/cart/list":
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
    private void listCart(HttpServletRequest request, HttpServletResponse response, CartDto cart)
            throws ServletException, IOException {
        request.setAttribute("cart", cart);
        request.getRequestDispatcher("/cart.jsp").forward(request, response);
    }

    private void addItem(HttpServletRequest request, HttpServletResponse response, CartDto cart, HttpSession session)
            throws IOException, ServletException {
        String itemId = request.getParameter("itemToAdd");

        if (itemId != null) {
            ItemDto itemToAdd = ItemHandler.handleGetItemById(Integer.parseInt(itemId));
            if (itemToAdd != null && moreItemsCanBeAdded(itemToAdd, cart))
                CartHandler.handleAddItem(cart, itemToAdd);
        }
        session.setAttribute("cart", cart);
        request.getRequestDispatcher(request.getContextPath() + "/items").forward(request, response);
    }

    private void removeItem(HttpServletRequest request, HttpServletResponse response, CartDto cart, HttpSession session)
            throws IOException, ServletException {
        String itemId = request.getParameter("itemToRemove");

        if (itemId != null) {
            ItemDto itemToRemove = ItemHandler.handleGetItemById(Integer.parseInt(itemId));
            CartHandler.handleRemoveItem(cart, itemToRemove);
        }
        session.setAttribute("cart", cart);
        request.getRequestDispatcher(request.getContextPath() + "/cart/list").forward(request, response);
    }

    private CartDto getSessionCart(HttpSession session) {
        CartDto cart = (CartDto) session.getAttribute("cart");
        if (cart == null) {
            cart = new CartDto();
            session.setAttribute("cart", cart);
        }
        return cart;
    }

    private boolean moreItemsCanBeAdded(ItemDto itemToAdd, CartDto cart) {
        System.out.println("getItemQuantityInCart " + cart.getItemQuantityInCart(itemToAdd));
        return cart.getItemQuantityInCart(itemToAdd) < itemToAdd.getStockQuantity();
    }

}