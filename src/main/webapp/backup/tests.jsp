<%@ page language ="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
    <title>Register</title>
</head>
    <body>
        <%
            response.setHeader("Cache-Control","no-cache, no-store, must-revalidate"); // HTTP 1.1
            response.setHeader("Pragma","no-cache"); // HTTP 1.0
            response.setHeader("Expires","0"); // if using a proxy server
        %>
        <h1> Home page ...</h1>
        <h2>WELCOME ${username}</h2>
        <h2>Role ${sessionScope.userRole}</h2>
        <h3>Need to implement:</h3>
        <i><c:out value="${cookie.lang}"></c:out></i>
        <a href="/epam/createTest">Create new test</a>
        <br>
        <br>
        <a href="/epam/passed/tests?page=1">My tests</a>
        <br>
        <br>
        <a href="/epam/users?page=1">Users</a>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="tests?page=1">Home</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link" aria-current="page" href="tests?page=1">My tests</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="Account">Account</a>
                </li>
                <c:if test="${sessionScope.userRole == 'admin'}">
                    <li class="nav-item">
                        <a class="nav-link" aria-current="page" href="/epam/createTest">Add test</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" aria-current="page" href="/epam/users?page=1">Users</a>
                    </li>
                </c:if>
            </ul>
            <ul class="navbar-nav ml-auto mb-2 mb-lg-0">
                <li class="nav-item border">
                    <a class="nav-link active mr-2" aria-current="page">Logged in as: <c:out value="${sessionScope.username}" /> </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active mr-2" aria-current="page">  </a>
                </li>
                <li class="nav-item ml-2">
                    <form action="logout">
                        <input type="submit" class="btn btn-light" value="Logout">
                    </form>
                </li>
            </ul>
        </div>
    </div>
</nav>
<br>
<c:import url="/views/templates/navbar.jsp"></c:import>
<jsp:include page="/views/templates/navbar.jsp" />

        <br>
            <c:set var="req" value="${pageContext.request}" />
            <c:set var="baseURL" value="${fn:replace(req.requestURL, req.requestURI, '')}" />
            <c:set var="params" value="${requestScope['javax.servlet.forward.query_string']}"/>
            <c:set var="requestPath" value="${requestScope['javax.servlet.forward.request_uri']}"/>
            <c:set var="pageUrl" value="${ baseURL }${ requestPath }${params}"/>
            <strong><c:out value="${pageUrl}">abc</c:out></strong>
        <br>
        <form action="logout">
            <input type="submit" value="Logout">
        </form>



        </br>
        </br>
        Here is the list of available tests
        <br>
             <br>
        <form action="/epam/tests?page=1" method="POST">
            <label for="testsSorting">Sorting:</label>
            <select name="testsSorting" id="testsSorting">
                <option value="name ASC">Name ascending</option>
                <option value="name DESC">Name descending</option>
                <option value="complexity ASC">Complexity ascending</option>
                <option value="complexity DESC">Complexity descending</option>
                <option value="questionsNum ASC">Number of questions ascending</option>
                <option value="questionsNum DESC">Number of questions descending</option>
            </select>
            <input type="submit" value="Apply">
        </form>
        <br>

        <table border="1">
            <thead>
                <th>id</th>
                <th>name</th>
                <th>subject</th>
                <th>complexity</th>
                <th>duration</th>
                <th>number of tests</th>
                <th>details</th>
                <th>Pass</th>
            </thead>
            <tbody>
                <c:forEach items="${requestScope['tests']}" var="element">
                    <tr>
                        <td>${element.id}</td>
                        <td>${element.name}</td>
                        <td>${element.subject}</td>
                        <td>${element.complexity}</td>
                        <td>${element.duration}</td>
                        <td>${element.questionsNum}</td>
                        <td><a href="/epam/test?id=${element.id}">Details</a></td>
                        <td><a href="/epam/take/test?id=${element.id}">Start</a></td>
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
            <a href="/epam/tests?page=${param.page-1}">Previous</a>
        </c:if>
        <c:if test="${param.page eq lastPage}">
            <p>Next</p>
        </c:if>
        <c:if test="${param.page lt lastPage}">
            <a href="/epam/tests?page=${param.page+1}">Next</a>
        </c:if>

        <br>
        last page         <c:out value="${param.page}"></c:out>

    </body>
</html>