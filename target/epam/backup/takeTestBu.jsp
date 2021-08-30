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
    <script>
        <%
            String clock = "123";
        %>
        let timeout = '<%= (Long) session.getAttribute("timeLeft" +request.getParameter("id"))%>'
        function timer() {
            console.log(timeout);
            if( --timeout > 0 ) {
                document.forma.clock.value = timeout;
                window.setTimeout( "timer()", 1000 );
            } else {
                document.forma.clock.value = "Time over";
                ///disable submit-button etc
            }
        }
    </script>
    <a href="tests?page=1">Tests</a>
    <c:set var="currentTestId" value="${param.id}" scope="session"/>
    <br>
    <c:out value="${sessionScope.currentTestId}"></c:out>
    <h3> Test name:  <c:out value="${test.name}"></c:out> || Subject: <c:out value="${test.subject}"> </c:out> || Complexity: <c:out value="${test.complexity}"></c:out> </h3>
    <h4> Duration: <c:out value="${test.duration}"> </c:out> seconds </h4>

    <form action="#" name="forma">
        Seconds remaining: <input type="text" name="clock" value="<%=clock%>" style="border:0px solid white">
        </form>
    <script>
        timer();
    </script>

    <h4> Number of questions: <c:out value="${test.questionsNum}"> </c:out></h4>
    <hr>
    <h3> Questions </h3>

    <form action="/epam/take/test?id=${test.id}" method="POST">
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