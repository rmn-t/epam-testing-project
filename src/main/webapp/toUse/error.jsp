<%@ page language ="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="my" uri="/tld/MyTagsDescriptor.tld"%>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <style>
    p {
      font-size: 2em;
    }
    </style>
</head>
    <h1> This is the error page </h1>

        <!-- <p style:"font-size: 45px;"><pre>${pageContext.out.flush()}${requestScope['javax.servlet.error.exception'].getMessage()}</pre></p> -->
        <i><strong><c:out value="${requestScope['javax.servlet.error.exception']}"></c:out></strong></i>
        <hr>
        <p>${requestScope['javax.servlet.error.exception'].getMessage()}</p>

        <c:set var = "errMsg" value = "${requestScope['javax.servlet.error.message']}"/>
        <c:set var = "string2" value = "${fn:replace(errMsg, 'javax.servlet.ServletException:', '')}" />
        abc${errMsg}abc



        <p>_<c:out value="${requestScope['javax.servlet.error.message']}"/>_<p>



        <%
            out.println(pageContext.getErrorData().getRequestURI());
            out.println("<br/>");
            out.println(pageContext.getErrorData().getStatusCode());
            out.println("<br/>");
            out.println("_" + pageContext.getException()+ "_");
            out.println("<br/>");
        %>


    <!-- Stack trace -->


<body>

</body>
</html>