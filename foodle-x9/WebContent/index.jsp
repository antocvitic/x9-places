<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Foodle by the most awesome X9</title>
</head>
<body>
<%@page import="com.x9.foodle.util.DateUtils"%>
<%
	java.util.Date d = DateUtils.getNowUTC();
%>
<h1></h1>
<p>Today's date is <%=DateUtils.dateToSolrDate(d)%> (UTC) and this
jsp page worked!<br />
Lets get some data from mysql</p>
<br />

<p><a href="${pageContext.request.contextPath}/login.jsp">login/register</a>
- <a href="${pageContext.request.contextPath}/logout">logout</a></p>

<p><a href="${pageContext.request.contextPath}/venue/edit.jsp">add
venue</a></p>

<hr />

Stuff

<hr />
<p>Some links:<br />
<a href="http://localhost:7777/solr/admin/">http://localhost:7777/solr/admin/</a>
<a href="http://localhost:8888/">Tomcat</a></p>

<hr />
<p>Debug:<br />
<a href="${pageContext.request.contextPath}/hasher">hash stuff with
jBCrypt</a> - <a href="${pageContext.request.contextPath}/dump-session">dump
current session</a> - <a
    href="${pageContext.request.contextPath}/dump-model">dump model
data</a></p>

</body>
</html>