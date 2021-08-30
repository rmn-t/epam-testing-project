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
    <title> <c:out value="${requestScope['test'].name}"></c:out>  </title>
</head>
<body>
    <a href="tests?page=1">Tests</a>
    <c:set var="currentTestId" value="${param.id}" scope="session"/>
    <br>
    <c:out value="${sessionScope.currentTestId}">abc</c:out>
    <h1> Test name:  <c:out value="${test.name}"></c:out>  </h1>
        <a href="/epam/editTest?id=${sessionScope.currentTestId}">Edit test</a>
        <br>
        <br>
        <form action="/epam/delete/test?id=${test.id}" method="POST">
            <input type="submit" value="Delete test">
        </form>
    <h2> Subject: <c:out value="${test.subject}"> </c:out></h2>
    <h2> Complexity: <c:out value="${test.complexity}"> </c:out></h2>
    <h2> Duration: <c:out value="${test.duration}"> </c:out> seconds </h2>
    <h2> Number of questions: <c:out value="${test.questionsNum}"> </c:out></h2>
    <hr>
    <h3> Questions </h3>
    <br>
    <a href="/epam/questionForm.jsp">Add question</a>
    <br>
    <c:forEach items="${requestScope['questions']}" var="question">
        <h4> Text : ${question.text} | Id : ${question.id} | <a href="/epam/edit/question?id=${question.id}">Edit</a>
            <form action="/epam/delete/question?id=${question.id}" method="POST">
                <input type="submit" value="Delete">
            </form>
        </h4>
            <c:forEach items="${question.answers}" var="answer">
                <p> Answer text : ${answer.text} || Answer correctness : ${answer.isCorrect} </p>
            </c:forEach>
    </c:forEach>


</body>
</html>