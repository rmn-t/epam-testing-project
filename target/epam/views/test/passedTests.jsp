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
    <title>///Passed tests</title>
</head>
    <body>
        <fmt:setLocale value="${cookie.lang.value}"/>
        <fmt:bundle basename="messages">
        <my:preventBack />

        <c:import url="/views/templates/navbar.jsp"></c:import>

        <p class="fs-3 text-center mt-3">///Your finished tests<p>


        <div class="container-fluid">
            <form action="/epam/passed/tests" method="GET">
                <div class="row">
                    <input type="hidden" name="page" value="1">
                    <div class="col"></div>
                    <div class="col-md-4 col-lg-2">
                        <div class="form-floating">
                             <select size="1" class="form-select bg-light text-dark text-center" aria-label="sort" id="sort" name="sort" onchange="submit()">
                                <option class="align-middle" value="testName ASC" ${param.sort == 'testName ASC' ? 'selected' : ''}>///Name A-Z</option>
                                <option class="align-middle" value="testName DESC" ${param.sort == 'testName DESC' ? 'selected' : ''}>///Name Z-A</option>
                                <option class="align-middle" value="question_num ASC" ${param.sort == 'question_num ASC' ? 'selected' : ''}>///Questions # A-Z</option>
                                <option class="align-middle" value="question_num DESC" ${param.sort == 'question_num DESC' ? 'selected' : ''}>///Questions # Z-A</option>
                                <option class="align-middle" value="grade ASC" ${param.sort == 'grade ASC' ? 'selected' : ''}>///Grades low to high</option>
                                <option class="align-middle" value="grade DESC" ${param.sort == 'grade DESC' ? 'selected' : ''}>///Grades high to low</option>
                                <option class="align-middle" value="date ASC" ${param.sort == 'date ASC' ? 'selected' : ''}>///Old first</option>
                                <option class="align-middle" value="date DESC" ${param.sort == 'date DESC' ? 'selected' : ''}>///New first</option>
                             </select>
                             <label class="text-center text-muted" for="sort">///Sorting:</label>
                        </div>
                    </div>
                    <div class="col-md-4 col-lg-2">
                        <div class="form-floating">
                            <select size="1" class="form-select bg-light text-dark text-center" aria-label="Default select example" id="perPage" name="perPage" onchange="submit()">
                                <option class="align-middle" value="10" ${param.perPage == 10 ? 'selected' : ''}>10</option>
                                <option class="align-middle" value="25" ${param.perPage == 25 ? 'selected' : ''}>25</option>
                                <option class="align-middle" value="50" ${param.perPage == 50 ? 'selected' : ''}>50</option>
                            </select>
                            <label class="text-center text-muted" for="subject">///Records per page:</label>
                        </div>
                    </div>
                    <div class="col"></div>
                </div>
            </form>
        </div>
        <hr>

        <table class="table table-light border border-3 align-middle">
            <thead class="table-info">
                <th class="text-center align-middle">///id</th>
                <th class="text-center align-middle">///test name</th>
                <th class="text-center align-middle">///time spent</th>
                <th class="text-center align-middle">///questions #</th>
                <th class="text-center align-middle">///correct #</th>
                <th class="text-center align-middle">///grade</th>
                <th class="text-center align-middle">///date</th>
            </thead>
            <tbody>
                <c:forEach items="${requestScope['passedTests']}" var="element">
                    <tr class="bg-light">
                        <td width="5%" class="text-center align-middle">${element.id}</td>
                        <td width="30%" class="text-left align-middle">${element.testName}</td>
                        <td width="10%" class="text-center align-middle">
                            <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${element.timeSpent}" />%
                        </td>
                        <td width="10%" class="text-center align-middle">${element.questionNum}</td>
                        <td width="10%" class="text-center align-middle">${element.correctAnswers}</td>
                        <td width="25%" class="text-center align-middle">
                        <strong><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${element.grade}" />%</strong>
                            <div class="progress">
                                <c:if test="${element.grade le 100 && element.grade ge 90}">
                                    <div class="progress-bar bg-info" role="progressbar" style="width: ${element.grade}%;" aria-valuenow="${element.grade}" aria-valuemin="0" aria-valuemax="100">
                                </c:if>
                                <c:if test="${element.grade lt 90 && element.grade ge 60}">
                                    <div class="progress-bar bg-success" role="progressbar" style="width: ${element.grade}%;" aria-valuenow="${element.grade}" aria-valuemin="0" aria-valuemax="100">
                                </c:if>
                                <c:if test="${element.grade lt 60 && element.grade ge 30}">
                                    <div class="progress-bar bg-warning" role="progressbar" style="width: ${element.grade}%;" aria-valuenow="${element.grade}" aria-valuemin="0" aria-valuemax="100">
                                </c:if>
                                <c:if test="${element.grade lt 30 && element.grade ge 0}">
                                    <div class="progress-bar bg-danger" role="progressbar" style="width: ${element.grade}%;" aria-valuenow="${element.grade}" aria-valuemin="0" aria-valuemax="100">
                                </c:if>
                                <!-- <strong><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${element.grade}" />%</strong> -->
                                </div>
                            </div>
                        </td>
                        <td width="10%" class="text-center align-middle">${element.date}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <nav aria-label="Page navigation">
            <ul class="pagination justify-content-center">
                <li class="page-item">
                    <a class="page-link bg-success text-light" href="/epam/passed/tests?page=1&sort=${param.sort}&perPage=${param.perPage}" tabindex="-1" aria-disabled="true">///First</a>
                </li>
                <c:if test="${param.page eq 1}">
                    <li class="page-item disabled">
                        <a class="page-link bg-light text-dark ml-2" href="#" tabindex="-1" aria-disabled="true">///Previous</a>
                    </li>
                </c:if>
                <c:if test="${param.page gt 1}">
                    <li class="page-item">
                        <a class="page-link bg-dark text-light ml-2" href="/epam/passed/tests?page=${param.page-1}&sort=${param.sort}&perPage=${param.perPage}">///Previous</a>
                    </li>
                </c:if>
                <c:if test="${param.page eq lastPage}">
                    <li class="page-item disabled">
                        <a class="page-link bg-light text-dark ml-2" href="#" tabindex="-1" aria-disabled="true">///Next</a>
                    </li>
                </c:if>
                <c:if test="${param.page lt lastPage}">
                    <li class="page-item">
                        <a class="page-link bg-dark text-light ml-2" href="/epam/passed/tests?page=${param.page+1}&sort=${param.sort}&perPage=${param.perPage}">///Next</a>
                    </li>
                </c:if>
                <li class="page-item">
                    <a class="page-link bg-success text-light" href="/epam/passed/tests?page=${lastPage}&sort=${param.sort}&perPage=${param.perPage}" tabindex="-1" aria-disabled="true">///Last</a>
                </li>
            </ul>
        </nav>

        </fmt:bundle>
    </body>
</html>