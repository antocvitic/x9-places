<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h"%>
<h:header title="Foodle X9 - Your profile"></h:header>
<h:headercontent />

<%@page import="com.x9.foodle.util.DateUtils"%>
<%
	java.util.Date d = DateUtils.getNowUTC();
%>

<div id="contentarea">

<p>Today's date is <%=DateUtils.dateToSolrDate(d)%> (UTC) and this
jsp page worked!
</p>
<br /><br />

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
data</a>
<br />
Showing your cookies:
<%
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

if (com.x9.foodle.user.UserUtils.getCurrentUser(request) != null) {
	out.print("You're logged in as:"); 
	out.print(com.x9.foodle.user.UserUtils.getCurrentUser(request).getUsername());
}
%>

</p>

</div>
<jsp:include page="includes/footer.jsp" /> 
