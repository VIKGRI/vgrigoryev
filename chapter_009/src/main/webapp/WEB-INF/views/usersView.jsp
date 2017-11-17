<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="grigoryev.servlets.User" %>
<%@ page import="java.util.List" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="simpleTags" prefix="formTags" %>

<html>
<head>
    <title>Title</title>
    <style>
        table, th, td {
            border: 1px solid black;
            border-collapse: collapse;
            cellpadding: "1";
            cellspacing: "1";
        }
    </style>
</head>
<body>
<h1 align="center">User Managing Page</h1>
<form method="post"
             action="${pageContext.servletContext.contextPath}/">
    Select type of action:
    <p>
        Name:<br>
        <input type="text" name="name"><br>
        Login:<br>
        <input type="text" name="login"><br>
        Email:<br>
        <input type="text" name="email"><br>
        Password:<br>
        <input type="text" name="password"><br>
        Role:<br>

        <formTags:select name="role" size="1"
                         optionList="${applicationScope.roleList}" /><br/>

        Action:
        <select name="actionType" size="1">
            <option value="insert">INSERT</option>
            <option value="update">UPDATE</option>
            <option value="delete">DELETE</option>
            <option value="select">SELECT</option>
            <option value="selectAll">SELECT ALL</option>
        </select>
        <br><br>
    <div style="text-align: center;">
        <input type="submit" value="submit action">
    </div>
    </p>
</form>
<form method="get"
      action="${pageContext.servletContext.contextPath}/signin">
    Exit: <input type="submit" value="exit">
</form>
<div>${requestScope.operationResult}</div><br/><br/>
<table>
    <tr>
        <th>name</th>
        <th>login</th>
        <th>email</th>
        <th>create date</th>
        <th>role</th>
    </tr>
    <c:forEach var="user" items="${users}" >
        <tr>
            <td><c:out value="${user.name}"/></td>
            <td><c:out value="${user.login}"/></td>
            <td><c:out value="${user.email}"/></td>
            <td><c:out value="${user.createDate}"/></td>
            <td><c:out value="${user.role.name}"/></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
