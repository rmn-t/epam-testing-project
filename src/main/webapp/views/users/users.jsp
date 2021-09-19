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
        .status-green {
           background-color: #a2faba;
        }
        .status-red {
           background-color: #faa2a2;
        }
    </style>
    <title><fmt:message key="user.users" /></title>
</head>
    <body>


        <my:preventBack />

        <c:import url="/views/templates/navbar.jsp"></c:import>

        </br>

        <p class="fs-1 text-center mt-3"><fmt:message key="user.listOfUsers" /><p>
        <hr>

        <div class="container-fluid">
            <form action="/epam/users" method="GET">
                <div class="row">
                    <input type="hidden" name="page" value="1">
                    <div class="col"></div>
                    <div class="col-md-4 col-lg-2">
                        <div class="form-floating">
                             <select size="1" class="form-select bg-light text-dark text-center" aria-label="sort" id="sort" name="sort" onchange="submit()">
                                <option class="align-middle" value="id ASC" ${param.sort == 'id ASC' ? 'selected' : ''}><fmt:message key="sort.idAsc" /></option>
                                <option class="align-middle" value="id DESC" ${param.sort == 'id DESC' ? 'selected' : ''}><fmt:message key="sort.idDesc" /></option>
                                <option class="align-middle" value="username ASC" ${param.sort == 'username ASC' ? 'selected' : ''}><fmt:message key="sort.usernameAtoZ" /></option>
                                <option class="align-middle" value="username DESC" ${param.sort == 'username DESC' ? 'selected' : ''}><fmt:message key="sort.usernameZtoA" /></option>
                                <option class="align-middle" value="first_name ASC" ${param.sort == 'first_name ASC' ? 'selected' : ''}><fmt:message key="sort.firstnameAtoZ" /></option>
                                <option class="align-middle" value="first_name DESC" ${param.sort == 'first_name DESC' ? 'selected' : ''}><fmt:message key="sort.firstnameZtoA" /></option>
                                <option class="align-middle" value="last_name ASC" ${param.sort == 'last_name ASC' ? 'selected' : ''}><fmt:message key="sort.lastnameAtoZ" /></option>
                                <option class="align-middle" value="last_name DESC" ${param.sort == 'last_name DESC' ? 'selected' : ''}><fmt:message key="sort.lastnameZtoA" /></option>
                             </select>
                             <label class="text-center text-muted" for="sort"><fmt:message key="sorting" /></label>
                        </div>
                    </div>

                    <div class="col-md-4 col-lg-2">
                        <div class="form-floating">
                            <select size="1" class="form-select bg-light text-dark text-center" aria-label="Default select example" id="statusId" name="statusId" onchange="submit()">
                                <option class="align-middle" value="0" ${param.statusId == 0}><fmt:message key="status.all" /></option>
                                <c:forEach items="${requestScope['statuses']}" var="element">
                                    <option class="align-middle" value="${element.id}" ${param.statusId == element.id ? 'selected' : ''}><fmt:message key="status.${element.name}" /></option>
                                </c:forEach>
                             </select>
                             <label class="text-center text-muted" for="status"><fmt:message key="status" /></label>
                        </div>
                    </div>

                    <div class="col-md-4 col-lg-2">
                        <div class="form-floating">
                            <select size="1" class="form-select bg-light text-dark text-center" aria-label="Default select example" id="perPage" name="perPage" onchange="submit()">
                                <option class="align-middle" value="10" ${param.perPage == 10 ? 'selected' : ''}>10</option>
                                <option class="align-middle" value="25" ${param.perPage == 25 ? 'selected' : ''}>25</option>
                                <option class="align-middle" value="50" ${param.perPage == 50 ? 'selected' : ''}>50</option>
                            </select>
                            <label class="text-center text-muted" for="subject"><fmt:message key="recordsPerPage" /></label>
                        </div>
                    </div>

                    <div class="col"></div>
                </div>
            </form>
        </div>

        <hr>

        <table class="table table-light border">
            <thead class="table-info border">
                <th class="text-center align-middle">Id</th>
                <th class="text-center align-middle"><fmt:message key="user.username" /></th>
                <th class="text-center align-middle"><fmt:message key="user.firstName" /></th>
                <th class="text-center align-middle"><fmt:message key="user.lastName" /></th>
                <th class="text-center align-middle"><fmt:message key="user.newPassword" /></th>
                <th class="text-center align-middle"><fmt:message key="user.role" /></th>
                <th class="text-center align-middle"><fmt:message key="status" /></th>
                <th class="text-center align-middle" colspan="2"><fmt:message key="actions" /></th>
            </thead>
            <tbody>
                <c:forEach items="${requestScope['users']}" var="element">
                    <tr class="align-middle text-center">
                        <form method="POST" action="/epam/edit/user?id=${element.id}">
                            <td class="text-center align-middle"><input type="text" class="text-center align-middle" name="userId" value="${element.id}" disabled></td>
                            <td class="text-center align-middle"><input type="text" class="text-center align-middle" name="username" value="${element.username}" disabled></td>
                            <td class="text-center align-middle"><input type="text" class="text-center align-middle" name="firstName" value="${element.firstName}"
                                required pattern="[-\sаАбБвВгГдДеЕёЁжЖзЗиИйЙкКлЛмМнНоОпПрРсСтТуУфФхХцЦчЧшШщЩъЪыЫьЬэЭюЮяЯҐґІіЇїЄєA-Za-z]+"
                                oninvalid="setCustomValidity('<fmt:message key='msg.loginValidation' />')"
                                onchange="try{setCustomValidity('')}catch(e){}"
                                maxlength="20"
                                minlength="2">
                            </td>
                            <td class="text-center align-middle"><input type="text" class="text-center align-middle" name="lastName" value="${element.lastName}"
                                required pattern="[-\sаАбБвВгГдДеЕёЁжЖзЗиИйЙкКлЛмМнНоОпПрРсСтТуУфФхХцЦчЧшШщЩъЪыЫьЬэЭюЮяЯҐґІіЇїЄєA-Za-z]+"
                                oninvalid="setCustomValidity('<fmt:message key='msg.loginValidation' />')"
                                onchange="try{setCustomValidity('')}catch(e){}"
                                maxlength="20"
                                minlength="2"
                            >
                            </td>
                            <td class="text-center align-middle"><input type="text" class="text-center align-middle" name="newPass" value="" placeholder="******"
                                pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,20}"
                                oninvalid="setCustomValidity('<fmt:message key='msg.passwordValidation' />')"
                                onchange="try{setCustomValidity('')}catch(e){}"
                                maxlength="20">
                            </td>
                            <td class="text-center align-middle">
                                <select class="form-select" name="roleId" id="roleId">
                                    <c:forEach items="${requestScope['roles']}" var="el">
                                        <option value="${el.id}" ${element.roleId == el.id ? 'selected' : ''}><fmt:message key="role.${el.name}" /></option>
                                    </c:forEach>
                                </select>
                            </td>

                            <td class="text-center align-middle">

                            <c:if test="${element.statusId == 1}">
                                <select class="form-select status-green text-center" name="statusId" id="statusId">
                            </c:if>
                            <c:if test="${element.statusId == 2}">
                                <select class="form-select status-red text-center" name="statusId" id="statusId">
                            </c:if>
                                <c:forEach items="${requestScope['statuses']}" var="el">
                                    <option value="${el.id}" ${element.statusId == el.id ? 'selected' : ''}><fmt:message key="status.${el.name}" /></option>
                                </c:forEach>
                                </select>
                            </td>
                            <td class="text-center align-middle">
                                <input class="btn btn-success" type="submit" value="<fmt:message key='save' />">
                                <input type="hidden" name="salt" value="${element.salt}"></td>
                                <input type="hidden" name="oldPass" value="${element.password}">
                                <input type="hidden" name="prevUrl" value="<my:getCurrUrl />">
                            </form>
                            </td>
                            <td>
                                <form method="POST" action="/epam/delete/user">
                                    <input type="hidden" name="prevUrl" value="<my:getCurrUrl />">
                                    <input type="hidden" name="userId" value="${element.id}">
                                    <input class="btn btn-danger" type="submit" value="<fmt:message key='delete' />">
                                </form>
                            </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <nav aria-label="Page navigation">
            <ul class="pagination justify-content-center">
                <c:if test="${param.page eq 1}">
                    <li class="page-item disabled">
                        <a class="page-link bg-light text-dark ml-2" href="#" tabindex="-1" aria-disabled="true"><fmt:message key="first" /></a>
                    </li>
                </c:if>
                <c:if test="${param.page gt 1}">
                    <li class="page-item">
                        <a class="page-link bg-success text-light" href="/epam/users?page=1&sort=${param.sort}&statusId=${param.statusId}&perPage=${param.perPage}" tabindex="-1" aria-disabled="true"><fmt:message key="first" /></a>
                    </li>
                </c:if>
                <c:if test="${param.page eq 1}">
                    <li class="page-item disabled">
                        <a class="page-link bg-light text-dark ml-2" href="#" tabindex="-1" aria-disabled="true"><fmt:message key="previous" /></a>
                    </li>
                </c:if>
                <c:if test="${param.page gt 1}">
                    <li class="page-item">
                        <a class="page-link bg-dark text-light ml-2" href="/epam/users?page=${param.page-1}&sort=${param.sort}&statusId=${param.statusId}&perPage=${param.perPage}"><fmt:message key="previous" /></a>
                    </li>
                </c:if>
                <c:if test="${param.page eq lastPage}">
                    <li class="page-item disabled">
                        <a class="page-link bg-light text-dark ml-2" href="#" tabindex="-1" aria-disabled="true"><fmt:message key="next" /></a>
                    </li>
                </c:if>
                <c:if test="${param.page lt lastPage}">
                    <li class="page-item">
                        <a class="page-link bg-dark text-light ml-2" href="/epam/users?page=${param.page+1}&sort=${param.sort}&statusId=${param.statusId}&perPage=${param.perPage}"><fmt:message key="next" /></a>
                    </li>
                </c:if>
                <c:if test="${param.page eq lastPage}">
                    <li class="page-item disabled">
                        <a class="page-link bg-light text-dark ml-2" href="#" tabindex="-1" aria-disabled="true"><fmt:message key="last" /></a>
                    </li>
                </c:if>
                <c:if test="${param.page lt lastPage}">
                    <li class="page-item">
                        <a class="page-link bg-success text-light" href="/epam/users?page=${lastPage}&sort=${param.sort}&statusId=${param.statusId}&perPage=${param.perPage}" tabindex="-1" aria-disabled="true"><fmt:message key="last" /></a>
                    </li>
                </c:if>
            </ul>
        </nav>

        </fmt:bundle>
    </body>
</html>