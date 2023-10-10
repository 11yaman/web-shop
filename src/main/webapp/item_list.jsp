<%@ page import="com.distsys.webshop.ui.view_model.ViewUser" %>
<%@ page import="com.distsys.webshop.ui.view_model.ViewItem" %>
<%@ page import="java.util.List" %>
<%--
  Created by IntelliJ IDEA.
  User: Yaman
  Date: 2023-10-03
  Time: 09:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Items</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/item_list.css">
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
        <h2>All items</h2>
        <table>
            <thead>
            <tr>
                <th>Item</th>
                <th>Price</th>
                <th>Stock</th>
                <th></th>
            </tr>
            </thead>
            <tbody>
                <%
                    List<ViewItem> items = (List<ViewItem>) request.getAttribute("items");
                    if (items != null && !items.isEmpty()) {
                        for (ViewItem item : items) {
                %>
                <tr>
                    <td><%= item.getName() %></td>
                    <td><%= item.getPrice() %> SEK </td>
                    <td> <%= item.getStockQuantity() %> </td>
                    <td>
                        <a href="${pageContext.request.contextPath}/cart/add?itemToAdd=<%= item.getId() %>">Add to Cart</a>
                    </td>
                </tr>
            <%
                }
                  }
            %>
        </table>
    </section>
</main>
</body>
</html>
