<%@ page language ="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<fmt:setLocale value="${cookie.lang.value}"/>
<fmt:bundle basename="messages">
<c:set var="requestPath" value="${requestScope['javax.servlet.forward.request_uri']}"/>
<c:set var="queryString" value="${requestScope['javax.servlet.forward.query_string']}"/>

    <nav class="navbar navbar-expand-lg navbar-dark bg-dark sticky-top">
        <div class="container-fluid">
            <a class="navbar-brand" href="tests?page=1">///Home+Главная</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <a class="nav-link" aria-current="page" href="tests?page=1">///My tests</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="Account">///Account-Личный кабинет</a>
                    </li>
                    <c:if test="${sessionScope.userRole == 'admin'}">
                        <li class="nav-item">
                            <a class="nav-link" aria-current="page" href="/epam/createTest">///Add test</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" aria-current="page" href="/epam/users?page=1">///Users</a>
                        </li>
                    </c:if>
                </ul>
                <ul class="navbar-nav ml-auto mb-2 mb-lg-0 align-bottom">
                    <li class="nav-item align-bottom">
                        <div class="align-bottom">
                        <form class="align-bottom" action="/epam/locale/edit" method="POST">
                             <input type="hidden" name="prevUrl" value="${requestPath}${empty queryString ? '' : '?'}${queryString}">
                             <select class="form-select bg-light text-dark text-center" aria-label="Default select example" id="lang" name="lang" onchange="submit()">
                                 <option class="align-middle" value="en" ${cookie.lang.value == 'en' ? 'selected' : ''}><fmt:message key="lang.en" /></option>
                                 <option class="align-middle" value="ru" ${cookie.lang.value == 'ru' ? 'selected' : ''}><fmt:message key="lang.ru" /></option>
                             </select>
                        </form>
                        </div>
                    </li>
                   <!-- <li class="nav-item"><a class="nav-link btn-light text-dark active mr-2" aria-current="page">//Logged in as: <c:out value="${sessionScope.username}" /> </a>                   </li> -->
                    <li class="nav-item">
                        <a class="nav-link active mr-2" aria-current="page">  </a>
                    </li>
                    <li class="nav-item ml-2">
                        <form action="logout">
                            <input type="submit" class="btn btn-light" value="///Logout">
                        </form>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
</fmt:bundle>
