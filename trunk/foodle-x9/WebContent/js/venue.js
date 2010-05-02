function newComment(reviewID) {
	var newDiv = $("#new_comment_div_" + reviewID)
	newDiv.toggleClass("new_comment_div_hidden");
	if (newDiv.hasClass("new_comment_div_hidden")) {
		$("#new_comment_a_" + reviewID).html("Write a comment");
	} else {
		$("#new_comment_a_" + reviewID).html("Discard comment");
	}
}