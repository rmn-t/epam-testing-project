<%@ page
language ="java"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"

%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Edit test</title>
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
</head>
<body>

</body>
</html>