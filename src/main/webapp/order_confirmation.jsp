<%@ page import="com.distsys.webshop.ui.view_model.ViewUser" %>
<%--
  Created by IntelliJ IDEA.
  User: Yaman
  Date: 2023-10-07
  Time: 14:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Order Confirmation</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/order_confirmation.css">

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
        <h2>Order Confirmation</h2>
        <%
            Integer orderId = (Integer) request.getAttribute("orderId");
            if (orderId == null) {
        %>
        <p>Order ID not found.</p>
        <%
        } else {
        %>
        <p>Your order with ID <%= orderId %> has been confirmed. Thank you for your purchase!</p>
        <%
            }
        %>
    </section>
</main>
</body>
</html>
