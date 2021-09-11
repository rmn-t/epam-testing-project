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
        .status-green {
           background-color: #a2faba;
        }
        .status-red {
           background-color: #faa2a2;
        }
    </style>
    <title><c:out value="${test.name}"></c:out></title>
</head>
    <body>

        <fmt:setLocale value="${cookie.lang.value}"/>
        <fmt:bundle basename="messages">
        <my:preventBack />

        <c:import url="/views/templates/navbar.jsp"></c:import>

        </br>

        <p class="fs-1 text-center mt-3">///List of users in the system<p>

        <hr>

        <div class="container-fluid">
            <form action="/epam/users" method="GET">
                <div class="row">
                    <input type="hidden" name="page" value="1">
                    <div class="col"></div>
                    <div class="col-md-4 col-lg-2">
                        <div class="form-floating">
                             <select size="1" class="form-select bg-light text-dark text-center" aria-label="sort" id="sort" name="sort" onchange="submit()">
                                <option class="align-middle" value="id ASC" ${param.sort == 'id ASC' ? 'selected' : ''}>///id A-Z</option>
                                <option class="align-middle" value="id DESC" ${param.sort == 'id DESC' ? 'selected' : ''}>///id Z-A</option>
                                <option class="align-middle" value="username ASC" ${param.sort == 'username ASC' ? 'selected' : ''}>///Username A-Z</option>
                                <option class="align-middle" value="username DESC" ${param.sort == 'username DESC' ? 'selected' : ''}>///Username Z-A</option>
                                <option class="align-middle" value="first_name ASC" ${param.sort == 'first_name ASC' ? 'selected' : ''}>///First name A-Z</option>
                                <option class="align-middle" value="first_name DESC" ${param.sort == 'first_name DESC' ? 'selected' : ''}>///First name Z-A</option>
                                <option class="align-middle" value="last_name ASC" ${param.sort == 'last_name ASC' ? 'selected' : ''}>///Last name A-Z</option>
                                <option class="align-middle" value="last_name DESC" ${param.sort == 'last_name DESC' ? 'selected' : ''}>///Last name Z-A</option>
                             </select>
                             <label class="text-center text-muted" for="sort">///Sorting:</label>
                        </div>
                    </div>

                    <div class="col-md-4 col-lg-2">
                        <div class="form-floating">
                            <select size="1" class="form-select bg-light text-dark text-center" aria-label="Default select example" id="statusId" name="statusId" onchange="submit()">
                                <option class="align-middle" value="0" ${param.statusId == 0}>///All statuses</option>
                                <c:forEach items="${requestScope['statuses']}" var="element">
                                    <option class="align-middle" value="${element.id}" ${param.statusId == element.id ? 'selected' : ''}>///${element.name}</option>
                                </c:forEach>
                             </select>
                             <label class="text-center text-muted" for="status">///Status:</label>
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

        <table class="table table-light border">
            <thead class="table-info border">
                <th class="text-center align-middle">///Id</th>
                <th class="text-center align-middle">///username</th>
                <th class="text-center align-middle">///first name</th>
                <th class="text-center align-middle">///last name</th>
                <th class="text-center align-middle">///new password</th>
                <th class="text-center align-middle">///role</th>
                <th class="text-center align-middle">///status</th>
                <th class="text-center align-middle" colspan="2">///actions</th>
            </thead>
            <tbody>
                <c:forEach items="${requestScope['users']}" var="element">
                    <tr class="align-middle text-center">
                        <form method="POST" action="/epam/edit/user?id=${element.id}">
                            <td class="text-center align-middle"><input type="text" class="text-center align-middle" name="userId" value="${element.id}" disabled></td>
                            <td class="text-center align-middle"><input type="text" class="text-center align-middle" name="username" value="${element.username}" disabled></td>
                            <td class="text-center align-middle"><input type="text" class="text-center align-middle" name="firstName" value="${element.firstName}"></td>
                            <td class="text-center align-middle"><input type="text" class="text-center align-middle" name="lastName" value="${element.lastName}"></td>
                            <td class="text-center align-middle"><input type="text" class="text-center align-middle" name="newPass" value="" placeholder="******"></td>
                            <td class="text-center align-middle">
                                <select class="form-select" name="roleId" id="roleId">
                                    <c:forEach items="${requestScope['roles']}" var="el">
                                        <option value="${el.id}" ${element.roleId == el.id ? 'selected' : ''}>///${el.name}</option>
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
                                    <option value="${el.id}" ${element.statusId == el.id ? 'selected' : ''}>///${el.name}</option>
                                </c:forEach>
                                </select>
                            </td>
                            <td class="text-center align-middle">
                                <input class="btn btn-success" type="submit" value="///Save">
                                <input type="hidden" name="salt" value="${element.salt}"></td>
                                <input type="hidden" name="oldPass" value="${element.password}">
                                <input type="hidden" name="prevUrl" value="<my:getCurrUrl />">
                            </form>
                            </td>
                            <td>
                                <form method="POST" action="/epam/delete/user">
                                    <input type="hidden" name="prevUrl" value="<my:getCurrUrl />">
                                    <input type="hidden" name="userId" value="${element.id}">
                                    <input class="btn btn-danger" type="submit" value="///Delete">
                                </form>
                            </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <nav aria-label="Page navigation">
            <ul class="pagination justify-content-center">
                <li class="page-item">
                    <a class="page-link bg-success text-light" href="/epam/users?page=1&sort=${param.sort}&statusId=${param.statusId}&perPage=${param.perPage}" tabindex="-1" aria-disabled="true">///First</a>
                </li>
                <c:if test="${param.page eq 1}">
                    <li class="page-item disabled">
                        <a class="page-link bg-light text-dark ml-2" href="#" tabindex="-1" aria-disabled="true">///Previous</a>
                    </li>
                </c:if>
                <c:if test="${param.page gt 1}">
                    <li class="page-item">
                        <a class="page-link bg-dark text-light ml-2" href="/epam/users?page=${param.page-1}&sort=${param.sort}&statusId=${param.statusId}&perPage=${param.perPage}">///Previous</a>
                    </li>
                </c:if>
                <c:if test="${param.page eq lastPage}">
                    <li class="page-item disabled">
                        <a class="page-link bg-light text-dark ml-2" href="#" tabindex="-1" aria-disabled="true">///Next</a>
                    </li>
                </c:if>
                <c:if test="${param.page lt lastPage}">
                    <li class="page-item">
                        <a class="page-link bg-dark text-light ml-2" href="/epam/users?page=${param.page+1}&sort=${param.sort}&statusId=${param.statusId}&perPage=${param.perPage}">///Next</a>
                    </li>
                </c:if>
                <li class="page-item">
                    <a class="page-link bg-success text-light" href="/epam/users?page=${lastPage}&sort=${param.sort}&statusId=${param.statusId}&perPage=${param.perPage}" tabindex="-1" aria-disabled="true">///Last</a>
                </li>
            </ul>
        </nav>

        </fmt:bundle>
    </body>
</html>