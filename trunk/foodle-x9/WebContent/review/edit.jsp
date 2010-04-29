<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%
	String venueID = request.getParameter("venueID");
	String reviewID = request.getParameter("reviewID");
%>

<h:header title="Foodle X9 - Your profile"></h:header>
<h:headercontent />

<h3 class="my_header">Add or edit review</h3>
<h:review_edit reviewID="<%= reviewID %>" venueID="<%= venueID %>" />

<h:footer />