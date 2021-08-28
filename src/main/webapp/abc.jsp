<%@page import="java.util.ArrayList"%>
<%@page import="com.web.revision.Contact"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Display tag Pagination and Sorting Example in JSP</title>
        <link rel="stylesheet" href="css/displaytag.css" type="text/css">
        <link rel="stylesheet" href="css/screen.css" type="text/css">
        <link rel="stylesheet" href="css/site.css" type="text/css">

    </head>
    <body>

        <%
            List<Contact> players = new ArrayList<Contact>();
            players.add(new Contact("Virat", 98273633, "Mohali"));
            players.add(new Contact("Mahendara", 98273634, "Ranchi"));
            players.add(new Contact("Virender", 98273635, "Delhi"));
            players.add(new Contact("Ajinkya", 98273636, "Jaipur"));
            players.add(new Contact("Gautam", 98273637, "Delhi"));
            players.add(new Contact("Rohit", 98273638, "Mumbai"));
            players.add(new Contact("Ashok", 98273639, "Kolkata"));
            players.add(new Contact("Ravi", 98273640, "Chennai"));

            session.setAttribute("players", players);

            List<Contact> stars = new ArrayList<Contact>();
            stars.add(new Contact("Shahrukh", 98273633, "Delhi"));
            stars.add(new Contact("Sallu", 98273634, "Ranchi"));
            stars.add(new Contact("Roshan", 98273635, "Delhi"));
            stars.add(new Contact("Devgan", 98273636, "Jaipur"));
            stars.add(new Contact("Hashmi", 98273637, "Delhi"));
            stars.add(new Contact("Abraham", 98273638, "Mumbai"));
            stars.add(new Contact("Kumar", 98273639, "Kolkata"));
            stars.add(new Contact("Shetty", 98273640, "Chennai"));

            session.setAttribute("stars", stars);

        %>



        <div id='tab1' class="tab_content" style="display: block; width: 100%">
            <h3>Display tag Pagination and Sorting Example</h3>
            <p>This is FIRST TABLE </p>
            <display:table name="sessionScope.players" pagesize="5"
                           export="true" sort="list" uid="one">
                <display:column property="name" title="Name"
                                sortable="true" headerClass="sortable" />
                <display:column property="contact" title="Mobile"
                                sortable="true" headerClass="sortable" />
                <display:column property="city" title="Resident"
                                sortable="true" headerClass="sortable" />
            </display:table>
        </div>

        <div id='tab2' class="tab_content" style="width: 100%">
            <h3>Table 2</h3>
            <p>This is SECOND TABLE</p>
            <display:table name="sessionScope.stars" pagesize="5"
                           export="false" sort="list" uid="two">
                <display:column property="name" title="Name"
                                sortable="true" headerClass="sortable" />
                <display:column property="contact" title="Mobile"
                                sortable="true" headerClass="sortable" />
                <display:column property="city" title="Resident"
                                sortable="true" headerClass="sortable" />
            </display:table>
        </div>
    </body>
</html>


Read more: https://javarevisited.blogspot.com/2014/02/dispaly-tag-pagination-sorting-example-JSP-servlet.html#ixzz74bF6YmAz