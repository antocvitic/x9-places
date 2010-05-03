
function updateReviewRanking(reviewID, data) {
	$("#review_ranker_"+reviewID+" .review_ranker_value").html(data.ranking);
	upDisabled = "";
	downDisabled = "";
	if (data.userRanking > 0) {
		var up = $("#review_ranker_"+reviewID+" .review_ranker_up");
		up.removeClass("review_rank_up_enabled")
		up.addClass("review_rank_up_disabled");
		var down = $("#review_ranker_"+reviewID+" .review_ranker_down");
		down.removeClass("review_rank_down_disabled")
		down.addClass("review_rank_down_enabled");
	} else if (data.userRanking < 0) {
		var up = $("#review_ranker_"+reviewID+" .review_ranker_up");
		up.removeClass("review_rank_up_disabled")
		up.addClass("review_rank_up_enabled");
		var down = $("#review_ranker_"+reviewID+" .review_ranker_down");
		down.removeClass("review_rank_down_enabled")
		down.addClass("review_rank_down_disabled");
	}
	
}

$(document).ready(function() {
    
    $(".review_ranker_action").click(function() {
        var ranking = $(this).data("rankingValue");
        var reviewID = $(this).parent().data("reviewID");
        var postURL = $(this).parent().data("postURL");
        $.post(postURL, { reviewID: reviewID, ranking: ranking }, function(data){
            if (data.status == "ok") {
                updateReviewRanking(reviewID, data);
            } else {
                alert(data.status);
            }
        }, "json");
        return false;
    });
});