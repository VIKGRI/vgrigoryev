<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="grigoryev.servlets.User" %>
<%@ page import="java.util.List" %>

<html>
<head>
    <title>Title</title>
    <style>
        table, th, td {
            border: 1px solid black;

            border-collapse: collapse;
            cellpadding: "1";
            celspacing: "1";
        }
    </style>
</head>
<body>
<h1 align="center">User Managing Page</h1>
<form method="post"
      action="Dispatch">
    Select type of action:
    <p>
        Name:<br>
        <input type="text" name="name"><br>
        Login:<br>
        <input type="text" name="login"><br>
        Email:<br>
        <input type="text" name="email"><br>
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
        <input type="submit">
      </div>
      </p>
    </form>
    <div> <%= request.getAttribute("operationResult") %></div><br/><br/>
    <table>
        <tr>
            <th>name</th>
            <th>login</th>
            <th>email</th>
            <th>create date</th>
        </tr>
            <%for (User user : (List<User>) request.getAttribute("users")) { %>
               <tr>
                   <td><%=user.getName()%></td>
                   <td><%=user.getLogin()%></td>
                   <td><%=user.getEmail()%></td>
                   <td><%=user.getCreateDate()%></td>
               </tr>
            <% } %>
    </table>
</body>
</html>
