<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="grigoryev.servlets.User" %>
<%@ page import="java.util.List" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="simpleTags" prefix="formTags" %>

<!DOCTYPE html>
<html>
<head>
    <title>Title</title>
    <script src="http://code.jquery.com/jquery-1.10.2.js"></script>
    <style>
        body {
            background-color: lightblue;
            font-family: Verdana, Geneva, Arial, sans-serif;
            color: darkblue;
            font-size: 22px;
        }

        #table th, td {
            border: 1px solid black;
            border-collapse: collapse;
        }

        #table {
            margin: auto;
            width: 50%;
        }

        #exit form {
            margin: auto;
            width: 50%;
        }

        #table th, td {
            padding: 15px;
            text-align: left;
        }

        #table tr:hover {
            background-color: #f5f5f5;
        }

        input[type=text], select {
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

        #mainForm {
            margin: auto;
            width: 50%;
        }
    </style>
    <script>

        function loadCitiesAndCountries() {
            $.ajax('./json', {
                method: 'get',
                complete: function (data) {

                    var citiesAndCountries = JSON.parse(data.responseText);
                    console.log(citiesAndCountries);

                    var cities = citiesAndCountries.cities;
                    var resultCities = "";
                    for (var i = 0; i < cities.length; i++) {
                        resultCities += "<option value=\"" + cities[i] + "\"" + ">" + cities[i] + "</option>";
                    }
                    document.getElementById("city").innerHTML = resultCities;

                    var countries = citiesAndCountries.countries;
                    var resultCountries = "";
                    for (var i = 0; i < countries.length; i++) {
                        resultCountries += "<option value=\"" + countries[i] + "\"" + ">" + countries[i] + "</option>";
                    }
                    document.getElementById("country").innerHTML = resultCountries;
                }
            })
        }

        $(loadCitiesAndCountries());

        function validate() {
            var name = document.getElementById("name").value;
            var login = document.getElementById("login").value;
            var email = document.getElementById("email").value;
            var password = document.getElementById("password").value;
            var city = document.getElementById("city").value;
            var country = document.getElementById("country").value;
            var role = document.getElementsByName("role")[0].value;
            var action = document.getElementById("selectAction").value;

            var isAvailable = true;
            if (action != "selectAll" && action != "insert" && login == "") {
                isAvailable = false;
                alert("You shoult input login!");
            } else if (action == "insert") {
                if (name == "" || email == "" || password == "" || city == ""
                    || country == "") {
                    isAvailable = false;
                    alert("You should fill out all the input fields")
                }
            }
            return isAvailable;
        }
    </script>
</head>
<body>
<div id="header">
    <h1 align="center">User Managing Page</h1>
</div>

<div id="mainForm">
    <form method="post"
          action="${pageContext.servletContext.contextPath}/" onsubmit="return validate()">
        <p>
            Name:<br>
            <input id="name" type="text" name="name"><br>
            Login:<br>
            <input id="login" type="text" name="login"><br>
            Email:<br>
            <input id="email" type="text" name="email"><br>
            Password:<br>
            <input id="password" type="text" name="password"><br>

            <button type="button" onclick="return loadCitiesAndCountries()">Update Lists of Cities and Countries by AJAX</button>
            City:
            <select id=city name="city" size="1"></select>
            <br>

            Country:
            <select id=country name="country" size="1"></select>
            <br>

            Role:<br>
            <formTags:select name="role" size="1"
                             optionList="${applicationScope.roleList}"/><br/>

            Select type of action:
            <select id=selectAction name="actionType" size="1">
                <option value="insert">INSERT</option>
                <option value="update">UPDATE</option>
                <option value="delete">DELETE</option>
                <option value="select">SELECT</option>
                <option value="selectAll">SELECT ALL</option>
            </select>
            <br>
        <div style="text-align: center;">
            <input type="submit" value="submit action">
        </div>
        </p>
    </form>
</div>

<div id="exit">
    <form method="get"
          action="${pageContext.servletContext.contextPath}/signin">
        Exit: <input type="submit" value="exit">
    </form>
</div>

<div id="operationResult">${requestScope.operationResult}</div>

<div id="table">
    <table>
        <tr>
            <th>name</th>
            <th>login</th>
            <th>email</th>
            <th>city</th>
            <th>country</th>
            <th>create date</th>
            <th>role</th>
        </tr>
        <c:forEach var="user" items="${users}">
            <tr>
                <td><c:out value="${user.name}"/></td>
                <td><c:out value="${user.login}"/></td>
                <td><c:out value="${user.email}"/></td>
                <td><c:out value="${user.city}"/></td>
                <td><c:out value="${user.country}"/></td>
                <td><c:out value="${user.createDate}"/></td>
                <td><c:out value="${user.role.name}"/></td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
