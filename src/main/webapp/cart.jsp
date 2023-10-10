<%@ page import="com.distsys.webshop.ui.view_model.ViewItem" %>
<%@ page import="java.util.List" %>
<%@ page import="com.distsys.webshop.ui.view_model.ViewCart" %>
<%@ page import="com.distsys.webshop.bo.handlers.CartHandler" %>
<%@ page import="com.distsys.webshop.ui.view_model.ViewUser" %><%--
<%--
  Created by IntelliJ IDEA.
  User: Yaman
  Date: 2023-10-02
  Time: 11:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Shopping Cart</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/cart.css">
</head>
<body>
<header>
    <a href="${pageContext.request.contextPath}/allItems">
        <h1>Our store</h1>
    </a>
    <nav>
        <a href="${pageContext.request.contextPath}/cart">Cart</a>
        <a href="${pageContext.request.contextPath}/user">Profile</a>
        <% ViewUser user = (ViewUser) session.getAttribute("user");
            if (user == null){ %>
        <a href="${pageContext.request.contextPath}/user/login">Log in</a>
        <%  } else { %>
        <a href="${pageContext.request.contextPath}/user/logout">Log Out</a>
        <%  } %>
    </nav>
</header>

<main>
    <section>
        <h2>Cart Contents</h2>
        <table>
            <thead>
            <tr>
                <th>Item</th>
                <th>Price (SEK)</th>
                <th>Quantity</th>
                <th>Remove</th>
            </tr>
            </thead>
            <tbody>
                <%
                    ViewCart cart  = (ViewCart) session.getAttribute("cart");
                    double total = 0.0;
                    if ( cart != null ) {
                        List<ViewItem> cartItems = cart.getItems();
                        total = CartHandler.handleGetTotal(cart);
                        if (!cartItems.isEmpty()) {
                            for (ViewItem cartItem : cartItems) {
                %>
            <tr>
                <td><%= cartItem.getName() %></td>
                <td><%= cartItem.getPrice() %> SEK</td>
                <td><%= cart.getItemQuantityInCart(cartItem) %></td>
                <td>
                    <a href="${pageContext.request.contextPath}/cart/remove?itemToRemove=<%= cartItem.getId() %>">Remove</a>
                </td>
            </tr>
            <%
                         }
                     }
                }
            %>
            </tbody>
        </table>
        <p>Total: <span id="total"><%= total %> SEK</span></p>
        <%
            if (cart != null && !cart.getItems().isEmpty()) {
        %>
        <a href="${pageContext.request.contextPath}/checkout" class="button">Checkout</a>
        <%
        } else {
        %>
        <a href="#" class="button disabled">Checkout</a>
        <%
            }
        %>
    </section>
</main>
</body>
</html>
