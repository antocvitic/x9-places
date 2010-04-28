<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h"%>
<h:header title="Foodle X9 - The most awesome venue search"></h:header>
<h:headercontent />

<%@page import="com.x9.foodle.util.DateUtils"%>
<%
	java.util.Date d = DateUtils.getNowUTC();
%>

<p>Today's date is <%=DateUtils.dateToSolrDate(d)%> (UTC) and this
jsp page worked!</p>
<br />
<br />


<hr />

Stuff

<hr />
<p>Some links:<br />
<a href="http://localhost:8983/solr/admin">http://localhost:8983/solr</a>
<a href="http://localhost:8888/">Tomcat</a></p>

<hr />

<h:footer />
