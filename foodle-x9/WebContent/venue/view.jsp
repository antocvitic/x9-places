<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@ taglib tagdir="/WEB-INF/tags" prefix="h"%>
<%@ taglib tagdir="/WEB-INF/tags/gmaps" prefix="gmaps"%>

<%@page import="java.util.*"%>
<%@page import="com.x9.foodle.venue.*"%>
<%@page import="com.x9.foodle.review.*"%>
<%@page import="com.x9.foodle.util.*"%>
<%!
VenueModel venue;
List<ReviewModel> reviews;
%>
<%
	String venueID = request.getParameter("venueID");

	if (venueID != null && !venueID.isEmpty()) {
		venue = VenueModel.getFromSolr(venueID);
		if (venue == null) {
			throw new RuntimeException("no venue with id: " + venueID);
		}
        reviews = venue.getReviews(25);
	} else {
		throw new RuntimeException("null or empty venueID!");
	}
%>
<h:header title="View venue - Foodle X9">
    <gmaps:include />
</h:header>
<h:headercontent />

<p><strong style="color: red">TODO:</strong></p>

<h1><%=venue.getTitle()%></h1>

<a
    href="${pageContext.request.contextPath}/venue/edit.jsp?venueID=<%=venueID %>">edit
this venue</a>

<p>Description: <%=venue.getDescription()%></p>

<p>Address: <%=venue.getAddress()%></p>

<p><gmaps:address address="<%=venue.getAddress()%>" id="gmaps_view1"
    css="gmaps_div" /></p>

<p>Tags: <%=StringUtils.join(venue.getTags(), ", ")%></p>

<a
    href="${pageContext.request.contextPath}/review/edit.jsp?venueID=<%=venueID %>">Write
a review for this venue</a>

<hr />

<h:reviews reviews="<%=reviews %>" />

<h:footer />