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
ModelList<VenueModel> venues = VenueModel.getFromSolrCreatedBy(user, new Pager(request, "v", new Pager(new SortField(SortableFields.TIME_ADDED, Order.DESC))));
ModelList<ReviewModel> reviews = ReviewModel.getFromSolrCreatedBy(user, new Pager(request, "r", new Pager(new SortField(SortableFields.TIME_ADDED, Order.DESC))));
ModelList<CommentModel> comments = CommentModel.getFromSolrCreatedBy(user, new Pager(request, "c", new Pager(new SortField(SortableFields.TIME_ADDED, Order.DESC))));
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
<br/>
<div id="tabs">
    <ul>
        <li><a href="#general">General</a></li>
        <li><a href="#venues">Venues</a></li>
        <li><a href="#reviews">Reviews</a></li>
        <li><a href="#comments">Comments</a></li>
    </ul>
    <div id="general">
        <center>
        <table style="text-align: left;">
            <tr>
                <td style="width: 180px"; >Reputation level</td>
                <td><strong><%= user.getReputationLevel() %></strong></td>
            </tr>
            <tr>
                <td>Venues written</td>
                <td><%= venues == null ? 0 : venues.getResultsFound() %></td>
            </tr>
            <tr>
                <td>Reviews written</td>
                <td><%= reviews == null ? 0 : reviews.getResultsFound() %></td>
            </tr>
            <tr>
                <td>Comments written</td>
                <td><%= comments == null ? 0 : comments.getResultsFound() %></td>
            </tr>
        </table>
        </center>
    </div>
    <div id="venues">
        Your venues:
        
        <%
        if (venues != null) {
        %>
             <br/>
             <h:pager_header mlist="<%= venues %>" hashAnchor="venues"/>
                <hr/>
                <center>
                <table>
                <% for (VenueModel venue : venues.getList()) { %>
                    <tr>
                        <td>
                            <a href="${pageContext.request.contextPath}/venue/view.jsp?venueID=<%= venue.getID() %>">
                                <%= venue.getTitle() %>
                                at <%= venue.getAddress() %>
                            </a>
                        </td>
                        <td>
                            <%= venue.getAverageRating() %> / 5, <%= venue.getNumberOfRatings() %> ratings
                        </td>
                    </tr>
            	<% } %>
                </table>
                </center>
                <hr/>
            <h:pager_footer mlist="<%= venues %>" hashAnchor="venues"/>
        <% } else { %>
            No venues
        <% } %>
    </div>
    <div id="reviews">
        Your reviews:
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
        if (comments != null ) {
        %>
            <h:comments review="<%= null %>" comments="<%= comments %>" enableNewComments="false" hashAnchor="comments"/>
        <% } else { %>
            No comments
        <% } %>
    </div>
</div>
<h:footer />
