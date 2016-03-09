<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">
    <script src="http://code.jquery.com/jquery-1.10.2.min.js" type="text/javascript"></script>
    <title>User Reservation</title>
</head>
<body>
<div id="hotel-list">
    <table class="table-booking">
        <c:forEach items="${bookings}" var="booking">
            <tr>
                <td>
                    <div>
                        From ${booking.startDate}
                    </div>
                </td>
                <td>
                    <div>
                        To ${booking.endDate}<br/>
                    </div>
                </td>
                <td>
                    <div>
                        Status ${booking.status}
                    </div>
                </td>
                <td>
                    <div>
                        Price: ${booking.room.price}$
                    </div>
                </td>
                <td>
                    <div>
                        Hotel ${booking.room.hotel.name}
                    </div>
                </td>
                <td>
                    <div class="comment" ><a href="/comment/${booking.room.hotel.id}">Comment</a></div>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
