<%@ page
language ="java"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
import="java.util.Arrays,java.util.List,com.epam.db.entities.Test"

%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Creating new test</title>
    <form action="/epam/createTest" method="POST">
        <label for="testName">Name: </label>
        <input name="testName" id="testName">
        <br><br>
        <label for="subject">Subject: </label>
        <input name="subject" id="subject">
        <br>
        <br>
        <label for="complexity">Complexity:</label>
        <select name="complexity" id="complexity">
            <option value="easy">Easy</option>
            <option value="medium">Medium</option>
            <option value="hard">Hard</option>
            <option value="advanced">Advanced</option>
        </select>
        <br>
        <br>
        <label for="duration">Duration:</label>
        <input type="number" name="duration" id="duration">
        <br>
        <br>
        <input type="submit" value="Create test">
    </form>
</head>
<body>

</body>
</html>