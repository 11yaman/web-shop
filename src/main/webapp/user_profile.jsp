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
    <a href="${pageContext.request.contextPath}/allItems">
      <h1>Our store</h1>
    </a>
    <nav>
        <a href="${pageContext.request.contextPath}/cart">Cart</a>
        <a href="${pageContext.request.contextPath}/user">Profile</a>
        <% String userId = (String) session.getAttribute("userId");
            if (userId == null){
                request.getRequestDispatcher(request.getContextPath() +"/user/login").forward(request, response);
            } else { %>
        <a href="${pageContext.request.contextPath}/user/logout">Log Out</a>
        <%  } %>
    </nav>
</header>

<main>
    <section>
        <h2>Logged in as <%=userId%></h2>
    </section>
</main>
</body>
</html>
