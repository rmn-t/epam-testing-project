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

<div style="font-weight: bold; font-size:20px" id="time-left"></div>
<script type="text/javascript">
    let totalSeconds = '<%= (Long) session.getAttribute("timeLeft" +request.getParameter("id"))%>';
    let min = parseInt(totalSeconds/60);
    let sec = parseInt(totalSeconds%60);

    function checkTime() {
        document.getElementById("time-left").innerHTML = 'Time left: ' + min + ' min ' + sec + ' sec';
        if (totalSeconds <= 0 ) {
            setTimeout('document.testForm.submit()',1000);
        } else {
            totalSeconds = totalSeconds - 1;
            min = parseInt(totalSeconds/60);
            sec = parseInt(totalSeconds%60);
            setTimeout("checkTime()",1000);
        }
    }
    setTimeout("checkTime()",1);
</script>


    <a href="/epam/tests?page=1">Tests</a>
    <c:set var="currentTestId" value="${param.id}" scope="session"/>
    <br>
    <c:out value="${sessionScope.currentTestId}"></c:out>
    <h3> Test name:  <c:out value="${test.name}"></c:out> || Subject: <c:out value="${test.subject}"> </c:out> || Complexity: <c:out value="${test.complexity}"></c:out> </h3>
    <h4> Duration: <c:out value="${test.duration}"> </c:out> seconds </h4>

    <h4> Number of questions: <c:out value="${test.questionsNum}"> </c:out></h4>
    <hr>
    <h3> Questions </h3>

    <form action="/epam/take/test?id=${test.id}" method="POST" name="testForm">
    <hr>
        <c:forEach items="${requestScope['questions']}" var="question">
            <label for="${question.id}" name="${question.id}">${question.text}</label>
            <br>
            <br>
            <div id="${question.id}">
                <c:forEach items="${question.answers}" var="answer">
                    <label for="${answer.id}"><input type="checkbox" id="${answer.id}" name="${question.id}" value="${answer.id}"/>${answer.text}</label><br>
                </c:forEach>
            </div>
            <br>
        </c:forEach>
        <hr>
        <hr>
        <hr>
        <br>
        <input type="submit" value="Submit now">
    </form>

</body>
</html>