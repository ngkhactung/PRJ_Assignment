<%-- 
    Document   : timetable
    Created on : Feb 29, 2024, 5:16:12 PM
    Author     : Admin
--%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <table border = "1px">
            <tr>
                <td rowspan="2">
                    <form id="yearForm" action="timetable" method="GET">
                        <select name="year" onchange="document.getElementById('yearForm').submit()">
                            <c:forEach begin="2021" end="2025" var="i">
                                <option 
                                    <c:if test="${requestScope.year == i}">
                                        selected
                                    </c:if>
                                    value="${i}">${i}</option>
                            </c:forEach>
                        </select> <br>
                    </form>

                    <form id="dayForm" action="timetable" method="GET">
                        <select name="startDay" onchange="document.getElementById('dayForm').submit()">
                            <c:forEach items="${requestScope.weeks}" var="week">
                                <option 
                                    <c:if test="${requestScope.startDay == week.startDay}">
                                        selected
                                    </c:if>
                                    value="${week.startDay}">${week.from} To ${week.to}</option>
                            </c:forEach>
                        </select>
                    </form>
                </td>

                <c:forEach items="${requestScope.daysOfWeek}" var="day">
                    <td>
                        <fmt:formatDate pattern="E" value="${day}"/>
                    </td>
                </c:forEach>
            </tr>

            <tr>
                <c:forEach items="${requestScope.daysOfWeek}" var="day">
                    <td>
                        <fmt:formatDate pattern="dd/MM" value="${day}"/>
                    </td>
                </c:forEach>
            </tr>
            <c:forEach items="${requestScope.slots}" var="slot">
                <tr>
                    <td>Slot ${slot.id}</td>
                    <c:forEach items="${requestScope.daysOfWeek}" var="day">
                        <td>
                            <c:forEach items="${requestScope.sessionList}" var="session">
                                <c:if test="${session.slot.id == slot.id && session.date == day}">
                                    <c:if test="${session.isTaken}">
                                        <a href="attendance_review?sessId=${session.id}">
                                            ${session.group.name} - ${session.group.course.code} <br>
                                            at ${session.room.building.id}-${session.room.name} <br>
                                        </a>
                                        <p style="color: green">(Taken)</p>
                                    </c:if>
                                    <c:if test="${!session.isTaken}">
                                        <a href="attendance_taking?sessId=${session.id}">
                                            ${session.group.name} - ${session.group.course.code} <br>
                                            at ${session.room.building.id}-${session.room.name} <br>
                                        </a>
                                        <p style="color: red">(Not yet)</p>
                                    </c:if> <br>
                                    ${session.slot.start} - ${session.slot.end}
                                </c:if>
                            </c:forEach>
                        </td>
                    </c:forEach>
                </tr>
            </c:forEach>
        </table>
        
        <button onclick="window.location.href = 'grade_taking'">Take Grade</button>
    </body>
</html>