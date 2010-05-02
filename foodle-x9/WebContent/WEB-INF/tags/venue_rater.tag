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

	if (user != null) {

		int userRating = user.getRatingForVenue(venue.getID());
		double avgRating = venue.getAverageRating();
		int ratings = venue.getNumberOfRatings();
%>
<script type="text/javascript">
function updateVenueRating(newRating, newNumberOfRatings) {
    $("#venue_average_rating").html(newRating);
    $("#venue_number_of_ratings").html(newNumberOfRatings);
    fixStars();
}

function fixStars() {
	avgRating = $("#venue_average_rating").html();
    for (i = 1; i <= <%=maxRating%>; i++) {
        if (i <= avgRating)
            $("#venue_rating_selector_"+i).attr("src", "<%=starActive%>");
        else
            $("#venue_rating_selector_"+i).attr("src", "<%=starNone%>");
    }
}

$(document).ready(function() {
    
    $(".venue_rating_selector").click(function() {
        var rating = this.title
        $.post("${pageContext.request.contextPath}/venue/rate", { venueID: "<%=venue.getID()%>", rating: rating }, function(data){
            if (data.status == "ok") {
                updateVenueRating(data.rating, data.ratings);
            }
        }, "json");
    });

    
    $(".venue_rating_selector").hover(function () {
        //alert("in");
        this.src = "<%=starHover%>";
        for (i = 1; i <= this.title; i++) {
        	$("#venue_rating_selector_"+i).attr("src", "<%=starHover%>");
        }
    }, fixStars);

    fixStars();
});

</script>
<div id="venue_rater">
<div><input type="hidden" id="venue_user_rating"
    value="<%=userRating%>" /> <%
 	for (int i = 1; i <= maxRating; i++) { %>
    <img id="venue_rating_selector_<%=i%>" class="venue_rating_selector" src="<%=starNone%>" width="24px" border="0" title="<%=i%>" /> 
    <% } %>
</div>
<div><span id="venue_average_rating"><%=StringUtils.formatRating(avgRating)%></span>
/ <%=maxRating%> - <span id="venue_number_of_ratings"><%=ratings%></span>
ratings</div>
</div>

<%
	} else {
%>
Log in to rate
<%
	}
%>