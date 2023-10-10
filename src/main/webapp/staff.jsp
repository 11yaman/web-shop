<%@ page import="com.distsys.webshop.ui.view_model.ViewUser" %>
<%--
  Created by IntelliJ IDEA.
  User: Yaman
  Date: 2023-10-09
  Time: 20:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Profile</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
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
            if (user == null){
                request.getRequestDispatcher(request.getContextPath() +"/user/login").forward(request, response);
            } else { %>
        <a href="${pageContext.request.contextPath}/user/logout">Log Out</a>
        <%  } %>
    </nav>
</header>

<main>
    <section>
        <h2>Logged in as staff <%=user != null ? user.getUserId() : ""%></h2>
    </section>
</main>
</body>
</html>
