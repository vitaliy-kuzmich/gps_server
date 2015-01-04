<!doctype html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<html lang="en">

<head>
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <link href="css/jquery-ui.css" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet">
    <script>var url_prefix = "${sessionScope.path_suffix}";</script>


    <script src="js/jquery-2.1.1.min.js"></script>
    <script src="js/jquery-ui.min.js"></script>
    <script src="js/knockout-3.2.0.js"></script>
    <script src="https://maps.googleapis.com/maps/api/js?v=3.exp"></script>
    <script src="js/map.js"></script>
    <script src="js/script.js"></script>


</head>
<body>

<form id="addUserForm" data-bind="submit: $root.addNewUser">
    <fieldset>
        <label for="username">Login</label>
        <input type="text" name="username" id="username" class="text ui-widget-content ui-corner-all">
        <label for="password">Password</label>
        <input type="password" name="password" id="password" value="xxxxxxx"
               class="text ui-widget-content ui-corner-all">
        <!-- Allow form submission with keyboard without duplicating the dialog button -->
        <input type="submit" tabindex="-1" style="position:absolute; top:-1000px">
    </fieldset>
</form>


<fmt:bundle basename="messages">
    <div class="ui-widget" id="users-contain">
        <h1>Existing Users:</h1>
        <table class="ui-widget ui-widget-content" id="users">
            <thead>
            <tr class="ui-widget-header ">
                <th><fmt:message key="user.name"/></th>
                <th><fmt:message key="user.enabled"/></th>
                <th><fmt:message key="user.role"/></th>
                <th><fmt:message key="user.remove"/></th>
                <th><fmt:message key="user.map"/></th>
            </tr>
            </thead>
            <tbody data-bind="foreach: users">
            <tr>
                <td data-bind="text: username"></td>
                <td data-bind="text: enabled"></td>
                <td data-bind="text: authorities[0].authority"></td>
                <td><a href="#" data-bind="click: $root.deleteUser">Remove</a></td>
                <td><a href="#" data-bind="click: $root.showOnMap">Open</a></td>
            </tr>
            </tbody>
        </table>
        <button id="create-user" data-bind="click: $root.openDialog"><fmt:message key="user.create"/></button>
    </div>
</fmt:bundle>
<div class="mapTemplate" style="display: none">
    Begin date(month/day/year): <input type="text" class="begin_date">End date: <input type="text" class="end_date">
    <button class="ok_btn">Search</button>
    <div class="mapCanvas" style="width: 100%; height: 93%;">
    </div>
</div>
</body>
</html>