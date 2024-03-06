<%-- 
    Document   : attendance_taking.jsp
    Created on : Mar 2, 2024, 6:25:52 PM
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
        <div>
            <p>Student group: ${requestScope.session.group.name}</p>
            <p>Course: ${requestScope.session.group.course.name}
                (${requestScope.session.group.course.code})</p>
            <p>Room: ${requestScope.session.room.building.id} 
                - ${requestScope.session.room.name}</p>
            <p>Slot: ${requestScope.session.slot.id}</p>
            <p>Date: <fmt:formatDate pattern="dd/MM/yyyy" 
                     value="${requestScope.session.date}"/></p>
        </div>
        <form action="attendance_taking" method="POST">
            <input type="hidden" name="sessionID" value="${requestScope.session.id}">
            <table border="1px">
                <tr>
                    <th>Index</th>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Image</th>
                    <th>Status</th>
                    <th>Note</th>
                    <th>Record Time</th>
                </tr>
                <c:forEach items="${requestScope.attList}" var="att" varStatus="loop">
                    <tr>
                        <td>${loop.count}</td>
                        <td>${att.student.id}</td>
                        <td>${att.student.name}</td>
                        <td>
                            <img src="${att.student.image}" style="height:146px;width:111px"/>
                        </td>
                        <td>
                            <input type="radio" 
                                   <c:if test="${att.status == false}">
                                       checked="checked"
                                   </c:if>
                                   name="status${att.student.id}" value="false"/> Absent
                            <input type="radio" 
                                   <c:if test="${att.status == true}">
                                       checked="checked"
                                   </c:if>
                                   name="status${att.student.id}" value="true"/> Present
                        </td>
                        <td>
                            <input type="text" name="note${att.student.id}" value="${att.note}">
                        </td>
                        <td>
                            <fmt:formatDate pattern="dd/MM/yyyy hh:mm:ss" 
                                        value="${att.recordTime}"/>
                        </td>
                    </tr>
                </c:forEach>
            </table>
            <input type="submit" value="Save">
        </form>
    </body>
</html>