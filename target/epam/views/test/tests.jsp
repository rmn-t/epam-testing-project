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
    <title>Tests</title>
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

        <fmt:message key="login.sign-in" />

        <br>___<my:MyTag />___<br>


        <c:set var="requestPath" value="${requestScope['javax.servlet.forward.request_uri']}"/>
        <c:set var="queryString" value="${requestScope['javax.servlet.forward.query_string']}"/>
        <strong><c:out value="${requestPath}${empty queryString ? '' : '?'}${queryString}">abc</c:out></strong><br>

        <p class="fs-1 text-center">///Currently available tests<p>
        <br>
        <form action="/epam/tests" method="GET">
            <input type="hidden" name="page" value="1">
            <label for="sort">Sorting:</label>
            <select name="sort" id="sort">
                <option value="name ASC">Name ascending</option>
                <option value="name DESC">Name descending</option>
                <option value="scale ASC">Complexity ascending</option>
                <option value="scale DESC">Complexity descending</option>
                <option value="questionsNum ASC">Number of questions ascending</option>
                <option value="questionsNum DESC">Number of questions descending</option>
            </select>
            <input type="submit" value="Apply">
        </form>
        <br>

        <c:if test="${sessionScope.userRole == 'admin'}">

        <c:out value="${testsSorting}"></c:out>

        </c:if>

        <table class="table table-light border">
            <thead class="table-dark">
                <th>///name</th>
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
                        <td class="text-center align-middle">${element.subject}</td>
                        <td class="text-center align-middle">${element.complexity}</td>
                        <td class="text-center align-middle">${element.duration}</td>
                        <td class="text-center align-middle">${element.questionsNum}</td>
                        <c:if test="${sessionScope.userRole == 'admin'}">
                            <td class="text-center align-middle"><a href="/epam/test?id=${element.id}">Details</a></td>
                        </c:if>
                        <td class="text-center">
                            <div>
                            <a class="btn btn-dark" href="/epam/take/test?id=${element.id}">Start</a>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <br>

        <nav aria-label="Page navigation">
            <ul class="pagination justify-content-center">
                <c:if test="${param.page eq 1}">
                    <li class="page-item disabled">
                        <a class="page-link bg-dark text-light" href="#" tabindex="-1" aria-disabled="true">Previous</a>
                    </li>
                </c:if>
                <c:if test="${param.page gt 1}">
                    <li class="page-item">
                        <a class="page-link bg-dark text-light" href="/epam/tests?page=${param.page-1}">Previous</a>
                    </li>
                </c:if>
                <c:if test="${param.page eq lastPage}">
                    <li class="page-item disabled">
                        <a class="page-link bg-dark text-light" href="#" tabindex="-1" aria-disabled="true">Next</a>
                    </li>
                </c:if>
                <c:if test="${param.page lt lastPage}">
                    <li class="page-item">
                        <a class="page-link bg-dark text-light" href="/epam/tests?page=${param.page+1}">Next</a>
                    </li>
                </c:if>
            </ul>
        </nav>

        </fmt:bundle>
    </body>
</html>