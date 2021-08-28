<%@ page
    language ="java"
    contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    import="java.util.Arrays,java.util.List"
%>

<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Insert title here</title>
</head>
    <body>

        <core:forEach items="${students}" var="s">
            ${s}<br>
        </core:forEach>

        <sql:setDataSource var="db" driver="com.mysql.cj.jdbc.Driver" url="jdbc:mysql://localhost:3306/p8test" user="root" password="testqq"/>

        <sql:query var="rs" dataSource="${db}">select * from users</sql:query>

        <core:forEach items="${rs.rows}" var="row">
            <core:out value="${row.id}"></core:out> : <core:out value="${row.login}"></core:out><br>
        </core:forEach>

        <core:set var="str" value="Pepepaga acdamck afvcak java" />
            Length : ${fn:length(str)}

        <core:forEach items="${fn:split(str,' ')}" var="s">
            <br>${s}
        </core:forEach>

        index : ${fn:indexOf(str,"ga")} <br>

        <core:if test="${fn:contains(str,'java')}">
            java found
        </core:if>


        <c:out value="<a href='tests?page=${lastPage}'>Previous</a>"></c:out>
        <a href="/csv/downloadIntentCSV?id=${lastPage}">Previous</a>
        <hr>
                <c:out value="${param.page}"></c:out>
        <hr>
    </body>
</html>




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
    <% }} %>

    reading request attributes
    https://www.javatips.net/blog/reading-request-attributes-using-jstl