<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <style>
        body {
            font-family: Verdana, Geneva, Arial, sans-serif;
            color: darkblue;
            font-size: 22px;
        }

        form {
            margin: auto;
            width: 50%;
            height: 50%;
        }

        input[type=text], input[type=password] {
            width: 100%;
            padding: 12px 20px;
            margin: 8px 0;
            display: inline-block;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }

        input[type=submit] {
            width: 100%;
            background-color: #4CAF50;
            color: white;
            padding: 14px 20px;
            margin: 8px 0;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        input[type=text]:focus, input[type=password]:focus {
            background-color: lightblue;
        }

        input[type=submit]:hover {
            background-color: #45a049;
        }

        div.mainForm {
            border-radius: 5px;
            background-color: #f2f2f2;
            padding: 20px;
        }
    </style>
</head>
<body>
<c:if test="${error != ''}">
    <div style="background-color: red">
        <c:out value="${error}"/>
    </div>
</c:if>
<div class="mainForm">
    <form method="post"
          action="${pageContext.servletContext.contextPath}/signin">
        Enter your login and password:
        <p style="background: greenyellow;">
            Login: <input type="text" name="login"><br/>
            Password: <input type="password" name="password"><br/>
            <input type="submit" value="submit">
        </p>
    </form>
</div>
</body>
</html>
