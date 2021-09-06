<%@ page language ="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="my" uri="/tld/MyTagDescriptor.tld"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
    <style>
    </style>
    <title>///Edit test</title>
</head>
    <body>
        <fmt:setLocale value="${cookie.lang.value}"/>
        <fmt:bundle basename="messages">
        <%
            response.setHeader("Cache-Control","no-cache, no-store, must-revalidate"); // HTTP 1.1
            response.setHeader("Pragma","no-cache"); // HTTP 1.0
            response.setHeader("Expires","0"); // if using a proxy server
        %>

        <c:import url="/views/templates/navbar.jsp"></c:import>


            <c:out value="${test}"></c:out>
            <form action="/epam/editTest?id=${test.id}" method="POST">
                <label for="name">Name: </label>
                <input name="name" id="name" value="${test.name}">
                <br><br>
                <label for="subject">Subject: </label>
                <input name="subject" id="subject" value="${test.subject}">
                <br>
                <br>
                <label for="complexity">Complexity:</label>
                <select name="complexity" id="complexity">
                    <option value="easy" ${test.complexity == 'easy' ? 'selected' : ''}>Easy</option>
                    <option value="medium" ${test.complexity == 'medium' ? 'selected' : ''}>Medium</option>
                    <option value="hard" ${test.complexity == 'hard' ? 'selected' : ''}>Hard</option>
                    <option value="advanced" ${test.complexity == 'advanced' ? 'selected' : ''}>Advanced</option>
                </select>
                <br>
                <br>
                <label for="duration">Duration:</label>
                <input type="number" name="duration" id="duration" value="${test.duration}">
                <br>
                <br>
                <input type="submit" value="Save changes">
            </form>
        </fmt:bundle>
    </body>
</html>