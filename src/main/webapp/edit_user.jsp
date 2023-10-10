<%@ page import="com.distsys.webshop.ui.view_model.ViewUser" %>
<%@ page import="com.distsys.webshop.bo.model.enums.UserRole" %><%--
  Created by IntelliJ IDEA.
  User: Yaman
  Date: 2023-10-10
  Time: 14:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Edit User</title>
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/edit_user.css">
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
      if (user == null || user.getRole()!= UserRole.ADMIN){
        response.sendRedirect(request.getContextPath() +"/user/login");
      } else { %>
    <a href="${pageContext.request.contextPath}/user/logout">Log Out</a>
    <%  } %>
  </nav>
</header>

<main>
  <section>
    <h2>Edit User</h2>
    <form action="${pageContext.request.contextPath}/admin/saveUser" method="post">
      <%
        ViewUser userToEdit = (ViewUser) request.getAttribute("userToEdit");
      %>
      <input type="hidden" name="userId" value="<%= userToEdit.getUserId() %>">

      <label for="firstName">First Name:</label>
      <input type="text" id="firstName" name="firstName" value="<%= userToEdit.getFirstName() %>">

      <label for="lastName">Last Name:</label>
      <input type="text" id="lastName" name="lastName" value="<%= userToEdit.getLastName() %>">

      <label for="userRole">User Role:</label>
      <select id="userRole" name="userRole">
        <option value="ADMIN" <%= (userToEdit.getRole() == UserRole.ADMIN) ? "selected" : "" %>>Admin</option>
        <option value="STAFF" <%= (userToEdit.getRole() == UserRole.STAFF) ? "selected" : "" %>>Staff</option>
        <option value="CUSTOMER" <%= (userToEdit.getRole() == UserRole.CUSTOMER) ? "selected" : "" %>>Customer</option>
      </select>

      <input type="submit" value="Save">
    </form>
  </section>
</main>
</body>
</html>

