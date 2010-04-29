<%@ attribute name="review" type="com.x9.foodle.review.ReviewModel" required="true" %>
<%@ tag import="com.x9.foodle.review.*" %>
<div class="review">
<h2><%= review.getTitle() %></h2>

<div style="text-align: left;">
Written by: <%= review.getCreatorID() %><br/>
Written: <%= review.getTimeAdded() %><br/>
Ranking: <%= review.getRanking() %>
</div>
<div style="">
<%= review.getText() %>
</div>
<a href="${pageContext.request.contextPath}/review/edit.jsp?reviewID=<%=review.getID() %>"">edit this review</a>
</div>