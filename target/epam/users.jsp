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
            <a href="/epam/tests?page=1">Tests</a>
                    <br>
        <br>
        <form action="logout">
            <input type="submit" value="Logout">
        </form>
        </br>
        </br>
        Here is the list of USERS
        <br>
     <br>
        <form action="/epam/users?page=1" method="POST">
            <label for="sort">Sorting:</label>
            <select name="sort" id="sort">
                <option value="username">Username</option>
                <option value="status">Status</option>
            </select>
            <input type="submit" value="Apply">
        </form>
        <br>

        <table border="1">
            <thead>
                <th>id</th>
                <th>username</th>
                <th>new password</th>
                <th>role</th>
                <th>status</th>
                <th>action</th>
            </thead>
            <tbody>
                <c:forEach items="${requestScope['users']}" var="element">
                    <tr>
                        <form method="POST" action="/epam/edit/user?id=${element.id}&page=1&sort=${param.sort}">
                            <td><input type="text" name="id" value="${element.id}" readonly></td>
                            <td><input type="text" name="username" value="${element.username}" readonly></td>
                            <td><input type="text" name="newPass" value="${element.password}" required></td>
                            <td>
                                <select name="role" id="role">
                                    <option value="admin" ${element.role == 'admin' ? 'selected' : ''}>admin</option>
                                    <option value="user" ${element.role == 'user' ? 'selected' : ''}>user</option>
                                </select>
                            </td>
                            <td>
                                <select name="status" id="status">
                                    <option value="active" ${element.status == 'active' ? 'selected' : ''}>active</option>
                                    <option value="banned" ${element.status == 'banned' ? 'selected' : ''}>banned</option>
                                </select>
                            </td>
                            <td><input type="submit" value="Save"></td>
                            <input type="hidden" name="salt" value="${element.salt}">
                            <input type="hidden" name="oldPass" value="${element.password}">
                        </form>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <br>
        <hr>
        <hr>
        <br>
        <c:out value="${userSort}"></c:out>
        <br>
        <c:if test="${param.page eq 1}">
            <p>Previous</p>
        </c:if>
        <c:if test="${param.page gt 1}">
            <a href="/epam/users?page=${param.page-1}&sort=${param.sort}">Previous</a>
        </c:if>
        <c:if test="${param.page eq lastPage}">
            <p>Next</p>
        </c:if>
        <c:if test="${param.page lt lastPage}">
            <a href="/epam/users?page=${param.page+1}&sort=${param.sort}">Next</a>
        </c:if>

        <br>
        last page         <c:out value="${param.page}"></c:out>

    </body>
</html>