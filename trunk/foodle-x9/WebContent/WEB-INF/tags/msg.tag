
<%@tag import="com.x9.foodle.util.*"%>
<%
	String msg = MessageDispatcher.popMsg(request);
	if (msg != null) {
%>
<div id="error_div" class="content_block"><strong>Message:</strong>
<%=msg%></div>
<br />
<%
	}
%>