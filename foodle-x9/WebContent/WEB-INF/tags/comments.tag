<%@ tag body-content="empty" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h"%>

<%@ tag import="java.util.*" %>
<%@ tag import="com.x9.foodle.util.*" %>
<%@ tag import="com.x9.foodle.review.*" %>
<%@ tag import="com.x9.foodle.comment.*" %>
<%@ tag import="com.x9.foodle.datastore.*" %>

<%@ attribute name="review" type="com.x9.foodle.review.ReviewModel" required="true"%>
<%@ attribute name="comments" type="com.x9.foodle.datastore.ModelList" required="true"%>
<%@ attribute name="enableNewComments" %>
<%@ attribute name="hashAnchor" %>
<%
@SuppressWarnings("unchecked")
    ModelList<CommentModel> mlist = (ModelList<CommentModel>) comments;
%>
<%
if (mlist.isEmpty()) {
    %>
    No comments<br />
    <%
} else {
	%>
    <h:pager_header mlist="<%= comments %>" hashAnchor="<%= hashAnchor %>"/>
    <%
    for (CommentModel comment : mlist.getList()) {
    %>
        
        <h:comment comment="<%= comment %>" />
        <br />
    <% } %>
    <h:pager_footer mlist="<%= comments %>" hashAnchor="<%= hashAnchor %>"/>
    
    <%
}

if ("true".equals(enableNewComments)) {
    %>
        <a id="new_comment_a_<%= review.getID() %>" href="javascript:newComment(<%= review.getID() %>)">Write a comment</a>
        <div id="new_comment_div_<%= review.getID() %>" class="new_comment_div_hidden">
            <form action="${pageContext.request.contextPath}/comment/edit" method="POST">
                <input type="hidden" name="reviewID" value="<%= review.getID() %>" />
                <input type="hidden" name="venueID" value="<%= review.getVenueID() %>" />
                <input type="hidden" name="redirect" value="<%= URLUtils.getCurrentURL(request) %>" />
                <textarea name="text" rows="3" cols="50" 
                    onkeypress="return imposeMaxLength(this, <%= CommentModel.Validator.COMMENT_TEXT_MAX_LENGTH %>);"></textarea>
                <br/>
                <input type="submit" value="Post comment" />
            </form>
        </div>
    <%   
}
%>