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
    <title><c:out value="${test.name}"></c:out></title>
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
                        <form action="/epam/editTest?id=${test.id}" method="POST">
                            <div class="row align-items-bottom">
                                <div class="col">
                                    <div class="form-floating mb-2 text-center">
                                        <input type="text" class="form-control bg-light" name="name" id="name" placeholder="///Test name" value="${test.name}">
                                        <label for="name" class="form-label">///Test name</label>
                                    </div>
                                </div>
                                <div class="col">
                                    <div class="mb-2 text-center">
                                        <div class="form-floating">
                                            <select size="1" class="form-select bg-light text-dark text-center" aria-label="Default select example" id="subject" name="subject">
                                                <c:forEach items="${requestScope['subjects']}" var="element">
                                                    <option class="align-middle" value="${element.id}">///${element.name}</option>
                                                </c:forEach>
                                             </select>
                                             <label class="text-center text-muted" for="subject">///Subject:</label>
                                        </div>
                                    </div>
                                </div>
                                <div class="col">
                                    <div class="mb-2 text-center">
                                        <div class="form-floating">
                                            <select size="1" class="form-select bg-light text-dark text-center" aria-label="Default select example" id="complexity" name="complexity">
                                                <c:forEach items="${requestScope['complexities']}" var="element">
                                                    <option class="align-middle" value="${element.id}">///${element.name}</option>
                                                </c:forEach>
                                            </select>
                                            <label class="text-center text-muted" for="complexity">///Subject:</label>
                                        </div>
                                    </div>
                                </div>
                                <div class="col">
                                    <div class="form-floating mb-2 text-center">
                                        <input type="number" class="form-control bg-light text-center" name="durationMin" id="durationMin" placeholder="///Test duration min" min="0" step="1"  onkeypress='return event.charCode >= 48 && event.charCode <= 57' value='<fmt:formatNumber value="${test.duration/60 - 0.49}" maxFractionDigits="0"/>' />
                                        <label for="durationMin" class="form-label">///Test duration min</label>
                                    </div>
                                </div>
                                <div class="col">
                                    <div class="form-floating mb-2 text-center">
                                        <input type="number" class="form-control bg-light text-center" name="durationSec" id="durationSec" placeholder="///Test duration sec" min="0" step="1"  onkeypress='return event.charCode >= 48 && event.charCode <= 57' max="59" value="${test.duration%60}">
                                        <label for="durationSec" class="form-label">///Test duration sec</label>
                                    </div>
                                </div>
                                <div class="mt-3 container text-center">
                                    <div class="row">
                                        <div class="col"></div>
                                        <div class="col-sm-5 mb-1"><button type="submit" class="btn btn-success btn-block">///Save info</button></div>
                                        <div class="col"></div>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </article>
                </div>
            </div>
        </div>
        <div class="container text-center mt-2">
            <div class="row">
                <form action="/epam/delete/test?id=${test.id}" method="POST">
                    <input class="btn btn-danger justify-content-center text-center" type="submit" value="Delete test">
                </form>
            </div>
        </div>

            <p class="fs-2 text-center mt-3 align-middle align-items-center">
                Number of questions: <c:out value="${test.questionsNum}"></c:out>
                <a class="btn btn-warning justify-content-center text-center" role="button" href="/epam/add/question?testId=${test.id}">///Add question</a>
            </p>
            <hr>
            <a href="/epam/questionForm.jsp">Add question</a>
            <br>

            <c:forEach items="${requestScope['questions']}" var="question">
            <div class="container text-left justify-content-center mt-2">
                <div class="row">
                    <div class="col"></div>
                    <div class="col-sm-10">
                        <div class="card text-dark bg-light mb-3 text-left">
                            <div class="card-header">
                                Question: ${question.text}
                                <form style="display: inline;" class="float-end" action="/epam/delete/question?id=${question.id}&testId=${param.id}" method="POST">
                                    <input class="btn btn-danger" type="submit" value="Delete">
                                </form>
                                <a class="btn btn-warning justify-content-center text-center float-end" role="button" href="/epam/edit/question?id=${question.id}">Edit</a>
                            </div>
                            <div class="card-body">
                                <c:forEach items="${question.answers}" var="answer">
                                    <div class="d-flex align-items-center">
                                        <c:if test="${answer.isCorrect eq false}">
                                            <button style="width: 50px;" type="button" class="btn btn-sm btn-danger mb-1 mr-auto" disabled>&#10060;</button>
                                        </c:if>
                                        <c:if test="${answer.isCorrect eq true}">
                                            <button style="width: 50px;" type="button" class="btn btn-sm btn-success mb-1 mr-auto" disabled><strong>&check;</strong></button>
                                        </c:if>
                                        <p class="ml-2 card-text mb-1 float-">&nbsp;${answer.text}</p>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                    <div class="col"></div>
                </div>
            </div>
            </c:forEach>

        </fmt:bundle>
    </body>
</html>