<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@page import="com.x9.foodle.venue.VenueModel"%>
<%
	String venueID = request.getParameter("id");
	VenueModel venue = VenueModel.getFromSolr(venueID);
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@ include file="/WEB-INF/includes/header_includes.jspf"%>
<style>

div {
    border: 1px solid black;
}

#header {
    display: block;
    padding: 20px;
    background-color: rgb(170, 200, 185);
    text-align: center;
}

#middle {
    display: block;
    height: 100%;
    background-color: rgb(220, 130, 130);
}

#left_sidebar {
    display: block;
	float: left;
	height: 100%;
	padding: 20px;
	background-color: rgb(220, 220, 230);
}

#center {
    display: block;
	padding: 20px;
    height: 100%;
	background-color: rgb(220, 230, 230);
}

#right_sidebar {
    display: block;
	float: right;
	height: 100%;
	padding: 20px;
	background-color: rgb(220, 230, 220);
}

#footer {
    display: block;
    padding: 20px;
    background-color: rgb(170, 185, 200);
    text-align: center;
}

#gmaps {
	display: block;
	width: 200px;
	height: 200px;
	background-color: lightblue;
}
</style>

<title>Venue: <%=venueID%> - Foodle-X9</title>
</head>
<body>



<div id="content">

<div id="header">[....Logo... ] <input type="text"
    value="Insert search terms here" /> <input type="submit"
    value="Search" /></div>

<div id="middle">
<div id="left_sidebar">
<ul>
    <li>asdfasdf</li>
    <li>asdfasdf</li>
    <li>asdfasdf</li>
    <li>asdfasdf</li>
    <li>asdfasdf</li>
    <li>asdfasdf</li>
    <li>asdfasdf</li>
    <li>asdfasdf</li>
    <li>asdfasdf</li>
    <li>asdfasdf</li>
    <li>asdfasdf</li>
    <li>asdfasdf</li>
    <li>asdfasdf</li>
    <li>asdfasdf</li>
    <li>asdfasdf</li>
</ul>
hejsan
</div>
<div id="right_sidebar">Karta:
<div id="gmaps"></div>
</div>


<div id="center">

<h1>Venue</h1>

Venue: <%=venue%></div>
</div>
<div id="footer">
Detta är vår footer
</div>

</div>

</body>
</html>