
function updateVenueRating(venueID, data) {
    $("#venue_average_rating_"+venueID).html(data.rating);
    $("#venue_number_of_ratings_"+venueID).html(data.ratings);
    $("#venue_user_rating_"+venueID).html(data.userRating);
    $("#venue_user_rating_outer_"+venueID).show();
    fixStars(venueID);
}

function fixStars(venueID) {
    avgRating = $("#venue_average_rating_"+venueID).html();
    for (i = 1; i <= 5; i++) {
        if (i <= avgRating)
            $("#venue_rating_star_"+venueID+"_"+i + " img.venue_star_actual").attr("src", "../images/stars/star-gold48.png");
        else
            $("#venue_rating_star_"+venueID+"_"+i + " img.venue_star_actual").attr("src", "../images/stars/star-white48.png");
    }
}

$(document).ready(function() {
    
    $(".venue_rating_editable").click(function() {
        var rating = this.title;
        var venueID = $(this).parent().parent().data("venueID");
        var postURL = $(this).parent().parent().data("postURL");
        $.post(postURL, { venueID: venueID, rating: rating }, function(data){
            if (data.status == "ok") {
                updateVenueRating(venueID, data);
            } else {
                alert(data.status);
            }
        }, "json");
    });

    
    $(".venue_rating_editable").hover(function () {
        var venueID = $(this).parent().parent().data("venueID");
        for (i = 1; i <= this.title; i++) {
            $("#venue_rating_star_"+venueID+"_"+i + " img.venue_star_actual").attr("src", "../images/stars/star-purple48.png");
        }
    }, function () {
        var venueID = $(this).parent().parent().data("venueID");
        fixStars(venueID);
    });
});