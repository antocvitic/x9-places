<%@ attribute name="review" type="com.x9.foodle.review.ReviewModel" required="true" %>
<%@ tag import="com.x9.foodle.review.*" %>
<div class="review">
	<div id="review_title_div">
		<p><b><%= review.getTitle() %></b> by <%= review.getCreatorID() %> (<%= review.getTimeAdded() %>)</p>
		<a href="${pageContext.request.contextPath}/review/edit.jsp?reviewID=<%= review.getID() %>"
		id="review_edit"">Edit</a>
	</div>
	
	<div style="text-align: left;">
	Ranking: <%= review.getRanking() %>
	</div>
	
	<div id="review_text">
	<%= review.getText() %>
	</div>
</div>