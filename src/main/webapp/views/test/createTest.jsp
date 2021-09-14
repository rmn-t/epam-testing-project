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
    <title>///Creating new test</title>
</head>
    <body>
        <fmt:setLocale value="${cookie.lang.value}"/>
        <fmt:bundle basename="messages">
        <my:preventBack />

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

                                <div class="mb-3 text-center">
                                    <div class="form-floating text-center">
                                        <input type="text" class="form-control" name="testName" id="testName" placeholder="///Test name" required maxlength="150">
                                        <label for="testName" class="form-label text-center ">///Test name</label>
                                    </div>
                                </div>
                                <div class="mb-3 text-center">
                                    <div class="form-floating">
                                        <select size="1" class="form-select bg-light text-dark text-center" id="subject" name="subject" required>
                                            <c:forEach items="${requestScope['subjects']}" var="element">
                                                <option class="align-middle" value="${element.id}">///${element.name}</option>
                                            </c:forEach>
                                         </select>
                                         <label class="text-center text-muted" for="subject">///Subject:</label>
                                    </div>
                                </div>
                                <div class="mb-3 text-center">
                                    <div class="form-floating">
                                        <select size="1" class="form-select bg-light text-dark text-center" id="complexity" name="complexity" required>
                                            <c:forEach items="${requestScope['complexities']}" var="element">
                                                <option class="align-middle" value="${element.id}">///${element.name}</option>
                                            </c:forEach>
                                        </select>
                                        <label class="text-center text-muted" for="complexity">///Subject:</label>
                                    </div>
                                </div>

                                <div class="mb-3 text-center">
                                    <div class="form-floating">
                                        <input type="number" class="form-control" name="durationMin" id="durationMin" min="0" step="1"  onkeypress='return event.charCode >= 48 && event.charCode <= 57' placeholder="///Test duration min" required>
                                        <label for="durationMin" class="form-label">///Test duration min</label>
                                    </div>
                                </div>

                                <div class="mb-3 text-center">
                                    <div class="form-floating">
                                        <input type="number" class="form-control" name="durationSec" id="durationSec" min="0" step="1"  onkeypress='return event.charCode >= 48 && event.charCode <= 57' max="59" placeholder="///Test duration sec" required>
                                        <label for="durationSec" class="form-label">///Test duration sec</label>
                                    </div>
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