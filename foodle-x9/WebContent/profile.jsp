<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h"%>
<%@ page import="com.x9.foodle.user.*"%>


<%
UserModel user = UserUtils.getCurrentUser(request, response);
%>

<h:header>
    <jsp:attribute name="title">
	<%@page import="com.x9.foodle.user.UserModel"%>
	<%
		if (user != null) {
			out.println("Got a user by id: " + user.getUsername());
		} else {
			out.println("Got null user");
		}
	%>
</jsp:attribute>
</h:header>
<h:headercontent />

<%@page import="com.x9.foodle.util.DateUtils"%>
<%
/*<jsp:include page="/includes/header.jsp" />*/ 
	java.util.Date d = DateUtils.getNowUTC();
%>

<div id="contentarea">

<p>Today's date is <%=DateUtils.dateToSolrDate(d)%> (UTC) and this
jsp page worked!</p>
<br />
<br />
<p>This is ey profile page,</p>

<p>Your latest venues: <a
    href="${pageContext.request.contextPath}/venue/edit.jsp">edit
venue</a></p>
<hr />
<p>Friends venues:</p>
<hr />
<p>New reviews:</p>
<hr />
<p>Other stuff:</p>
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
data</a> <br />
Showing your cookies: <%
Cookie[] cookies = request.getCookies();
for (int i=0; i<cookies.length; i++) {
	out.println(cookies[i].getName()+":\t"+
	cookies[i].getValue()+"<br />");
}

if (session.getAttribute("logged_in_userid") != null) {
out.print("<br /><p>Showing session data</p>");
out.print(session.getAttribute("logged_in_userid").toString() + "<br />");
out.print(session.getAttribute("logged_in_session_token").toString() + "<br />");
}

if (user != null) {
	out.print("You're logged in as:<br />"); 
	out.print(user.getUsername());
}
%>
</p>

</div>
<h:footer />
