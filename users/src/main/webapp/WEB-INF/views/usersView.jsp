<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="grigoryev.models.User" %>
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

        function validate() {
            var id = document.getElementById("id").value;
            var name = document.getElementById("name").value;
            var login = document.getElementById("login").value;
            var email = document.getElementById("email").value;
            var password = document.getElementById("password").value;
            var role = document.getElementsByName("role")[0].value;
            var action = document.getElementById("selectAction").value;
            var streetName = document.getElementById("street_name").value;
            var houseNo = document.getElementById("house_no").value;
            var apartmentNo = document.getElementById("apartment_no").value;
            var musicTypes = getMusicCheckedCheckBoxes();

            var isAvailable = true;
            console.log(action);
            if (action == "select_by_id" || action == "update" || action == "delete") {
                if (id == "") {
                    isAvailable = false;
                    alert("You should input id to perform " + action);
                } else {
                    isAvailable = validateId(id);
                }
            } else if (action == "insert") {
                if (name == "" || login == "" || email == ""
                    || password == "" || streetName == ""
                    || houseNo == "" || apartmentNo == "" || musicTypes.length == 0) {
                    isAvailable = false;
                    alert("You should fill out all the input fields")
                }
            } else if (action == "select_by_login") {
                if (login == "") {
                    isAvailable = false;
                    alert("You should input login!");
                }
                document.getElementById("id").value = "";
            } else if (action == "select_by_role") {
                if (role == "") {
                    isAvailable = false;
                    alert("You should input role!");
                }
                document.getElementById("id").value = "";
            } else if (action == "select_by_music_type") {
                if (musicTypes.length == 0) {
                    isAvailable = false;
                    alert("You should choose music type!");
                }
                document.getElementById("id").value = "";
            } else if (action == "select_by_address") {
                if (streetName == "" || houseNo == "" || apartmentNo == "") {
                    isAvailable = false;
                    alert("You should fill out all address fields!");
                }
                document.getElementById("id").value = "";
            }
            return isAvailable;
        }

        function validateId(id) {
            var reg = /[\d]/i;
            var isAvailable = true;
            if (!reg.test(id)) {
                isAvailable = false;
                alert("id should be a number!");
            }
            return isAvailable;
        }

        function getMusicCheckedCheckBoxes() {
            var checkboxes = document.getElementsByClassName('musicCheckBoxes');
            var checkboxesChecked = [];
            for (var index = 0; index < checkboxes.length; index++) {
                if (checkboxes[index].checked) {
                    checkboxesChecked.push(checkboxes[index].value);
                }
            }
            return checkboxesChecked;
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
            ID:<br>
            <input id="id" type="text" name="id"><br>
            Name:<br>
            <input id="name" type="text" name="name"><br>
            Login:<br>
            <input id="login" type="text" name="login"><br>
            Email:<br>
            <input id="email" type="text" name="email"><br>
            Password:<br>
            <input id="password" type="text" name="password"><br>

            Address:<br>
            Street name:<br>
            <input id="street_name" type="text" name="street_name"><br>
            House number:<br>
            <input id="house_no" type="text" name="house_no"><br>
            Apartment number:<br>
            <input id="apartment_no" type="text" name="apartment_no"><br>

            Role:<br>
            <formTags:select name="role" size="1"
                             optionList="${applicationScope.roleList}"/><br/>

            Music types:<br>
            <formTags:checkBoxes checkBoxList="${applicationScope.musicTypeList}"/><br/>

            Select type of action:
            <select id="selectAction" name="actionType" size="1">
                <option value="insert">INSERT</option>
                <option value="update">UPDATE</option>
                <option value="delete">DELETE</option>
                <option value="select_by_id">SELECT BY ID</option>
                <option value="select_by_login">SELECT BY LOGIN</option>
                <option value="select_by_role">SELECT BY ROLE</option>
                <option value="select_by_music_type">SELECT BY MUSIC TYPE</option>
                <option value="select_by_address">SELECT BY ADDRESS</option>
                <option value="select_all">SELECT ALL</option>
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

<div id="table">
    <table>
        <tr>
            <th>id</th>
            <th>name</th>
            <th>login</th>
            <th>email</th>
            <th>role name</th>
            <th>street</th>
            <th>house no</th>
            <th>apartment no</th>
            <th>music types</th>
        </tr>
        <c:forEach var="user" items="${users}">
            <tr>
                <td><c:out value="${user.id}"/></td>
                <td><c:out value="${user.name}"/></td>
                <td><c:out value="${user.login}"/></td>
                <td><c:out value="${user.email}"/></td>
                <td><c:out value="${user.role.name}"/></td>
                <td><c:out value="${user.address.streetName}"/></td>
                <td><c:out value="${user.address.houseNo}"/></td>
                <td><c:out value="${user.address.apartmentNo}"/></td>
                <td>
                    <ul>
                        <c:forEach var="type" items="${user.musicTypes}">
                            <li>
                                <c:out value="${type.title}"/>
                            </li>
                        </c:forEach>
                    </ul>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>