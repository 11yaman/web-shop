<%@ page import="com.distsys.webshop.ui.viewmodel.UserDto" %>
<%@ page import="com.distsys.webshop.bo.enums.UserRole" %>
<%--
  Created by IntelliJ IDEA.
  User: Yaman
  Date: 2023-10-05
  Time: 22:45
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
    <a href="${pageContext.request.contextPath}/items">
      <h1>Our store</h1>
    </a>
    <nav>
        <a href="${pageContext.request.contextPath}/cart/list">Cart</a>
        <a href="${pageContext.request.contextPath}/user/profile">Profile</a>
        <% UserDto user = (UserDto) session.getAttribute("user");
            if (user == null || user.getRole()!= UserRole.CUSTOMER){
                response.sendRedirect(request.getContextPath() +"/user/login");
            } else { %>
        <a href="${pageContext.request.contextPath}/user/logout">Log Out</a>
        <%  } %>
    </nav>
</header>

<main>
    <section>
        <h2>Logged in as customer: <%=user.getUserId()%></h2>
    </section>
</main>
</body>
</html>
