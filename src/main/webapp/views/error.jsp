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
        .error-card {
            background-color: #ff7070;
        }
        </style>
        <title>///Error</title>
    </head>
    <body>
        <fmt:setLocale value="${cookie.lang.value}"/>
        <fmt:bundle basename="messages">
        <my:preventBack />

        <c:import url="/views/templates/navbar.jsp"></c:import>

        </br>

        <div style="margin-top:12%;" class="container">
            <div class="row">
                <div class="col-2"></div>
                    <div class="col">
                        <div class="card text-white error-card mb-3">
                            <div class="card-header">///An error occurred while processing your request. Please try again later or contact support support@test.com</div>
                            <div class="card-body">
                                <h5 class="card-title">///Status code: <c:out value="${requestScope['javax.servlet.error.status_code']}" /></h5>
                                <p class="card-text">${requestScope['javax.servlet.error.exception'].getMessage()}</p>
                            </div>
                        </div>
                    </div>
                <div class="col-2"></div>
            </div>
        </div>

        </fmt:bundle>
    </body>
</html>