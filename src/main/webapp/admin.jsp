<%@ page import="com.distsys.webshop.ui.view_model.ViewUser" %>
<%@ page import="java.util.List" %>
<%--
  Created by IntelliJ IDEA.
  User: Yaman
  Date: 2023-10-09
  Time: 18:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin Profile</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/admin.css">
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
        <h2>Logged in as admin <%= user != null ? user.getUserId() : "" %></h2>
    </section>

    <section>
        <h2>User Management</h2>
        <table>
            <thead>
            <tr>
                <th>User ID</th>
                <th>First Name</th>
                <th>Last Name</th>
                <th>User Role</th>
                <th>Action</th>
            </tr>
            </thead>
            <tbody>
            <%
                List<ViewUser> allUsers = (List<ViewUser>) request.getAttribute("allUsers");
                if (allUsers!=null){
                    int currentPage = request.getParameter("page")!=null ? Integer.parseInt(request.getParameter("page")) : 1;
                    int itemsPerPage = 20;
                    int start = (currentPage - 1) * itemsPerPage;
                    int end = Math.min(start + itemsPerPage, allUsers.size());

                    for (int i = start; i < end; i++) {
                        ViewUser u = allUsers.get(i);
            %>
            <tr>
                <td><%= u.getUserId() %></td>
                <td><%= u.getFirstName() %></td>
                <td><%= u.getLastName() %></td>
                <td><%= u.getRole() %></td>
                <td>
                    <a href="<%= request.getContextPath() %>/admin/editUser?id=<%= u.getUserId() %>">Edit</a>
                </td>
            </tr>
            <%
                    }
            %>
            </tbody>
        </table>

        <div class="pagination">
            <%
                int totalPages = (int) Math.ceil((double) allUsers.size() / itemsPerPage);
            %>
            <%
                if (currentPage > 1) {
            %>
            <a href="<%= request.getContextPath() %>/admin?page=<%= currentPage - 1 %>">Back</a>
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
            <a href="<%= request.getContextPath() %>/admin?page=<%= currentPage + 1 %>">Next</a>
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