<%@ tag body-content="empty" %>
<%@ attribute name="review" type="com.x9.foodle.review.ReviewModel" required="true" %>

<%@ tag import="com.x9.foodle.review.*" %>
<%@ tag import="com.x9.foodle.user.*" %>
<% UserModel user = UserUtils.getCurrentUser(request, response); %>

<div id="review_ranker_<%= review.getID() %>" class="review_ranker">
    <span class="review_ranker_value"><%= review.getRanking() %></span>
    <% 
    String actionClass = "";
    String upClass = "review_rank_up_enabled";
    String downClass = "review_rank_down_enabled";
    if (user != null) { 
        actionClass = "review_ranker_action";
        int userRanking = user.getRankingForReview(review.getID());
        if (userRanking < 0) {
            upClass = "review_rank_up_enabled";
            downClass = "review_rank_down_disabled";
        } else if (userRanking > 0) {
        	upClass = "review_rank_up_disabled";
        	downClass = "review_rank_down_enabled";
        }
    }
    %>
        <a href="#" class="<%= actionClass %> <%= upClass %> review_ranker_button review_ranker_up"></a>
        <a href="#" class="<%= actionClass %> <%= downClass %> review_ranker_button review_ranker_down"></a>
        <script type="text/javascript">
            $("#review_ranker_<%= review.getID() %>").data("reviewID", "<%= review.getID() %>");
            $("#review_ranker_<%= review.getID() %>").data("postURL", "${pageContext.request.contextPath}/review/rank");
            $("#review_ranker_<%= review.getID() %> .review_ranker_up").data("rankingValue", "1");
            $("#review_ranker_<%= review.getID() %> .review_ranker_down").data("rankingValue", "-1");
        </script>
</div>