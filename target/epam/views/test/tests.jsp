<%@ page language ="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="my" uri="/tld/MyTagsDescriptor.tld"%>

<fmt:setLocale value="${cookie.lang.value}"/>
<fmt:bundle basename="messages">

<c:if test="${lastPage eq 0}">
    <c:set var="lastPage" scope="request" value="1"></c:set>
</c:if>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
    <style>
        .active-test {
            color: green;
            font-weight:600;
        }
        .inactive-test {
            color: red;
            font-weight:600;
        }
    </style>
    <title><fmt:message key="tests" /></title>
</head>
    <body>

        <my:preventBack />

        <c:import url="/views/templates/navbar.jsp"></c:import>

        <p class="fs-1 text-center mt-3"><fmt:message key="msg.currentlyAvailableTests" /><p>

        <div class="container-fluid">
            <form action="/epam/tests" method="GET">
                <div class="row">
                    <input type="hidden" name="page" value="1">
                    <div class="col"></div>
                    <div class="col-smd-3 col-lg-2">
                        <div class="form-floating">
                             <select size="1" class="form-select bg-light text-dark text-center" aria-label="sort" id="sort" name="sort" onchange="submit()">
                                <option class="align-middle" value="name ASC" ${param.sort == 'name ASC' ? 'selected' : ''}><fmt:message key="sort.nameAtoZ" /></option>
                                <option class="align-middle" value="name DESC" ${param.sort == 'name DESC' ? 'selected' : ''}><fmt:message key="sort.nameZtoA" /></option>
                                <option class="align-middle" value="scale ASC" ${param.sort == 'scale ASC' ? 'selected' : ''}><fmt:message key="sort.easyToHard" /></option>
                                <option class="align-middle" value="scale DESC" ${param.sort == 'scale DESC' ? 'selected' : ''}><fmt:message key="sort.hardToEasy" /></option>
                                <option class="align-middle" value="questionsNum ASC" ${param.sort == 'questionsNum ASC' ? 'selected' : ''}><fmt:message key="sort.numberOfQuestionsAsc" /></option>
                                <option class="align-middle" value="questionsNum DESC" ${param.sort == 'questionsNum DESC' ? 'selected' : ''}><fmt:message key="sort.numberOfQuestionsDesc" /></option>
                             </select>
                             <label class="text-center text-muted" for="sort"><fmt:message key="sorting" /></label>
                        </div>
                    </div>
                    <div class="col-smd-3 col-lg-2">
                        <div class="form-floating">
                            <select size="1" class="form-select bg-light text-dark text-center" aria-label="Default select example" id="subject" name="subject" onchange="submit()">
                                <option class="align-middle" value="0" ${param.subject == 0 ? 'selected' : ''}><fmt:message key="subjects.all" /></option>
                                <c:forEach items="${requestScope['subjects']}" var="element">
                                    <option class="align-middle" value="${element.id}" ${param.subject == element.id ? 'selected' : ''}>${element.name}</option>
                                </c:forEach>
                             </select>
                             <label class="text-center text-muted" for="subject"><fmt:message key="subjects.subject" /></label>
                        </div>
                    </div>
                    <div class="col-smd-3 col-lg-2">
                        <div class="form-floating">
                            <select size="1" class="form-select bg-light text-dark text-center" aria-label="Default select example" id="perPage" name="perPage" onchange="submit()">
                                <option class="align-middle" value="10" ${param.perPage == 10 ? 'selected' : ''}>10</option>
                                <option class="align-middle" value="25" ${param.perPage == 25 ? 'selected' : ''}>25</option>
                                <option class="align-middle" value="50" ${param.perPage == 50 ? 'selected' : ''}>50</option>
                            </select>
                            <label class="text-center text-muted" for="subject"><fmt:message key="recordsPerPage" /></label>
                        </div>
                    </div>
                    <c:if test="${sessionScope.currentUser.role == 'admin'}">
                        <div class="col-smd-3 col-lg-2">
                            <div class="form-floating">
                                <select size="1" class="form-select bg-light text-dark text-center" aria-label="Default select example" id="testStatus" name="testStatus" onchange="submit()">
                                    <option class="align-middle" value="all" ${param.testStatus == 'all' ? 'selected' : ''}><fmt:message key="status.all" /></option>
                                    <option class="align-middle" value="active" ${param.testStatus == 'active' ? 'selected' : ''}><fmt:message key="status.active" /></option>
                                    <option class="align-middle" value="inactive" ${param.testStatus == 'inactive' ? 'selected' : ''}><fmt:message key="status.inactive" /></option>
                                </select>
                                <label class="text-center text-muted" for="subject"><fmt:message key="status.testStatus" /></label>
                            </div>
                        </div>
                    </c:if>
                    <div class="col"></div>
                </div>
            </form>
        </div>

        <br>
        <table class="table table-light border" style="table-layout: fixed;">
            <thead class="table-info align-middle">
                <th style="width: 25%; white-space: nowrap; text-overflow: ellipsis; overflow: hidden;" width="25%"><fmt:message key="tests.name" /></th>
                <th class="text-center"><fmt:message key="subjects.subject" /></th>
                <th class="text-center"><fmt:message key="tests.complexity" /></th>
                <th class="text-center"><fmt:message key="tests.duration" /></th>
                <th class="text-center"><fmt:message key="tests.questionsNum" /></th>
                <c:if test="${sessionScope.currentUser.role == 'admin'}">
                    <th class="text-center"><fmt:message key="status" /></th>
                    <th class="text-center"><fmt:message key="details" /></th>
                </c:if>
                <th class="text-center"><fmt:message key="actions" /></th>
            </thead>
            <tbody>
                <c:forEach items="${requestScope['tests']}" var="element">
                    <tr>
                        <td style="width: 25%; text-overflow: ellipsis; overflow: hidden;" width="25%" class="align-middle">${element.name}</td>
                        <td class="text-center align-middle">${element.subject}</td>
                        <td class="text-center align-middle"><fmt:message key="complexity.${element.complexity}" /></td>
                        <td class="text-center align-middle"><my:floor val='${element.duration/60}' /> <fmt:message key="time.min" /> : ${element.duration%60} <fmt:message key="time.sec" /></td>
                        <td class="text-center align-middle">${element.questionsNum}</td>
                        <c:if test="${sessionScope.currentUser.role == 'admin'}">
                            <c:if test="${element.isActive == true}">
                                <td class="text-center align-middle active-test">
                                <fmt:message key="status.active" />
                            </c:if>
                            <c:if test="${element.isActive == false}">
                                <td class="text-center align-middle inactive-test">
                                <fmt:message key="status.inactive" />
                            </c:if>
                            </td>
                            <td class="text-center align-middle"><a class="btn btn-warning" href="/epam/test?id=${element.id}"><fmt:message key="edit" /></a></td>
                        </c:if>
                        <td class="text-center align-middle">
                            <div>
                                <a class="btn text-center align-middle btn-success ${element.questionsNum == 0 || element.isActive == false ? 'disabled' : ''}" href="/epam/take/test?id=${element.id}" data-bs-toggle="tooltip" data-bs-placement="top" title="<fmt:message key='msg.timerWillStart' />" disabled ><fmt:message key='start' /></a>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <br>

        <c:set var = "role" scope = "request" value = "admin"/>
        <nav aria-label="Page navigation">
            <ul class="pagination justify-content-center">
                <c:if test="${param.page eq 1}">
                    <li class="page-item disabled">
                        <a class="page-link bg-light text-dark ml-2" href="#" tabindex="-1" aria-disabled="true"><fmt:message key='first' /></a>
                    </li>
                </c:if>
                <c:if test="${param.page gt 1}">
                    <li class="page-item">
                        <a class="page-link bg-success text-light" href="/epam/tests?page=1&sort=${param.sort}&subject=${param.subject}&perPage=${param.perPage}<c:if test='${sessionScope.currentUser.role == role}'>&testStatus=${param.testStatus}</c:if>" tabindex="-1" aria-disabled="true"><fmt:message key='first' /></a>
                    </li>
                </c:if>
                <c:if test="${param.page eq 1}">
                    <li class="page-item disabled">
                        <a class="page-link bg-light text-dark ml-2" href="#" tabindex="-1" aria-disabled="true"><fmt:message key='previous' /></a>
                    </li>
                </c:if>
                <c:if test="${param.page gt 1}">
                    <li class="page-item">
                        <a class="page-link bg-dark text-light ml-2" href="/epam/tests?page=${param.page-1}&sort=${param.sort}&subject=${param.subject}&perPage=${param.perPage}<c:if test='${sessionScope.currentUser.role == role}'>&testStatus=${param.testStatus}</c:if>"><fmt:message key='previous' /></a>
                    </li>
                </c:if>
                <c:if test="${param.page eq lastPage}">
                    <li class="page-item disabled">
                        <a class="page-link bg-light text-dark ml-2" href="#" tabindex="-1" aria-disabled="true"><fmt:message key='next' /></a>
                    </li>
                </c:if>
                <c:if test="${param.page lt lastPage}">
                    <li class="page-item">
                        <a class="page-link bg-dark text-light ml-2" href="/epam/tests?page=${param.page+1}&sort=${param.sort}&subject=${param.subject}&perPage=${param.perPage}<c:if test='${sessionScope.currentUser.role == role}'>&testStatus=${param.testStatus}</c:if>"><fmt:message key='next' /></a>
                    </li>
                </c:if>
                <c:if test="${param.page eq lastPage}">
                    <li class="page-item disabled">
                        <a class="page-link bg-light text-dark ml-2" href="#" tabindex="-1" aria-disabled="true"><fmt:message key='last' /></a>
                    </li>
                </c:if>
                <c:if test="${param.page lt lastPage}">
                    <li class="page-item">
                        <a class="page-link bg-success text-light" href="/epam/tests?page=${lastPage}&sort=${param.sort}&subject=${param.subject}&perPage=${param.perPage}<c:if test='${sessionScope.currentUser.role == role}'>&testStatus=${param.testStatus}</c:if>" tabindex="-1" aria-disabled="true"><fmt:message key='last' /></a>
                    </li>
                </c:if>
            </ul>
        </nav>

        <script>
            let tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
            let tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
              return new bootstrap.Tooltip(tooltipTriggerEl)
            })

        </script>

        </fmt:bundle>
    </body>
</html>