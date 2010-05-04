<%@ attribute name="venue" type="com.x9.foodle.venue.VenueModel"
    required="true"%>
<%@ tag import="com.x9.foodle.user.*"%>
<%@ tag import="com.x9.foodle.util.*"%>
<%
	UserModel user = UserUtils.getCurrentUser(request, response);

	String starNone = "../images/stars/star-white48.png";
	String starActive = "../images/stars/star-gold48.png";
	String starHover = "../images/stars/star-purple48.png";

	final int maxRating = 5;
    
	double avgRating = venue.getAverageRating();
    int ratings = venue.getNumberOfRatings();

    String imgClass = "";
	if (user != null) {
        imgClass = "venue_rating_editable";
	}
%>
<div id="venue_rating_<%= venue.getID() %>">

<div>
    <script type="text/javascript">
    $("#venue_rating_<%= venue.getID() %>").data("venueID", "<%= venue.getID() %>"); 
    $("#venue_rating_<%= venue.getID() %>").data("postURL", "${pageContext.request.contextPath}/venue/rate"); 
    $(document).ready(function() {
    	fixStars("<%= venue.getID() %>");
    });
    </script>
    <% for (int i = 1; i <= maxRating; i++) { %>
        <div id="venue_rating_star_<%= venue.getID() %>_<%=i%>" class="venue_star_div <%= imgClass %>"title="<%=i%>">
            <img class="venue_star_actual"/>
            <!--  <img class="venue_star_background" src="<%=starNone %>"/> -->
        </div>
    <% } %>
</div>
<div>
    <span id="venue_average_rating_<%= venue.getID() %>"><%=StringUtils.formatRating(avgRating)%></span> / <%=maxRating%>
    - <span id="venue_number_of_ratings_<%= venue.getID() %>"><%=ratings%></span> ratings
    <% if (user != null) { 
    	int userRating = user.getRatingForVenue(venue.getID());
        
        String display = "";
        if (userRating == UserModel.NO_RATING) {
            display = "display:none;";
        }
    %>
        <span id="venue_user_rating_outer_<%= venue.getID() %>" style="<%= display %>">
            (your rating is <span id="venue_user_rating_<%= venue.getID() %>"><%= userRating %></span>)
        </span>
    <%  } %>
</div>

</div>