<%@ page language ="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="my" uri="/tld/MyTagsDescriptor.tld"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
    <style>
    </style>
    <title>///Error</title>
</head>
    <body>

    <fmt:setLocale value="${cookie.lang.value}"/>
    <fmt:bundle basename="messages">
    <my:preventBack />

    <c:import url="/views/templates/navbar.jsp"></c:import>

    </br>

        <hr>
        <p>${requestScope['javax.servlet.error.exception'].getMessage()}</p>

        <%
            out.println(pageContext.getErrorData().getRequestURI());
            out.println("<br/>");
            out.println(pageContext.getErrorData().getStatusCode());
            out.println("<br/>");
            out.println("_" + pageContext.getException()+ "_");
            out.println("<br/>");
        %>

        </fmt:bundle>
    </body>
</html>