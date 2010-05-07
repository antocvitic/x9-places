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
			out.println(user.getUsername() +"'s Profile");
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
ModelList<VenueModel> venues = VenueModel.getFromSolrCreatedBy(user, new Pager(request, "v"));
%>
<h:pager_header mlist="<%= venues %>"/><br/>
<%= venues.getPager().toString() %><br/>
Showing <%= venues.getResultsReturned() %> venues of <%= venues.getResultsFound() %>, starting at <%= venues.getOffset() %><br/>
<% for (VenueModel venue : venues.getList()) { %>
<a href="${pageContext.request.contextPath}/venue/view.jsp?venueID=<%= venue.getID() %>"><%= venue.getTitle() %></a><br/>
<% } %>
<hr />

Your reviews:
<%
ModelList<ReviewModel> reviews = ReviewModel.getFromSolrCreatedBy(user, new Pager(request, "r"));
%>
Showing <%= reviews.getResultsReturned() %> reviews of <%= reviews.getResultsFound() %>, starting at <%= reviews.getOffset() %>
<h:reviews reviews="<%= reviews %>" />
<hr />

Your comments:
<%
ModelList<CommentModel> comments = CommentModel.getFromSolrCreatedBy(user, new Pager(new SortField(SortableFields.TITLE)));
%>
Showing <%= comments.getResultsReturned() %> comments of <%= comments.getResultsFound() %>, starting at <%= comments.getOffset() %><br/>
<h:comments review="<%= null %>" comments="<%= comments %>" enableNewComments="false"></h:comments>
<hr />



<h:footer />
