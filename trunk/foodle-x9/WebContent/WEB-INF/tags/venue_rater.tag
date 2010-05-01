<%@ attribute name="venue" type="com.x9.foodle.venue.VenueModel"
    required="true"%>
<%@ tag import="com.x9.foodle.user.*"%>
<%
UserModel user = UserUtils.getCurrentUser(request, response);

if (user != null) {
    
    int userRating = user.getRatingForVenue(venue.getID());
    
%>
<script type="text/javascript">
function rateVenue() {
    var rating = $("#venue_rater").attr("value");
    $.post("${pageContext.request.contextPath}/venue/rate", { venueID: "<%=venue.getID()%>", rating: rating }, function(data){
        if (data.status == "ok") {
        	updateVenueRating(data.rating, data.ratings);
        }
    }, "json");   
}
</script>
<select id="venue_rater">
    <% for (int i = 1; i <= 5; i++) { %>
        <option value="<%= i %>" <%= i == userRating ? "selected=\"selected\"" : "" %>><%= i %></option>
    <% } %>
</select>
<input type="button" value="Rate!" onclick="javascript:rateVenue()" />
<% } else { %>
Log in to rate
<% } %>