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
        .difficulty-icon{
            width: 8rem;
            height: 4rem;
            position: relative;
            margin-bottom: 0.5rem;
            z-index: -1;
        }
        .difficulty-icon.green{
            background: linear-gradient(30deg, #85CB9E 0%, #85CB9E 25%, #cecaca 25% , #cecaca 100%);
        }
        .difficulty-icon.green .bar{
            transform: rotate(-60deg);
        }
        .difficulty-icon.yellow{
            background: linear-gradient(60deg, #FECE0C 0%, #FECE0C 37.5%, #cecaca 37.5% , #cecaca 100%);
        }
        .difficulty-icon.yellow .bar{
            transform: rotate(-30deg);
        }
        .difficulty-icon.orange{
            background: linear-gradient(120deg, orange 0%, orange 62.5%, #cecaca 62.5% , #cecaca 100%);
        }
        .difficulty-icon.orange .bar{
            transform: rotate(30deg);
        }
        .difficulty-icon.red{
            background: #F05549;
        }
        .difficulty-icon.red .bar{
            transform: rotate(90deg);
        }


        .difficulty-icon::before {
            position: absolute;
            content: "";
            background: radial-gradient(white 28%, transparent 29%, transparent 52%, #fff 52%, #fff 56%, transparent 59% , #fff 62%);
            width: 8rem;
            height: 8rem;
        }

        .difficulty-icon .bar{
            width: 8rem;
            height: 8rem;
            position: relative;
        }

        .difficulty-icon .bar::after {
            width: 1rem;
            height: 1rem;
            content: "";
            background: #9C9C9C;
            top: calc(50% - 0.5rem);
            left: calc(50% - 0.5rem);
            position: absolute;
            z-index: 5;
            border-radius: 50%;
        }

        .difficulty-icon .bar::before {
            position: absolute;
            content: "";
            width: 0.9rem;
            height: 4rem;
            background: linear-gradient(to right, white 0%, white 35%, #9C9C9C 35%, #9C9C9C 65%, white 65%, white 100%);
            border-radius: 15%;
            top: 0;
            left: calc(50% - 0.45rem);
        }
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

        <p class="fs-3 text-center mt-3">///Your finished tests<p>

        <table class="table table-light border border-2 align-middle">
            <thead class="table-dark">
                <th class="text-center align-middle">///id</th>
                <th class="text-center align-middle">///test name</th>
                <th class="text-center align-middle">///test name</th>
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
                        <td width="10%" class="text-center align-middle"><fmt:formatNumber value='${element.timeSpent/60-0.49}' maxFractionDigits="0"/> ///min:${element.timeSpent%60} ///sec</td>
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



        <br>
        <br>
        <c:out value="${testsSorting}"></c:out>
        <br>
        <c:if test="${param.page eq 1}">
            <p>Previous</p>
        </c:if>
        <c:if test="${param.page gt 1}">
            <a href="/epam/passed/tests?page=${param.page-1}">Previous</a>
        </c:if>
        <c:if test="${param.page eq lastPage}">
            <p>Next</p>
        </c:if>
        <c:if test="${param.page lt lastPage}">
            <a href="/epam/passed/tests?page=${param.page+1}">Next</a>
        </c:if>

        <br>
        last page         <c:out value="${param.page}"></c:out>

        </fmt:bundle>
    </body>
</html>