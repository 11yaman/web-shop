package com.distsys.webshop.ui.servlet;

import com.distsys.webshop.bo.handlers.ItemHandler;
import com.distsys.webshop.ui.view_model.ViewItem;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ItemController", value = {"/allItems"})
public class ItemController extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        listAllItems(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    private void listAllItems(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<ViewItem> items = ItemHandler.handleGetAllItems();
        request.setAttribute("items", items);

        String page = request.getParameter("page");
        request.setAttribute("currentPage", page != null ? Integer.parseInt(page) : 1);
        request.getRequestDispatcher("/item_list.jsp").forward(request, response);
    }
}