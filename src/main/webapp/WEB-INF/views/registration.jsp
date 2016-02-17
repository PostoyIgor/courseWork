<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
    <script src="http://code.jquery.com/jquery-1.10.2.min.js" type="text/javascript"></script>
    <script src="<c:url value='/resources/js/main.js'/>"></script>
    <link href="<c:url value='/resources/css/styles.css'/>" rel="stylesheet">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Hotel Booking</title>
</head>
<body>
<h3>Hotel Booking Services</h3>
<form:form method="POST" commandName="user" action="save-user" id="registerForm">
    <fieldset class="boxBody">
        <form:label path="login">Login:</form:label><br/>
        <form:input path="login"/><br/>
        <form:errors path="login" cssClass="error"/>

        <form:label path="firstName">First Name:</form:label><br/>
        <form:input path="firstName"/><br/>
        <form:errors path="firstName" cssClass="error"/>

        <form:label path="lastName">Last Name:</form:label><br/>
        <form:input path="lastName"/><br/>
        <form:errors path="lastName" cssClass="error"/>

        <form:label path="email">E-Mail:</form:label><br/>
        <form:input path="email"/><br/>
        <form:errors path="email" cssClass="error"/>
        <form:label path="password">Password:</form:label><br/>
        <form:password path="password"/><br/>
        <form:errors path="password" cssClass="error"/>
        <form:checkbox path="admin"/>
        <form:label path="admin">Admin</form:label>
        <input type="submit" id="btnLogin" value="Register">
    </fieldset>
</form:form>
</body>
</html>
