<%@ page language ="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="my" uri="/tld/MyTagsDescriptor.tld"%>

<fmt:setLocale value="${empty cookie.lang.value ? 'en' : cookie.lang.value }"/>
<fmt:bundle basename="messages">

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
    <title><fmt:message key="login.sign-in" /></title>
</head>
    <body>
        <div style="margin-top: 5%;"class="container">
            <div class="row align-items-center">
                <div class="col"></div>
                <div class="col-lg-4 col-md-6 col-sm-8">
                    <div class="card">
                        <article class="card-body">
                            <h4 class="card-title text-center mb-4 mt-1"><fmt:message key="msg.loginInToSystem" /></h4>
                            <hr>
                            <c:if test="${not empty loginStatus}">
                                <div class="alert alert-danger alert-dismissible fade show text-center" role="alert">
                                    <strong><fmt:message key="msg.${loginStatus}" /></strong>
                                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                                </div>
                            </c:if>
                            <form action="login" method="POST">
                            <div class="mb-2 text-center">
                                <label for="username" class="form-label"><fmt:message key="user.login" /></label>
                                <input type="text" class="form-control" name="username" id="username" placeholder="<fmt:message key="user.login" />" value="admin" maxlength="25" minlength="3" required>
                            </div>
                            <div class="mb-2 text-center">
                                <label for="password" class="form-label"><fmt:message key="user.password" /></label>
                                <input class="form-control" id="password" placeholder="******" type="password" name="password" value="testqq" maxlength="20" required>
                            </div>
                            <div class="container text-center">
                                <div class="row">
                                    <div class="col-sm-1"></div>
                                    <div class="col-sm-5 mb-1"><button type="submit" class="btn btn-primary btn-block"><fmt:message key="login.sign-in" /></button></div>
                                    <div class="col-sm-5 mb-1"><a href="register" ><button type="button" class="btn btn-dark btn-block"><fmt:message key="msg.register" /></button></a></div>
                                    <div class="col-sm-1"></div>
                                </div>
                            </div>
                            </form>
                        </article>
                    </div>
                </div>
                <div class="col"></div>
            </div> <!-- row.// -->
        </div>
        <hr>
        <div class="container">
            <div class="row">
                <div class="col"></div>
                <div class="col-lg-2 col-md-6 col-sm-8">
                    <form action="/epam/locale/edit" method="POST">
                         <input type="hidden" name="prevUrl" value="<my:getCurrUrl />">
                         <select class="form-select text-center" aria-label="Default select example" id="lang" name="lang" onchange="submit()">
                             <option value="en" ${cookie.lang.value == 'en' ? 'selected' : ''}><fmt:message key="lang.en" /></option>
                             <option value="ru" ${cookie.lang.value == 'ru' ? 'selected' : ''}><fmt:message key="lang.ru" /></option>
                         </select>
                    </form>
                </div>
                <div class="col"></div>
            <div class="row"></div>
        </div>
        </fmt:bundle>
    </body>
</html>