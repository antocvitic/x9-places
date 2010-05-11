<%@ tag import="java.util.Enumeration" %>
<%@ tag import="com.x9.foodle.user.*" %>
<%
UserModel user = UserUtils.getCurrentUser(request, response);
%>

<div id="footer">
    <center>
	<font size="3">
	
	<table border="0" cellspacing="6" width="500">
	<tr>
    	<th>About us</th>
    	<th>Help</th>
    	<th>Join</th>
	</tr>
	<tr>
    	<td align="center"><a href="${pageContext.request.contextPath}/X9Team.jsp">The X9 Team</a></td>
    	<td align="center"><a href="${pageContext.request.contextPath}/faq.jsp">FAQ</a></td>
        <td align="center"><a href="http://x9board.forumotion.com/">X9 Forum</a></td>
	</tr>
	<tr>
    	<td align="center"><a href="${pageContext.request.contextPath}/thisProject.jsp">This Project</a></td>
    	<td align="center"></td>
    	<% if (user == null) { %>
        <td align="center"><a href="${pageContext.request.contextPath}/login.jsp">Register</a></td>
        <% } else { %>
        <td align="center"></td>
        <% } %>
	</tr>
	</table> 
	</font>
    </center>

		<!-- COMMENTING AWAY THIS STUFF, REMOVE COMMENT IF YOU WANT TO USE IT
<p>
Cookies:
<%
Cookie[] cookies = request.getCookies();
if (cookies == null) {
    out.println("none");
} else {
    for (Cookie c : cookies) {
        out.println(c.getName()+":\t"+
        c.getValue()+"|");
    }
}
%>
</p>
<p>
Session data:
<%
Enumeration<String> sns = session.getAttributeNames();

if (!sns.hasMoreElements()) {
    out.println("empty");   
} else {
    do {
        String name = sns.nextElement(); 
        out.println(name + ":\t" + session.getAttribute(name) + "|");
    } while (sns.hasMoreElements());
}
%>
</p>

							END OF COMMENT THINGIE-->  

  
</div> <!-- this closes footer -->
</div> <!-- this closes content_wrapper from  /WEB-INF/tags/headercontent.jsp -->
</div> <!-- this closes all from  /WEB-INF/tags/headercontent.jsp -->
</body>
</html>