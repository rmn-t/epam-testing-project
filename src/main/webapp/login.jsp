<%@ page language ="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Insert title here</title>
</head>
    <body>
        <form action="login" method="POST">
            Enter username: <input type="text" name="username"><br>
            Enter password: <input type="password" name="password"><br>
            <br>
            <input type="submit" value="login">

        </form>
        <br>

        <a href="register.jsp">Register</a>
    </body>
</html>