<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Foodle by the most awesome X9</title>
</head>
<body>
<%
	java.util.Date d = new java.util.Date();
%>
<h1></h1>
<p>Today's date is <%=d.toString()%> and this jsp page worked!<br />
Lets get some data from mysql</p>
<br />

<p><a href="login.jsp">login/register</a> - <a href="logout">logout</a></p>

<hr/>
<p>TODO: Test searching/querying solr here</p>
<form action="/foodle-x9/Test" method="POST"><label
    for="VenueTitle">A Venue: </label><input id="VenueTitle"
    name="VenueTitle" type="text" /><br />

<input type="submit" value="Submit"></input></form>

<hr/>
<p>Some links:<br />
<a href="http://localhost:7777/solr/admin/">http://localhost:7777/solr/admin/</a>
<a href="http://localhost:8888/">Tomcat</a></p>

<hr/>
<p>Debug:<br />
<a href="hasher">hash stuff with jBCrypt</a> - <a href="dump-session">dump
current session</a> - <a href="dump-user">dump
user data</a></p>

</body>
</html>