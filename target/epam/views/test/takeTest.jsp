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

            <p class="fs-1 text-center mt-3">Test name:  <c:out value="${test.name}"></c:out><p>
            <div class="container">
                <div class="row text-center">
                    <div class="col">
                        <button type="button" class="btn btn-warning mb-1 mr-auto" disabled>///Subject: <c:out value="${test.subject}"></c:out></button>
                    </div>
                    <div class="col">
                        <button type="button" class="btn btn-warning mb-1 mr-auto" disabled>///Complexity: <c:out value="${test.complexity}"></c:out></button>
                    </div>
                    <div class="col">
                        <button type="button" class="btn btn-warning mb-1 mr-auto" disabled>///Number of questions: <c:out value="${test.questionsNum}"> </c:out></button>
                    </div>
                    <div class="col">
                        <button type="button" class="btn btn-warning mb-1 mr-auto" disabled>Duration: <fmt:formatNumber value='${test.duration/60-0.49}' maxFractionDigits="0"/> ///min:${test.duration%60} ///sec</button>
                    </div>
                </div>
            </div>

            <div class="sticky-top text-end">
                <div class="btn btn-info text-end" style="font-weight: bold; font-size:20px; margin-top:4%" id="time-left" disabled></div>
            </div>

            <hr>
            <p class="fs-3 text-center mt-3">///Answer the following questions:<p>

            <form class="mb-3" action="/epam/take/test?id=${test.id}" method="POST" name="testForm">
            <hr>

            <c:forEach items="${requestScope['questions']}" var="question">
                <div class="container">
                    <div class="row align-items-center">
                        <div class="col-sm-1"></div>
                        <div class="col">
                            <div class="card bg-light text-dark mb-3">
                                <article class="card-body">
                                    <h5 class="card-title "><label class="card-title" for="${question.id}" name="${question.id}">${question.text}</label></h5>
                                    <div id="${question.id}">
                                        <c:forEach items="${question.answers}" var="answer">
                                            <div class="form-check">
                                                <input class="form-check-input" type="checkbox" id="${answer.id}" name="${question.id}" value="${answer.id}">
                                                <label class="form-check-label" for="${answer.id}">
                                                    ${answer.text}
                                                </label>
                                            </div>
                                        </c:forEach>
                                    </div>
                                </article>
                            </div>
                        </div>
                        <div class="col-sm-1"></div>
                    </div>
                </div>
                </c:forEach>
                <div class="text-center justify-content-center">
                    <input class="btn btn-success" id="sub-btn" type="submit" value="Submit now">
                </div>
            </form>

            <script type="text/javascript">
                let totalSeconds = '<%= (Long) session.getAttribute("timeLeft" +request.getParameter("id"))%>';
                let min = parseInt(totalSeconds/60);
                let sec = parseInt(totalSeconds%60);

                function checkTime() {
                    document.getElementById("time-left").innerHTML = '///Left: ' + min + ' min ' + sec + ' sec';
                    if (totalSeconds <= 0 ) {
                        setTimeout('document.testForm.submit()',1000);
                    } else {
                        totalSeconds = totalSeconds - 1;
                        min = parseInt(totalSeconds/60);
                        sec = parseInt(totalSeconds%60);
                        setTimeout("checkTime()",1000);
                    }
                }
                setTimeout("checkTime()",1);
            </script>

        </fmt:bundle>
    </body>
</html>