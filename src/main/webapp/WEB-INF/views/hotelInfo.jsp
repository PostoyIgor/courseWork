<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">
    <title>Hotel Description</title>
</head>
<body>
<div id="hotel-info">
    <p>Hotel : ${hotel.name}
        <c:forEach begin="1" end="${hotel.stars}"><img class="stars"
                                                       src="/resources/images/hotels/stars.png"/></c:forEach>

</div>
<div class="right-panel">
</div>
<div>
    <div class="main">
        <table>
            <c:forEach items="${hotel.rooms}" var="room">
                <tr class="room-table">
                    <td>
                        <img onerror="this.onerror=null;this.src='../resources/images/rooms/noImage.jpg'"
                             src="/resources/images/rooms/${hotel.name}${room.id}.jpg">
                    </td>
                    <td>
                        <a href="${hotel.id}/roomDetails/${room.id}">Room № ${room.number}</a><br/>
                        Type is ${room.type}<br/>
                        Places: ${room.seats}<br/>
                        Price: ${room.price} &#36;
                    </td>
                    <td>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>
<c:choose>
    <c:when test="${user.hotelOwner and hotel.user.id eq user.id}">
        <div class="add-room-div">
            <form id="add-room" method="post" action="/addRoom" enctype="multipart/form-data">
                <input id="roomNumber" name="number" type="number" placeholder="№" required><br/>
                <input id="roomType" type="text" name="type" placeholder="Type" required><br/>
                <input id="roomPrice" type="number" name="price" placeholder="Price" required><br/>
                <textarea id="roomDescription" name="description" placeholder="Description"></textarea><br/>
                <input id="roomPlaces" name="seats" placeholder="Seats" type="number" required><br/>
                <input type="file" name="image"><br/>
                <input type="hidden" name="hotel" value="${hotel.id}">
                <input id="roomSubmit" type="submit" value="Add Room">
            </form>
        </div>
    </c:when>
    <c:otherwise>
    </c:otherwise>
</c:choose>
</body>
</html>
