<%@ page
    language ="java"
    contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    import="java.util.Arrays,java.util.List,com.epam.db.entities.Test"

%>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Insert title here</title>
</head>
    <body>
        <h1> Home page ...</h1>
        <%
            response.setHeader("Cache-Control","no-cache, no-store, must-revalidate"); // HTTP 1.1
            response.setHeader("Pragma","no-cache"); // HTTP 1.0
            response.setHeader("Expires","0"); // if using a proxy server
            if(session.getAttribute("username") == null) {
                    response.sendRedirect("login.jsp");
            }
        %>
        WELCOME ${username}
        </br>
        <form action="logout">
            <input type="submit" value="logout">
        </form>
        </br>
        </br>
        Here is the list of available tests
        <table>
            <thead>
                <th>id</th>
                <th>name</th>
                <th>subject</th>
                <th>complexity</th>
                <th>duration</th>
            </thead>
            <tbody>
                <%
                    List<Test> list = (List) request.getAttribute("tests");
                    if (list == null) {
                        System.out.println("list is null");
                    } else {
                        for (Test t : list) {
                %>
                <tr>
                    <td>
                        <%= t.getName() %>
                    </td>
                </tr>
                <% } %>
            </tbody>
        </table>


    </body>
</html>