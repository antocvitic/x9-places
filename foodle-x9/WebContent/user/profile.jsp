<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h"%>
<%@ page import="com.x9.foodle.user.*" %>
<%@ page import="com.x9.foodle.venue.*" %>
<%@ page import="com.x9.foodle.review.*" %>
<%@ page import="com.x9.foodle.comment.*" %>
<%@ page import="com.x9.foodle.datastore.*" %>
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

<h1>Profile</h1>
Your reputation level:
<%
out.println(user.getReputationLevel());
%>
<br />
Your venues: <br/>
<%
ModelList<VenueModel> venues = VenueModel.getFromSolrCreatedBy(user, 0, 12, VenueModel.sf(VenueModel.SortableField.TITLE, SortField.Order.DESC));
%>
Showing <%= venues.getResultsReturned() %> venues of <%= venues.getResultsFound() %>, starting at <%= venues.getOffset() %><br/>
<% for (VenueModel venue : venues.getList()) { %>
<a href="${pageContext.request.contextPath}/venue/view.jsp?venueID=<%= venue.getID() %>"><%= venue.getTitle() %></a><br/>
<% } %>
<hr />

Your reviews:
<%
ModelList<ReviewModel> reviews = ReviewModel.getFromSolrCreatedBy(user, 0, 12, ReviewModel.sf(ReviewModel.SortableField.VENUE_ID, SortField.Order.ASC));
%>
Showing <%= reviews.getResultsReturned() %> reviews of <%= reviews.getResultsFound() %>, starting at <%= reviews.getOffset() %>
<h:reviews reviews="<%= reviews %>" />
<hr />

Your comments:
<%
ModelList<CommentModel> comments = CommentModel.getFromSolrCreatedBy(user, 0, 12, CommentModel.sf(CommentModel.SortableField.TIME_ADDED, SortField.Order.ASC));
%>
Showing <%= comments.getResultsReturned() %> comments of <%= comments.getResultsFound() %>, starting at <%= comments.getOffset() %><br/>
<h:comments review="<%= null %>" comments="<%= comments %>" enableNewComments="false"></h:comments>
<hr />



<h:footer />
