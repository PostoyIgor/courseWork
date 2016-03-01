<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
    <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">
    <script src="http://code.jquery.com/jquery-1.10.2.min.js" type="text/javascript"></script>
    <script src="<c:url value="/resources/js/divPopUp.js" />"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Hotel Booking Service</title>
</head>
<body>
<c:choose>
    <c:when test="${user.login==null}">
        <a class="menuLink" href="/registration">Sign In</a><a href="#" class="menuLink" id="showpopup">Log In</a>
        <div id="popup">
            <div>
                <form:form method="POST" commandName="user" action="check-user" id="loginForm">
                    <h4>Enter to the site<br/></h4>
                    <form:label path="login">Login:</form:label><br/>
                    <form:input path="login"/>
                    <form:errors path="login" cssClass="error"/><br/>
                    <form:label path="password">Password:</form:label><br/>
                    <form:password path="password"/>
                    <form:errors path="password" cssClass="error"/><br/>

                    <input type="submit" id="btnLogin" value="Login">
                    <span id="ajax-error"></span>
                </form:form>
            </div>
            <div class="close">[X]</div>
        </div>
        <div id="back"></div>
    </c:when>
    <c:otherwise>
        <a class="menuLink" href="/profile">${user.login} Profile</a>
        <a class="menuLink" href="/logout">Logout</a>
    </c:otherwise>
</c:choose>
<div class="right-panel">
    <div class="filter">
        <span><h5>Filtering Room</h5></span>
        <form id="filter-form" action="search" onsubmit="return checkDate()">
            <label for="city">City</label>
            <input id="city" type="text" name="city">
            <label for="hotel">Hotel</label>
            <input id="hotel" type="text" name="hotel">
            <label>Hotel Rank</label>
            <select form="filter-form" name="stars">
                <option></option>
                <option value="1">1</option>
                <option value="2">2</option>
                <option value="3">3</option>
                <option value="4">4</option>
                <option value="5">5</option>
            </select>
            <label id="fromDate">From date</label>
            <input type="date" name="fromDate" class="date" data-date-split-input="true" required>
            <label id="toDate">To date</label>
            <input type="date" name="toDate" class="date" data-date-split-input="true" required>

            <label id="numOfTravelers">Number of travelers</label>
            <input type="number" name="numOfTravelers" min="1" max="4" required>
            <input type="submit" value="Search" name="searchSubmit">
        </form>
    </div>
</div>
<div id="hotel-list">
    <table class="table-hotels">
        <c:forEach items="${hotels}" var="hotel">
            <tr>
                <td>
                    <div>
                        <img class="hotel-img"
                             onerror="this.onerror=null;this.src='../resources/images/rooms/noImage.jpg'"
                             src="/resources/images/hotels/${hotel.id}.jpg">
                    </div>
                </td>
                <td>
                    <div>
                        <a href="/hotel/${hotel.id}">${hotel.name}</a> in ${hotel.city}
                        Available rooms: ${hotel.rooms.size()}
                    </div>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>

</body>
</html>
