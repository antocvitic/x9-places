<%@ taglib tagdir="/WEB-INF/tags" prefix="h"%>

<%@ tag import="java.util.*" %>
<%@ tag import="com.x9.foodle.util.*" %>
<%@ tag import="com.x9.foodle.review.*" %>
<%@ tag import="com.x9.foodle.comment.*" %>

<%@ attribute name="review" type="com.x9.foodle.review.ReviewModel" required="true"%>
<%@ attribute name="comments" type="java.util.List" required="true"%>
<%@ attribute name="enableNewComments" %>
<%
@SuppressWarnings("unchecked")
    List<CommentModel> comments_typed = (List<CommentModel>) comments;
%>
<%
if (comments_typed.isEmpty()) {
    %>
    No comments<br />
    <%
} else {
	%><%= comments_typed.size() %> comments<br /><%
    for (CommentModel comment : comments_typed) {
    %>
        
        <h:comment comment="<%= comment %>" />
        <br />
    <%
    } 
}

if ("true".equals(enableNewComments)) {
    %>
        <a id="new_comment_a_<%= review.getID() %>" href="javascript:newComment(<%= review.getID() %>)">Write a comment</a>
        <div id="new_comment_div_<%= review.getID() %>" class="new_comment_div_hidden">
            <form action="${pageContext.request.contextPath}/comment/edit" method="POST">
                <input type="hidden" name="reviewID" value="<%= review.getID() %>" />
                <input type="hidden" name="venueID" value="<%= review.getVenueID() %>" />
                <input type="hidden" name="redirect" value="<%= URLUtils.getCurrentURL(request) %>" />
                <textarea name="text" rows="3" cols="50"></textarea>
                <br/>
                <input type="submit" value="Post comment" />
            </form>
        </div>
    <%   
}
%>