<%@ attribute name="comment" type="com.x9.foodle.comment.CommentModel" required="true" %>
<%@ tag import="com.x9.foodle.comment.*" %>
<div id="comment_<%= comment.getID() %>" class="comment">
    <div class="comment_title_div">
        <p><b>Comment</b> by <%= comment.getCreator().getUsername() %> (<%= comment.getTimeAdded() %>)</p>
    </div>
    
    <div class="comment_text">
    <%= comment.getText() %>
    </div>
</div>