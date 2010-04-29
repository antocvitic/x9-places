<%@ attribute name="venue" type="com.x9.foodle.venue.VenueModel"
    required="true"%>
<%@ tag import="com.x9.foodle.user.*"%>
<%
UserModel user = UserUtils.getCurrentUser(request, response);

if (user != null) {
%>
<script type="text/javascript">
function rateVenue() {
    alert("rateVenue");
    var rating = $("#venue_rater").attr("value");
    
    $.post("${pageContext.request.contextPath}/venue/rate", { venueID: "<%=venue.getID()%>", rating: rating }, function(data){
         alert("Data Loaded: " + data);
       });   
}
</script>
<select id="venue_rater">
    <option value="1">1</option>
    <option value="2">2</option>
    <option value="3">3</option>
    <option value="4">4</option>
    <option value="5">5</option>
</select>
<input type="button" value="Rate!" onclick="javascript:rateVenue()" />
<% } else { %>
Log in to rate
<% } %>