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
        <%
            response.setHeader("Cache-Control","no-cache, no-store, must-revalidate"); // HTTP 1.1
            response.setHeader("Pragma","no-cache"); // HTTP 1.0
            response.setHeader("Expires","0"); // if using a proxy server
            if(session.getAttribute("username") == null) {
                response.sendRedirect("login.jsp");
            }
        %>
        <h1> Home page ...</h1>
        <h2>WELCOME ${username}</h2>
        <h3>Need to implement:</h3>
        <a href="/epam/createTest.jsp">Create new test</a>
        <br>
        <br>
        <a href="/epam/passed/tests?page=1">My tests</a>
        <br>
        <br>
        <a href="/epam/users?page=1&sort=username">Users</a>
        <br>
        <br>
        <form action="logout">
            <input type="submit" value="Logout">
        </form>
        </br>
        </br>
        Here is the list of available tests
        <br>
             <br>
        <form action="/epam/tests?page=1" method="POST">
            <label for="testsSorting">Sorting:</label>
            <select name="testsSorting" id="testsSorting">
                <option value="name ASC">Name ascending</option>
                <option value="name DESC">Name descending</option>
                <option value="complexity ASC">Complexity ascending</option>
                <option value="complexity DESC">Complexity descending</option>
                <option value="questionsNum ASC">Number of questions ascending</option>
                <option value="questionsNum DESC">Number of questions descending</option>
            </select>
            <input type="submit" value="Apply">
        </form>
        <br>

        <table border="1">
            <thead>
                <th>id</th>
                <th>name</th>
                <th>subject</th>
                <th>complexity</th>
                <th>duration</th>
                <th>number of tests</th>
                <th>details</th>
                <th>Pass</th>
            </thead>
            <tbody>
                <c:forEach items="${requestScope['tests']}" var="element">
                    <tr>
                        <td>${element.id}</td>
                        <td>${element.name}</td>
                        <td>${element.subject}</td>
                        <td>${element.complexity}</td>
                        <td>${element.duration}</td>
                        <td>${element.questionsNum}</td>
                        <td><a href="/epam/test?id=${element.id}">Details</a></td>
                        <td><a href="/epam/take/test?id=${element.id}">Start</a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <br>
        <br>
        <c:out value="${testsSorting}"></c:out>
        <br>
        <c:if test="${param.page eq 1}">
            <p>Previous</p>
        </c:if>
        <c:if test="${param.page gt 1}">
            <a href="/epam/tests?page=${param.page-1}">Previous</a>
        </c:if>
        <c:if test="${param.page eq lastPage}">
            <p>Next</p>
        </c:if>
        <c:if test="${param.page lt lastPage}">
            <a href="/epam/tests?page=${param.page+1}">Next</a>
        </c:if>

        <br>
        last page         <c:out value="${param.page}"></c:out>

    </body>
</html>