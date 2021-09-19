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
    <title><fmt:message key="account" /></title>
</head>
    <body>

        <my:preventBack />

        <c:import url="/views/templates/navbar.jsp"></c:import>

        <div style="margin-top: 2%;"class="container">
            <div class="row align-items-center">
                <div class="col"></div>
                <div class="col-lg-4 col-md-6 col-sm-8">
                    <div class="card">
                        <article class="card-body">
                            <h4 class="card-title text-center mb-4 mt-1"><fmt:message key="account.editDetails" /></h4>
                            <hr>
                            <c:if test="${not empty updStatus}">
                                <div class="alert alert-info alert-dismissible fade show text-center" role="alert">
                                    <strong><fmt:message key="msg.accountInfoUpdated" /></strong>
                                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                                </div>
                            </c:if>
                            <form action="account" method="POST">
                                <div class="mb-2 text-center">
                                    <label for="username" class="form-label"><fmt:message key="user.login" /></label>
                                    <input type="text" value="${sessionScope.currentUser.username}" class="form-control" name="username" id="username" placeholder="<fmt:message key="user.login" />" type="text" required disabled>
                                </div>
                                <div class="mb-2 text-center">
                                    <label for="password" class="form-label"><fmt:message key="user.newPassword" /></label>
                                    <input type="password" class="form-control" name="password" id="password" placeholder=""
                                    pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,20}"
                                    oninvalid="setCustomValidity('<fmt:message key='msg.passwordValidation' />')"
                                    onchange="try{setCustomValidity('')}catch(e){}"
                                    maxlength="20"
                                    >
                                </div>
                                <div class="mb-2 text-center">
                                    <label for="firstName" class="form-label"><fmt:message key="user.firstName" /></label>
                                    <input type="text" value="${sessionScope.currentUser.firstName}" class="form-control" name="firstName" id="firstName" placeholder="<fmt:message key="user.firstName" />"
                                    required pattern="[-\sаАбБвВгГдДеЕёЁжЖзЗиИйЙкКлЛмМнНоОпПрРсСтТуУфФхХцЦчЧшШщЩъЪыЫьЬэЭюЮяЯҐґІіЇїЄєA-Za-z]+"
                                    oninvalid="setCustomValidity('<fmt:message key='msg.firstLastNameValidation' />')"
                                    onchange="try{setCustomValidity('')}catch(e){}"
                                    maxlength="20"
                                    minlength="2"
                                    >
                                </div>
                                <div class="mb-2 text-center">
                                    <label for="lastName" class="form-label"><fmt:message key="user.lastName" /></label>
                                    <input type="text" value="${sessionScope.currentUser.lastName}" class="form-control" name="lastName" id="lastName" placeholder="<fmt:message key="user.lastName" />"
                                    required pattern="[-\sаАбБвВгГдДеЕёЁжЖзЗиИйЙкКлЛмМнНоОпПрРсСтТуУфФхХцЦчЧшШщЩъЪыЫьЬэЭюЮяЯҐґІіЇїЄєA-Za-z]+"
                                    oninvalid="setCustomValidity('<fmt:message key='msg.firstLastNameValidation' />')"
                                    onchange="try{setCustomValidity('')}catch(e){}"
                                    maxlength="20"
                                    minlength="2"
                                    >
                                </div>
                                <div class="container text-center mt-3">
                                    <div class="row ">
                                        <div class="col"></div>
                                        <div class="col-sm-7 mb-1"><button type="submit" class="btn btn-success btn-block"><fmt:message key="saveChanges" /> &check;</button></a></div>
                                        <div class="col"></div>
                                    </div>
                                </div>
                            </form>
                        </article>
                    </div>
                </div>
                <div class="col"></div>
            </div> <!-- row.// -->
        </div>







        </fmt:bundle>
    </body>
</html>