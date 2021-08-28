<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Devices</title>
    <link rel="stylesheet" href="../style/ownstyles.css">
</head>
<body class="secbody">

    <c:set var="listSize" value="${fn:length(devices)}"/>

    <%--<jsp:useBean id="pagedData" class="by.epam.task5.bean.DevicesContainer"/>
    <bean:size id="listSize" name="pagedData" property="computers"/>--%>

    <c:set var="pageSize" value="7"/>
    <c:set var="pageBegin" value="${param.pageBegin}"/>
    <c:set var="pageEnd" value="${pageBegin + pageSize - 1}"/>

    <div>
        <table align="center">
            <tr>
                <th rowspan="3">ID</th>
                <th rowspan="3">Name</th>
                <th rowspan="3">Origin</th>
                <th rowspan="3">Price</th>
                <th colspan="6">Type</th>
                <th rowspan="3">Critical</th>
            </tr>
            <tr>
                <th rowspan="2">Peripheral</th>
                <th rowspan="2">Power</th>
                <th rowspan="2">Ports</th>
                <th colspan="3">Hardware</th>
            </tr>
            <tr>
                <th>Keyboard</th>
                <th>Mouse</th>
                <th>Speakers</th>
            </tr>
            <c:forEach var="computer" items="${devices}" begin="${pageBegin}" end="${pageEnd}">
                <tr>
                    <td>${computer.id}</td>
                    <td>${computer.name}</td>
                    <td>${computer.origin}</td>
                    <td>${computer.price}</td>
                    <td>${computer.type.peripheral}</td>
                    <td>${computer.type.power}</td>
                    <td>
                        <c:forEach var="port" items="${computer.type.ports}">
                            <c:out value="${port.portName}"></c:out>
                        </c:forEach>
                    </td>
                    <td>${computer.type.hardware.hasKeyboard}</td>
                    <td>${computer.type.hardware.hasMouse}</td>
                    <td>${computer.type.hardware.hasSpeakers}</td>
                    <td>${computer.critical}</td>
                </tr>
            </c:forEach>
        </table>

        <div style="text-align: center">

            <c:if test="${(pageBegin - pageSize) ge 0}">
                <a class="btngo" href='<c:url value="/jsp/table.jsp">
                        <c:param name="pageBegin" value="${pageBegin - pageSize}"/>
                    </c:url>'>
                    &larr;
                </a>
            </c:if>

            &nbsp;

            <c:if test="${(listSize gt pageSize) and (pageEnd lt listSize)}">
                <a class="btngo" href='<c:url value="/jsp/table.jsp">
                   <c:param name="pageBegin" value="${pageBegin + pageSize}"/>
                 </c:url>'>
                    &rarr;
                </a>
            </c:if>
        </div>

        <a class="btnback" href="../index.jsp">home</a>

    </div>

</body>
</html>