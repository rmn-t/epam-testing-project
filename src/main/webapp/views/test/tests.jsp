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
    <title>///Main</title>
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

        <p class="fs-1 text-center mt-3">///Currently available tests<p>

        <div class="container-fluid">
            <form action="/epam/tests" method="GET">
                <div class="row">
                    <input type="hidden" name="page" value="1">
                    <div class="col"></div>
                    <div class="col-md-4 col-lg-2">
                        <div class="form-floating">
                             <select size="1" class="form-select bg-light text-dark text-center" aria-label="sort" id="sort" name="sort" onchange="submit()">
                                <option class="align-middle" value="name ASC" ${param.sort == 'name ASC' ? 'selected' : ''}>///Name A-Z</option>
                                <option class="align-middle" value="name DESC" ${param.sort == 'name DESC' ? 'selected' : ''}>///Name Z-A</option>
                                <option class="align-middle" value="scale ASC" ${param.sort == 'scale ASC' ? 'selected' : ''}>///Easy to hard</option>
                                <option class="align-middle" value="scale DESC" ${param.sort == 'scale DESC' ? 'selected' : ''}>///Hard to easy</option>
                                <option class="align-middle" value="questionsNum ASC" ${param.sort == 'questionsNum ASC' ? 'selected' : ''}>///Questions number A-Z</option>
                                <option class="align-middle" value="questionsNum DESC" ${param.sort == 'questionsNum DESC' ? 'selected' : ''}>///Questions number Z-A</option>
                             </select>
                             <label class="text-center text-muted" for="sort">///Sorting:</label>
                        </div>
                    </div>
                    <div class="col-md-4 col-lg-2">
                        <div class="form-floating">
                            <select size="1" class="form-select bg-light text-dark text-center" aria-label="Default select example" id="subject" name="subject" onchange="submit()">
                                <option class="align-middle" value="0" ${param.subject == 0 ? 'selected' : ''}>///All subjects</option>
                                <c:forEach items="${requestScope['subjects']}" var="element">
                                    <option class="align-middle" value="${element.id}" ${param.subject == element.id ? 'selected' : ''}>///${element.name}</option>
                                </c:forEach>
                             </select>
                             <label class="text-center text-muted" for="subject">///Subject:</label>
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
        <br>
        <table class="table table-light border">
            <thead class="table-dark">
                <th>///Test name</th>
                <th class="text-center">///subject</th>
                <th class="text-center">///complexity</th>
                <th class="text-center">///duration</th>
                <th class="text-center">///questions</th>
                <c:if test="${sessionScope.userRole == 'admin'}">
                    <th class="text-center">///details</th>
                </c:if>
                <th class="text-center">///Pass</th>
            </thead>
            <tbody>
                <c:forEach items="${requestScope['tests']}" var="element">
                    <tr>
                        <td class="align-middle">${element.name}</td>
                        <td class="text-center align-middle">///${element.subject}</td>
                        <td class="text-center align-middle">///${element.complexity}</td>
                        <td class="text-center align-middle"><fmt:formatNumber value='${element.duration/60-0.49}' maxFractionDigits="0"/> ///min:${element.duration%60} ///sec</td>
                        <td class="text-center align-middle">${element.questionsNum}</td>
                        <c:if test="${sessionScope.userRole == 'admin'}">
                            <td class="text-center align-middle"><a class="btn btn-warning" href="/epam/test?id=${element.id}">///Details</a></td>
                        </c:if>
                        <td class="text-center">
                            <div>
                            <a class="btn btn-success" href="/epam/take/test?id=${element.id}" data-bs-toggle="tooltip" data-bs-placement="top" title="Tooltip on top">///Start</a>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <br>

        <nav aria-label="Page navigation">
            <ul class="pagination justify-content-center">
                <li class="page-item">
                    <a class="page-link bg-success text-light" href="/epam/tests?page=1&sort=${param.sort}&subject=${param.subject}&perPage=${param.perPage}" tabindex="-1" aria-disabled="true">///First</a>
                </li>
                <c:if test="${param.page eq 1}">
                    <li class="page-item disabled">
                        <a class="page-link bg-light text-dark ml-2" href="#" tabindex="-1" aria-disabled="true">///Previous</a>
                    </li>
                </c:if>
                <c:if test="${param.page gt 1}">
                    <li class="page-item">
                        <a class="page-link bg-dark text-light ml-2" href="/epam/tests?page=${param.page-1}&sort=${param.sort}&subject=${param.subject}&perPage=${param.perPage}">///Previous</a>
                    </li>
                </c:if>
                <c:if test="${param.page eq lastPage}">
                    <li class="page-item disabled">
                        <a class="page-link bg-light text-dark ml-2" href="#" tabindex="-1" aria-disabled="true">///Next</a>
                    </li>
                </c:if>
                <c:if test="${param.page lt lastPage}">
                    <li class="page-item">
                        <a class="page-link bg-dark text-light ml-2" href="/epam/tests?page=${param.page+1}&sort=${param.sort}&subject=${param.subject}&perPage=${param.perPage}">///Next</a>
                    </li>
                </c:if>
                <li class="page-item">
                    <a class="page-link bg-success text-light" href="/epam/tests?page=${lastPage}&sort=${param.sort}&subject=${param.subject}&perPage=${param.perPage}" tabindex="-1" aria-disabled="true">///Last</a>
                </li>
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