<%@ tag body-content="empty" %>
<%@ tag import="com.x9.foodle.util.*" %>
<%@ tag import="com.x9.foodle.util.MessageDispatcher.*" %>
<div id="msg" class="msg">
<span class="msg_closer"><a href="javascript:closeMessageBox()">X</a></span>
<span id="msg_content">
</span>
</div>
<br />
<%
	Message msg = MessageDispatcher.popMsg(request);
	if (msg != null) {
%>
<script type="text/javascript">
$(document).ready(function() {
    showMessage(<%= msg.toJSON() %>, true);
});
</script>
<% } %>