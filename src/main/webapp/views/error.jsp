<%@ page language ="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="my" uri="/tld/MyTagsDescriptor.tld"%>

<fmt:setLocale value="${cookie.lang.value}"/>
<fmt:bundle basename="messages">

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
        <style>
            .error-card {
                background-color: #ff7070;
            }
            .container {
                margin-top:10%;
            }

        </style>
        <title><fmt:message key="error.error" /></title>
    </head>
    <body>

        <my:preventBack />

        <c:if test="${not empty sessionScope.currentUser}">
            <c:import url="/views/templates/navbar.jsp"></c:import>
        </c:if>

        </br>

        <div class="container">
            <div class="row">
                <div class="col-2"></div>
                    <div class="col">
                        <div class="card text-white bg-primary mb-3">
                            <div class="card-header">An error occurred while processing your request. Please try again later or contact support@test.com</div>
                            <div class="card-body">
                                <h5 class="card-title">Status code: <c:out value="${requestScope['javax.servlet.error.status_code']}" /></h5>
                                <p class="card-text">
                                    <c:if test="${requestScope['javax.servlet.error.exception'].getClass().getCanonicalName() == 'javax.servlet.ServletException' }">
                                        ${requestScope['javax.servlet.error.exception'].getMessage()}
                                    </c:if>
                                    <c:if test="${requestScope['javax.servlet.error.exception'].getClass().getCanonicalName() != 'javax.servlet.ServletException' }">
                                        An unexpected error happened.
                                    </c:if>
                                </p>
                            </div>
                        </div>
                    </div>
                <div class="col-2"></div>
            </div>
        </div>

        </fmt:bundle>
    </body>
</html>