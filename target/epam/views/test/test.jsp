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
    </style>
    <title>
        <c:if test="${param.id != 0}">
            <c:out value="${test.name}"></c:out>
        </c:if>
        <c:if test="${param.id == 0}">
            <fmt:message key="tests.createNewTest" />
        </c:if>
    </title>
</head>
    <body>

        <my:preventBack />

        <c:import url="/views/templates/navbar.jsp"></c:import>

        <div style="margin-top: 2%;"class="container">
            <div class="row align-items-center">
                <div class="card">
                    <article class="card-body">
                        <h4 class="card-title text-center mb-4 mt-1">
                            <c:if test="${param.id != 0}">
                                <fmt:message key="tests.editTestInfo" />
                                <i><h6 class="text-center">*<fmt:message key="msg.atLeastOneQuestion" /></h6></i>
                            </c:if>
                            <c:if test="${param.id == 0}">
                                <fmt:message key="tests.createNewTest" />
                            </c:if>
                        </h4>
                        <hr>

                        <c:set var="edit_test" value="editTest?id=${test.id}" scope="request"></c:set>
                        <!-- <form action="/epam/${param.id == '0' ? 'createTest' : edit_test}" method="POST"> -->
                        <form action="/epam/editTest?id=${test.id}" method="POST">
                            <div class="row align-items-bottom">
                                <div class="col">
                                    <div class="form-floating mb-2 text-center">
                                        <input type="text" class="form-control bg-light" name="name" id="name" placeholder="<fmt:message key='tests.name' />" value="${test.name}" required maxlength="150"
                                        oninvalid="this.setCustomValidity('<fmt:message key='msg.fillOutThisField' />')"
                                        oninput="this.setCustomValidity('')"
                                        >
                                        <label for="name" class="form-label"><fmt:message key="tests.name" /></label>
                                    </div>
                                </div>
                                <div class="col">
                                    <div class="mb-2 text-center">
                                        <div class="form-floating">
                                            <select size="1" class="form-select bg-light text-dark text-center" aria-label="Default select example" id="subject" name="subject">
                                                <c:forEach items="${requestScope['subjects']}" var="element">
                                                    <option class="align-middle" value="${element.id}" ${test.subjectId == element.id ? 'selected' : ''}>${element.name}</option>
                                                </c:forEach>
                                             </select>
                                             <label class="text-center text-muted" for="subject"><fmt:message key="subjects.subject" /></label>
                                        </div>
                                    </div>
                                </div>
                                <div class="col">
                                    <div class="mb-2 text-center">
                                        <div class="form-floating">
                                            <select size="1" class="form-select bg-light text-dark text-center" aria-label="Default select example" id="complexity" name="complexity">
                                                <c:forEach items="${requestScope['complexities']}" var="element">
                                                    <option class="align-middle" value="${element.id}" ${test.complexityId == element.id ? 'selected' : ''}><fmt:message key='complexity.${element.name}' /></option>
                                                </c:forEach>
                                            </select>
                                            <label class="text-center text-muted" for="complexity"><fmt:message key="tests.complexity" /></label>
                                        </div>
                                    </div>
                                </div>
                                <div class="col">
                                    <div class="form-floating mb-2 text-center">
                                        <input type="number" class="form-control bg-light text-center" name="durationMin" id="durationMin" placeholder="<fmt:message key='tests.durationMin' />" min="0" step="1"  onkeypress='return event.charCode >= 48 && event.charCode <= 57' value='<my:floor val="${test.duration/60}" />' maxFractionDigits="0"/ required
                                        oninvalid="this.setCustomValidity('<fmt:message key='msg.fillOutThisField' />')"
                                        oninput="this.setCustomValidity('')"
                                        >
                                        <label for="durationMin" class="form-label"><fmt:message key="tests.durationMin" /></label>
                                    </div>
                                </div>
                                <div class="col">
                                    <div class="form-floating mb-2 text-center">
                                        <input type="number" class="form-control bg-light text-center" name="durationSec" id="durationSec" placeholder="<fmt:message key='tests.durationSec' />" min="0" step="1"  onkeypress='return event.charCode >= 48 && event.charCode <= 57' max="59" value="${test.duration%60}" required
                                        oninvalid="this.setCustomValidity('<fmt:message key='msg.zeroToFiftyNineSeconds' />')"
                                        oninput="this.setCustomValidity('')"
                                        >
                                        <label for="durationSec" class="form-label"><fmt:message key="tests.durationSec" /></label>
                                    </div>
                                </div>
                                <c:if test="${param.id != 0}">
                                    <div class="col">
                                        <div class="mb-2 text-center">
                                            <div class="form-floating">
                                                <select size="1" class="form-select bg-light text-dark text-center" aria-label="Default select example" id="isActive" name="isActive">
                                                    <c:if test="${test.questionsNum != 0}">
                                                        <option class="align-middle" value="true" ${test.isActive == true ? 'selected' : ''}><fmt:message key="status.active" /></option>
                                                    </c:if>
                                                    <option class="align-middle" value="false" ${test.isActive == false ? 'selected' : ''}><fmt:message key="status.inactive" /></option>
                                                </select>
                                                <label class="text-center text-muted" for="isActive"><fmt:message key="status" /></label>
                                            </div>
                                        </div>
                                    </div>
                                </c:if>
                                <div class="mt-3 container text-center">
                                    <div class="row">
                                        <div class="col"></div>
                                        <div class="col-sm-5 mb-1"><button type="submit" class="btn btn-success btn-block"><fmt:message key="saveChanges" /></button></div>
                                        <div class="col"></div>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </article>
                </div>
            </div>
        </div>
        <c:if test="${param.id != 0}">
            <div class="container text-center mt-2">
                <div class="row">
                    <form action="/epam/delete/test?id=${test.id}" method="POST">
                        <input class="btn btn-danger justify-content-center text-center" type="submit" value="<fmt:message key='tests.delete' />">
                    </form>
                </div>
            </div>

            <p class="fs-2 text-center mt-3 align-middle align-items-center">
                <fmt:message key="tests.questionsNum" />: <c:out value="${test.questionsNum}"></c:out>
                <a class="btn btn-warning justify-content-center text-center" role="button" href="/epam/add/question?testId=${test.id}"><fmt:message key="questions.add" /></a>
            </p>
            <hr>
        </c:if>

            <c:forEach items="${requestScope['questions']}" var="question">
            <div class="container text-left mt-2">
                <div class="row">
                    <div class="col"></div>
                    <div class="col-sm-10">
                        <div class="card text-dark bg-light mb-3 text-left">
                            <div class="card-header">
                                ${question.text}
                                <form style="display: inline;" class="float-end align-self-end" action="/epam/delete/question?id=${question.id}&testId=${param.id}" method="POST">
                                    <input type="hidden" name="questionsLeft" value="${test.questionsNum}">
                                    <input class="btn btn-danger" type="submit" value="<fmt:message key='delete' />">
                                </form>
                                <a class="btn btn-warning justify-content-center text-center float-end align-self-end" role="button" href="/epam/edit/question?id=${question.id}&testId=${param.id}"><fmt:message key="edit" /></a>
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