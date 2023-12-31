<%@ page import="com.distsys.webshop.ui.viewmodel.UserDto" %>
<%@ page import="com.distsys.webshop.ui.viewmodel.ItemDto" %>
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
                List<ItemDto> items = (List<ItemDto>) request.getAttribute("items");
                if (items!=null && !items.isEmpty()){
                    int currentPage = (int) request.getAttribute("currentPage");
                    int itemsPerPage = 20;

                    int startIdx = (currentPage - 1) * itemsPerPage;
                    int endIdx = Math.min(startIdx + itemsPerPage, items.size());
                    for (int i = startIdx; i < endIdx; i++) {
                        ItemDto item = items.get(i);
            %>
            <tr>
                <td><%= item.getName() %></td>
                <td><%= item.getPrice() %> SEK</td>
                <td><%= item.getStockQuantity() %></td>
                <td>
                    <a href="${pageContext.request.contextPath}/cart/add?itemToAdd=<%= item.getId() %>">Add to Cart</a>
                </td>
            </tr>
            <%
                }
            %>
            </tbody>
        </table>

        <div class="pagination">
            <%
                int totalPages = (int) Math.ceil((double) items.size() / itemsPerPage);
            %>
            <%
                if (currentPage > 1) {
            %>
            <a href="${pageContext.request.contextPath}/items?page=<%= currentPage - 1 %>">Back</a>
            <%
            } else {
            %>
            <span class="disabled">Back</span>
            <%
                }
            %>

            <%
                if (currentPage < totalPages) {
            %>
            <a href="${pageContext.request.contextPath}/items?page=<%= currentPage + 1 %>">Next</a>
            <%
            } else {
            %>
            <span class="disabled">Next</span>
            <%
                }
                }
            %>
        </div>
    </section>
</main>
</body>
</html>