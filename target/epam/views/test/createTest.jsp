<%@ page language ="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="my" uri="/tld/MyTagDescriptor.tld"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
    <style>
    </style>
    <title>///Creating new test</title>
</head>
    <body>
        <fmt:setLocale value="${cookie.lang.value}"/>
        <fmt:bundle basename="messages">
        <%
            response.setHeader("Cache-Control","no-cache, no-store, must-revalidate"); // HTTP 1.1
            response.setHeader("Pragma","no-cache"); // HTTP 1.0
            response.setHeader("Expires","0"); // if using a proxy server
        %>

        <c:import url="/views/templates/navbar.jsp"></c:import>

        <div style="margin-top: 2%;"class="container">
            <div class="row align-items-center">
                <div class="col"></div>
                <div class="col-lg-4 col-md-6 col-sm-8">
                    <div class="card">
                        <article class="card-body">
                            <h4 class="card-title text-center mb-4 mt-1">///Fill in test info</h4>
                            <hr>
                            <c:if test="${not empty loginStatus}">
                                <div class="alert alert-danger alert-dismissible fade show text-center" role="alert">
                                    <strong><fmt:message key="msg.${loginStatus}" /></strong>
                                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                                </div>
                            </c:if>
                            <form action="/epam/createTest" method="POST">
                            <div class="mb-2 text-center">
                                <label for="testName" class="form-label">///Test name</label>
                                <input type="text" class="form-control" name="testName" id="testName" placeholder="///Test name" required>
                            </div>
                            <div class="mb-2 text-center">
                                <div class="form-floating">
                                    <select size="1" class="form-select bg-light text-dark text-center" aria-label="Default select example" id="subject" name="subject" required>
                                        <c:forEach items="${requestScope['subjects']}" var="element">
                                            <option class="align-middle" value="${element.id}">///${element.name}</option>
                                        </c:forEach>
                                     </select>
                                     <label class="text-center text-muted" for="subject">///Subject:</label>
                                </div>
                            </div>
                            <div class="mb-2 text-center">
                                <div class="form-floating">
                                    <select size="1" class="form-select bg-light text-dark text-center" aria-label="Default select example" id="complexity" name="complexity" required>
                                        <c:forEach items="${requestScope['complexities']}" var="element">
                                            <option class="align-middle" value="${element.id}">///${element.name}</option>
                                        </c:forEach>
                                    </select>
                                    <label class="text-center text-muted" for="complexity">///Subject:</label>
                                </div>
                            </div>
                            <div class="mb-2 text-center">
                                <label for="duration" class="form-label">///Test duration</label>
                                <input type="number" class="form-control" name="duration" id="duration" placeholder="///Test duration" required>
                            </div>

                            <div class="mt-3 container text-center">
                                <div class="row">
                                    <div class="col"></div>
                                    <div class="col-sm-5 mb-1"><button type="submit" class="btn btn-success btn-block">///Create test</button></div>
                                    <div class="col"></div>
                                </div>
                            </div>

                            </form>
                        </article>
                    </div>
                </div>
                <div class="col"></div>
            </div>
        </div>

        </fmt:bundle>
    </body>
</html>