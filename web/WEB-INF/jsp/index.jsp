<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <title>Status Wall</title>

        <link rel="stylesheet" type="text/css" href="style.css">
        <link rel="stylesheet" type="text/css" href="bootstrap.css">
        <!-- <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script> -->
        <script type="text/javascript" src="updaterjs.js"></script>
    </head>

    <body>
        <div id="wrap">       
            <h1 id="h1">Status Wall</h1>

            <form class="form-inline" id="loginForm" onsubmit="return false;">
                <div class="control-group">
                    <c:set var="userNameString" value="${myName}"></c:set>
                    <c:set var="userColorString" value="${myColor}"></c:set>
                    <jsp:useBean id="userNameString" type="java.lang.String" />
                    <jsp:useBean id="userColorString" type="java.lang.String" />
                    <input id="name" type="text" class="input-small" placeholder="${myName}" <c:if test='<%=!userNameString.equals("Username") %>'>value="${myName}"</c:if>>
                    <!-- <input id="status" type="text" class="input-small" placeholder="status"> -->
                    <select id="status" >
                        <option>${myStatus}</option>
                        <option>Home</option>
                        <option>School</option>
                        <option>Gaming</option>
                        <option>Party</option>
                        <option>Sleep</option>
                    </select>
                    <input id="color" type="text" class="input-small" placeholder="${myColor}" <c:if test='<%=!userColorString.equals("99FFAA") %>'>value="${myColor}"</c:if>>
                    <button type="submit" class="btn btn-primary" onclick="addMyInfo(this.form);">update</button>
                </div>
            </form>

            <div id="people">

            </div>


        </div>
        <footer>by Ali Abdul-Karim, Nico Hämäläinen, Henri Levälampi @ 2013</footer>

        <script>
            function checkForUpdates() {
                refreshClients();
            }
            checkForUpdates();
            updateTimer = setInterval(checkForUpdates, 10000);
            //document.ready(function() {
                //toCreateDiv("ali", "zzz", "00FF99");
                //toCreateDiv("nico", "häxing", "99FF00");
                //toCreateDiv("heq", "häxing", "0099FF");
            //});
        </script>

    </body>
</html>