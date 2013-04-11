<%-- 
    Document   : userxml
    Created on : Apr 10, 2013, 8:50:19 PM
    Author     : Kone
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Current users</title>
    </head>
    <body>
        <h1>clients: ${clients.size}</h1>
        <div id="client-field">
            <c:forEach items="${clients.clients}" var="cData">
            <div id="${cData.name}" style="float:left; border:1px solid black; padding: 0.2em; margin-right: 2.5em; background-color: #${cData.color}">${cData.name}: <br /> ${cData.status}</div>
            </c:forEach>
        </div>
    </body>
</html>
