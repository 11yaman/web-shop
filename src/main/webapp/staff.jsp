<%@ page import="com.distsys.webshop.ui.view_model.ViewUser" %>
<%@ page import="com.distsys.webshop.bo.model.enums.UserRole" %>
<%@ page import="com.distsys.webshop.ui.view_model.ViewOrder" %>
<%@ page import="java.util.List" %>
<%@ page import="com.distsys.webshop.ui.view_model.ViewItem" %>
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
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/staff.css">
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
            if (user == null || user.getRole() != UserRole.STAFF){
                request.getRequestDispatcher(request.getContextPath() +"/user/login").forward(request, response);
            } else { %>
        <a href="${pageContext.request.contextPath}/user/logout">Log Out</a>
        <%  } %>
    </nav>
</header>

<main>
    <section>
        <h2>Logged in as staff: <%=user.getUserId()%></h2>
    </section>

    <section>
        <h2>Uncompleted Orders</h2>
        <table>
            <thead>
            <tr>
                <th>Order ID</th>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Street Name</th>
                <th>Zip Code</th>
                <th>City</th>
                <th>Order Date</th>
                <th>Order Status</th>
                <th>User ID</th>
                <th>Action</th>
            </tr>
            </thead>
            <tbody>
            <%
                List<ViewOrder> uncompletedOrders = (List<ViewOrder>) request.getAttribute("uncompletedOrders");
                for (ViewOrder order : uncompletedOrders) {
            %>
            <tr>
                <td><%= order.getId() %></td>
                <td><%= order.getFirstName() %></td>
                <td><%= order.getLastName() %></td>
                <td><%= order.getStreetName() %></td>
                <td><%= order.getZipCode() %></td>
                <td><%= order.getCity() %></td>
                <td><%= order.getDateTime().toLocalDate() %></td>
                <td><%= order.getStatus() %></td>
                <td><%= order.getUserId() != null ? order.getUserId() : "" %></td>
                <td>
                    <a href="${pageContext.request.contextPath}/staff/packOrder?id=<%= order.getId() %>">Pack</a>
                </td>
            </tr>
            <%
                }
            %>
            </tbody>
        </table>
    </section>
</main>
</body>
</html>
