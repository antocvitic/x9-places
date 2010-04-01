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

<p>Try "John" as name or Scarlet</p>
<form action="/foodle-x9/Test" method="POST"><label for="Name">Namn:
</label><input id="Name" name="Name" type="text" /><br />

<input type="submit" value="Submit"></input></form>
<br />
<br />
<hr />
<p>Test searching/querying solr here</p>
<form action="/foodle-x9/Test" method="POST"><label
    for="VenueTitle">A Venue: </label><input id="VenueTitle"
    name="VenueTitle" type="text" /><br />

<input type="submit" value="Submit"></input></form>
<p>Some links:</p>
<a href="http://localhost:7777/solr/admin/">http://localhost:7777/solr/admin/</a>
<a href="http://localhost:8888/">Tomcat</a>


</body>
</html>