<%@ page
    language ="java"
    contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    import="java.util.Arrays,java.util.List,com.epam.db.entities.Test"

%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Insert title here</title>
</head>
    <body>
        <c:out value="${loginStatus}"></c:out>
        <form action="login" method="POST">
            Enter username: <input type="text" name="username" value="admin"><br>
            Enter password: <input type="password" name="password" value="testqq"><br>
            <br>
            <input type="submit" value="login">
        </form>
        <br>

        <a href="register.jsp">Register</a>
    </body>
</html>