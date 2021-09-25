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

    .tester {
        margin-bottom: 150px;
    }
    </style>
    <title><c:out value="${test.name}"></c:out></title>
</head>
    <body>

        <my:preventBack />

        <c:import url="/views/templates/navbar.jsp"></c:import>


            <div class="container">
                <div class="row text-center">
                    <p class="fs-1 text-center mt-3" style="text-overflow: ellipsis; overflow: hidden">Test name:  <c:out value="${test.name}"></c:out><p>
                </div>
                <div class="row text-center">
                    <div class="col">
                        <button type="button" class="btn btn-warning mb-1 mr-auto" disabled><fmt:message key="subjects.subject" />: <c:out value="${test.subject}"></c:out></button>
                    </div>
                    <div class="col">
                        <button type="button" class="btn btn-warning mb-1 mr-auto" disabled><fmt:message key="tests.complexity" />: <fmt:message key="complexity.${test.complexity}" /></button>
                    </div>
                    <div class="col">
                        <button type="button" class="btn btn-warning mb-1 mr-auto" disabled><fmt:message key="tests.questionsNum" />: <c:out value="${test.questionsNum}"> </c:out></button>
                    </div>
                    <div class="col">
                        <button type="button" class="btn btn-warning mb-1 mr-auto" disabled><fmt:message key="tests.duration" />: <my:floor val='${test.duration/60}' /><fmt:message key="time.min" /> : ${test.duration%60} <fmt:message key="time.sec" /></button>
                    </div>
                </div>
            </div>

            <!-- <div class="sticky-bottom text-center"> -->


            <hr>
            <p class="fs-3 text-center mt-3"><fmt:message key="msg.answerTheFollowingQuestions" /><p>

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
                <div class="tester">
                </div>

                <nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-bottom">
                    <div class="container-fluid text-center mx-auto row">
                        <div class="col"></div>

                        <div class="col"><div class="text-center ">
                            <input class="btn btn-success text-center" id="sub-btn" type="submit" value="<fmt:message key='submit' />">
                        </div>
                        <div class="text-center mt-1">
                            <div class="btn btn-info text-end disabled" style="font-weight: bold; font-size:16px;" id="time-left" disabled></div>
                        </div>
                        </div>
                        <div class="col"></div>
                    </div>
                </nav>
            </form>



            <script type="text/javascript">
                let totalSeconds = '<%= (Long) session.getAttribute("timeLeft" +request.getParameter("id"))%>';
                let min = parseInt(totalSeconds/60);
                let sec = parseInt(totalSeconds%60);

                function checkTime() {
                    document.getElementById("time-left").innerHTML = '<fmt:message key="left" />: ' + min + ' <fmt:message key="time.min" /> ' + sec + ' <fmt:message key="time.sec" />';
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