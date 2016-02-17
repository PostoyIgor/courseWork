<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">
    <title>Hotel Description</title>
</head>
<body>
${user.login}
<div id="hotel-info">
    <p>Hotel : ${hotel.name}
        <c:forEach begin="1" end="${hotel.stars}"><img class="stars"
                                                       src="/resources/images/hotels/stars.png"/></c:forEach>
    </p>
</div>
<div class="right-panel">

</div>


<div class="main">


    <div>
        <table>
            <c:forEach items="${hotel.rooms}" var="room">
                <tr class="room-table">
                    <td>
                        <img onerror="this.onerror=null;this.src='../resources/images/rooms/noImage.jpg'"
                             src="/resources/images/rooms/${hotel.name}${room.id}.jpg">
                    </td>
                    <td>
                        Room id ${room.id}<br/>
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
    <div>
        <form id="add-room" method="post" action="/addRoom" enctype="multipart/form-data">
            <div>
                <input id="roomType" type="text" name="type" placeholder="Type" required>
            </div>
            <div>
                <input id="roomPrice" type="number" name="price" placeholder="Price" required>
            </div>
            <div>
                <input id="roomPlaces" name="seats" placeholder="Seats" type="number" required>
            </div>
            <div>
                <input type="file" name="image">
            </div>
            <input type="hidden" name="hotel" value="${hotel.id}">
            <div>
                <input id="roomSubmit" type="submit" value="Add Room">
            </div>
        </form>
    </div>
</div>

</body>
</html>
