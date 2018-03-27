<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.softserve.edu.constants.PagePaths" %>
<!DOCTYPE html>
<html>
<head>
    <title>${pageTitle}</title>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link type="text/css" rel="stylesheet" href="/css/materialize.min.css" media="screen,projection"/>
    <link type="text/css" rel="stylesheet" href="/css/app.css" media="screen,projection"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta charset="UTF-8">
</head>
<body>
<nav class="light-blue lighten-1" role="navigation">
    <div class="nav-wrapper container">
        <a id="logo-container" class="brand-logo">${pageTitle}</a>
        <c:if test="${not empty fullUserName}">
            <a href="#" data-target="mobile-sidenav" class="sidenav-trigger"><i class="material-icons">menu</i></a>
            <ul class="right hide-on-med-and-down">
                <li><a href="${PagePaths.USER_ITEMS}"><i class="material-icons left">list</i>Items</a></li>
                <li><a href="${PagePaths.USER_ACCOUNT}"><i class="material-icons left">account_circle</i>${fullUserName}</a></li>
                <li><a href="${PagePaths.LOGOUT}"><i class="material-icons left">exit_to_app</i>Logout</a></li>
            </ul>
        </c:if>
    </div>
</nav>
<c:if test="${not empty fullUserName}">
    <ul class="sidenav" id="mobile-sidenav">
        <li><a href="${PagePaths.USER_ITEMS}"><i class="material-icons left">list</i>Items</a></li>
        <li><a href="${PagePaths.USER_ACCOUNT}"><i class="material-icons left">account_circle</i>${fullUserName}</a></li>
        <li><a href="${PagePaths.LOGOUT}"><i class="material-icons left">exit_to_app</i>Logout</a></li>
    </ul>
</c:if>