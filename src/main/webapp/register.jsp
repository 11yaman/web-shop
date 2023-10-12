<%@ page import="com.distsys.webshop.ui.viewmodel.UserDto" %><%--
  Created by IntelliJ IDEA.
  User: Yaman
  Date: 2023-10-09
  Time: 12:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/login_register.css">
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
        <%
            } else {
                request.getRequestDispatcher(request.getContextPath() +"/user/profile").forward(request, response);
            } %>
    </nav>
</header>
<main>
    <section>
        <h2>User Registration</h2>
        <form action="${pageContext.request.contextPath}/user/register" method="post">
            <label for="firstName">First Name:</label>
            <input type="text" id="firstName" name="firstName" required>
            <br>
            <label for="lastName">Last Name:</label>
            <input type="text" id="lastName" name="lastName" required>
            <br>
            <label for="userId">Username:</label>
            <input type="text" id="userId" name="userId" required>
            <br>
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required>
            <br>
            <input type="submit" value="Register">
        </form>
    </section>
</main>
</body>
</html>
