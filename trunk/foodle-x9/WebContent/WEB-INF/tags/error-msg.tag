<%@ attribute name="parentRequest"
    type="javax.servlet.ServletRequest" required="true"%>
<%@tag import="com.x9.foodle.util.*"%>
<%
    if (parentRequest.getParameter("error") != null) {
        String error = parentRequest.getParameter("error");
        String message = parentRequest.getParameter("reason");
        if (message == null)
            message = "Unknown reason";
        else
            message = URLUtils.decodeLatin(message);
    %>
<div id="error_div" class="content_block">
<strong>Error:</strong><%=error%><br />
<strong>Reason:</strong> <%= message%></div>
<br />
<% } %>