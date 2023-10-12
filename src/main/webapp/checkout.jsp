<%@ page import="com.distsys.webshop.ui.viewmodel.CartDto" %>
<%@ page import="com.distsys.webshop.ui.viewmodel.ItemDto" %>
<%@ page import="java.util.List" %>
<%@ page import="com.distsys.webshop.bo.handlers.CartHandler" %>
<%@ page import="com.distsys.webshop.ui.viewmodel.UserDto" %>
<%--
  Created by IntelliJ IDEA.
  User: Yaman
  Date: 2023-10-07
  Time: 13:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Checkout</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/checkout.css">
</head>
<body>
<header>
    <a href="${pageContext.request.contextPath}/items">
        <h1>Our store</h1>
    </a>
    <nav>
        <a href="${pageContext.request.contextPath}/cart/list">Cart</a>
        <a href="${pageContext.request.contextPath}/user/profile">Profile</a>
        <% UserDto user = (UserDto) session.getAttribute("user");
            if (user == null){ %>
        <a href="${pageContext.request.contextPath}/user/login">Log in</a>
        <%  } else { %>
        <a href="${pageContext.request.contextPath}/user/logout">Log Out</a>
        <%  } %>
    </nav>
</header>

<main>
    <section>
        <h2>Order Summary</h2>
        <table>
            <thead>
            <tr>
                <th>Item</th>
                <th>Price (SEK)</th>
                <th>Quantity</th>
            </tr>
            </thead>
            <tbody>
            <% CartDto cart = (CartDto) session.getAttribute("cart");
                if (cart == null || cart.getItems().isEmpty()) {
                    response.sendRedirect(request.getContextPath() +"/cart/list");%>
            <%} else {
                    List<ItemDto> cartItems = cart.getItems();
                    if (!cartItems.isEmpty()) {
                        for (ItemDto cartItem : cartItems) {
            %>
            <tr>
                <td><%= cartItem.getName() %></td>
                <td><%= cartItem.getPrice() %> SEK</td>
                <td><%= cart.getItemQuantityInCart(cartItem) %></td>
            </tr>
        <%
                    }
                        %>
            </tbody>
        </table>
        <p>Total: <span id="total"><%= CartHandler.handleGetTotal(cart) %> SEK</span></p>
        <%
                }
            }
        %>
    </section>
    <section>
        <h2>Shipping Information</h2>
        <%
            if (user == null) {
        %>
        <p>Shopping as a Guest</p>
        <%
        } else {
        %>

        <p>Shopping as <%= user.getUserId() %></p>
        <%
            }
        %>
        <form action="${pageContext.request.contextPath}/order/confirm" method="post">
            <div class="shipping-info">
                <label for="firstName">First Name:</label>
                <input type="text" id="firstName" name="firstName" required>
            </div>
            <div class="shipping-info">
                <label for="lastName">Last Name:</label>
                <input type="text" id="lastName" name="lastName" required>
            </div>
            <div class="shipping-info">
                <label for="streetName">Street Name:</label>
                <input type="text" id="streetName" name="streetName" required>
            </div>
            <div class="shipping-info">
                <label for="zipCode">ZIP Code:</label>
                <input type="text" id="zipCode" name="zipCode" required>
            </div>
            <div class="shipping-info">
                <label for="city">City:</label>
                <input type="text" id="city" name="city" required>
            </div>
            <input type="submit" value="Confirm Order">
        </form>
    </section>
</main>
</body>
</html>
