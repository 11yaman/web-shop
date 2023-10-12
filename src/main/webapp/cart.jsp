<%@ page import="com.distsys.webshop.ui.viewmodel.ItemDto" %>
<%@ page import="java.util.List" %>
<%@ page import="com.distsys.webshop.ui.viewmodel.CartDto" %>
<%@ page import="com.distsys.webshop.bo.handlers.CartHandler" %>
<%@ page import="com.distsys.webshop.ui.viewmodel.UserDto" %><%--
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
                    CartDto cart  = (CartDto) session.getAttribute("cart");
                    double total = 0.0;
                    if ( cart != null ) {
                        List<ItemDto> cartItems = cart.getItems();
                        total = CartHandler.handleGetTotal(cart);
                        if (!cartItems.isEmpty()) {
                            for (ItemDto cartItem : cartItems) {
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
        <a href="${pageContext.request.contextPath}/order/checkout" class="button">Checkout</a>
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
