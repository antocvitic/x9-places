<%@ taglib tagdir="/WEB-INF/tags" prefix="h"%>

<%@ attribute name="review" type="com.x9.foodle.review.ReviewModel" required="true" %>
<%@ tag import="com.x9.foodle.review.*" %>
<%@ tag import="com.x9.foodle.user.*" %>
<% UserModel user = UserUtils.getCurrentUser(request, response); %>
<div id="review_<%= review.getID() %>" class="review">
	<div class="review_title_div">
		<p><b><%= review.getTitle() %></b> by <%= review.getCreator().getUsername() %> (<%= review.getTimeAdded() %>)</p>
	</div>
	
	<div style="text-align: left;">
	Ranking: <h:review_ranker review="<%= review %>" />
	</div>
	
	<div class="review_text">
	<%= review.getText() %>
	</div>
    
    <h:comments review="<%= review %>" comments="<%= review.getComments(5) %>" enableNewComments="<%= user == null ? \"false\" : \"true\" %>" />
</div>