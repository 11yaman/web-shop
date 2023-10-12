<%@ page import="com.distsys.webshop.ui.viewmodel.UserDto" %>
<%@ page import="java.util.List" %>
<%@ page import="com.distsys.webshop.bo.enums.UserRole" %>
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
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/admin_profile.css">
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
            if (user == null || user.getRole()!= UserRole.ADMIN){
                response.sendRedirect(request.getContextPath() +"/user/login");
            } else { %>
        <a href="${pageContext.request.contextPath}/user/logout">Log Out</a>
        <%  } %>
    </nav>
</header>

<main>
    <section>
        <h2>Logged in as admin: <%=user.getUserId()%></h2>
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
                List<UserDto> allUsers = (List<UserDto>) request.getAttribute("allUsers");
                if (allUsers!=null && !allUsers.isEmpty()){
                    int currentPage = (int) request.getAttribute("currentPage");
                    int itemsPerPage = 20;

                    int startIdx = (currentPage - 1) * itemsPerPage;
                    int endIdx = Math.min(startIdx + itemsPerPage, allUsers.size());
                    for (int i = startIdx; i < endIdx; i++) {
                        UserDto u = allUsers.get(i);
            %>
            <tr>
                <td><%= u.getUserId() %></td>
                <td><%= u.getFirstName() %></td>
                <td><%= u.getLastName() %></td>
                <td><%= u.getRole() %></td>
                <td>
                    <a href="${pageContext.request.contextPath}/admin/editUser?id=<%= u.getUserId() %>">Edit</a>
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
            <a href="${pageContext.request.contextPath}/admin?page=<%= currentPage - 1 %>">Back</a>
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
            <a href="${pageContext.request.contextPath}/admin?page=<%= currentPage + 1 %>">Next</a>
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