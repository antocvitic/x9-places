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
String title = user.getUsername() + "'s profile page - Foodle X9";
%>

<h:header title="<%= title %>">
    <script type="text/javascript">
    $(function(){
        // init tabs
        $('#tabs').tabs();
        $("#tabs").bind("tabsshow", function(event, ui) { 
            window.location.hash = ui.tab.hash;
        })
                
    });
    </script>
</h:header>
<h:headercontent />


<h1><%= user.getUsername() %>'s profile</h1>

<div id="tabs">
    <ul>
        <li><a href="#general">General</a></li>
        <li><a href="#venues">Venues</a></li>
        <li><a href="#reviews">Reviews</a></li>
        <li><a href="#comments">Comments</a></li>
    </ul>
    <div id="general">
        Your reputation level: <strong><%= user.getReputationLevel() %></strong>
    </div>
    <div id="venues">
        Your venues: <br/>
        <%
        ModelList<VenueModel> venues = VenueModel.getFromSolrCreatedBy(user, new Pager(request, "v", new Pager(new SortField(SortableFields.TIME_ADDED, Order.DESC))));
        %>
        <%
        if (venues != null) {
        %>
            <h:pager_header mlist="<%= venues %>" hashAnchor="venues"/>
                <hr/>
                <% for (VenueModel venue : venues.getList()) { %>
            	<a href="${pageContext.request.contextPath}/venue/view.jsp?venueID=<%= venue.getID() %>"><%= venue.getTitle() %></a><br/>
            	<% } %>
                <hr/>
            <h:pager_footer mlist="<%= venues %>"/>
        <% } else { %>
            No venues
        <% } %>
    </div>
    <div id="reviews">
        Your reviews:
        <%
        ModelList<ReviewModel> reviews = ReviewModel.getFromSolrCreatedBy(user, new Pager(request, "r", new Pager(new SortField(SortableFields.TIME_ADDED, Order.DESC))));
        %>
        <%
        if (reviews != null ) {
        %>
            <h:reviews reviews="<%= reviews %>" hashAnchor="reviews"/>
        <% } else { %>
            No reviews
        <% } %>
    </div>
    <div id="comments">
        Your comments:
        <%
        ModelList<CommentModel> comments = CommentModel.getFromSolrCreatedBy(user, new Pager(request, "c", new Pager(new SortField(SortableFields.TIME_ADDED, Order.DESC))));
        %>
        <%
        if (comments != null ) {
        %>
            <h:comments review="<%= null %>" comments="<%= comments %>" enableNewComments="false" hashAnchor="comments"/>
            <hr />
        <% } else { %>
            No comments
        <% } %>
    </div>
</div>
<h:footer />
