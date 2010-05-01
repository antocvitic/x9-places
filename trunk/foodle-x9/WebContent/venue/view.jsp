<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@ taglib tagdir="/WEB-INF/tags" prefix="h"%>
<%@ taglib tagdir="/WEB-INF/tags/gmaps" prefix="gmaps"%>

<%@page import="java.util.*"%>
<%@page import="com.x9.foodle.user.*"%>
<%@page import="com.x9.foodle.venue.*"%>
<%@page import="com.x9.foodle.review.*"%>
<%@page import="com.x9.foodle.util.*"%>
<%!
UserModel user;
VenueModel venue;
List<ReviewModel> reviews;
%>
<%
user = UserUtils.getCurrentUser(request, response);
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

<!-- Text content -->
<div class="venue">
	<div id="venue_text">
		<div id="venue_title_div">
			<h1 id="venue_title"><%=venue.getTitle()%></h1>
			<a href="${pageContext.request.contextPath}/venue/edit.jsp?venueID=<%=venueID %>" 
			id="venue_edit">Edit</a>
		</div>
		<div id="venue_info_div">
			<p>Average rating: <%=venue.getAverageRating()%> - Number of raters: <%= venue.getNumberOfRatings() %></p>
			<h:venue_rater venue="<%= venue %>" />
			
			<p id="address"><%=venue.getAddress()%></p>
		</div>
		
		<div id="venue_description_div">
			<p><%=venue.getDescription()%></p>
		</div>

		<div id=venue_tags>
			<p>
			<!-- getTags() returns an ArrayList (1 May 2010) -->
			<%
			 ArrayList<String> tagsList = venue.getTags();
			 Iterator tagsIterator = tagsList.iterator();
			 while (tagsIterator.hasNext()) {
			 %>
			<small id="venue_tag"><%= tagsIterator.next()%></small>
			<% 
			 }%>	
			 </p>
		</div>
		<a href="${pageContext.request.contextPath}/review/edit.jsp?venueID=<%=venueID %>" 
		id="venue_review">Write a review</a>
		
	</div> <!-- end of venue_text -->
	
	<!-- map for the venue -->
	<div id="venue_map">
		<p><gmaps:address address="<%=venue.getAddress()%>" id="gmaps_view1" css="gmaps_div" /></p>
	</div>
	
	
	
</div> <!-- end of venue -->

<h:reviews reviews="<%=reviews %>" />

<h:footer />