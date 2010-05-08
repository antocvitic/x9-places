<%@ tag import="java.util.Enumeration" %>


<div id="footer">
	<font size="3">
	<center>
	<table border="0" cellspacing="6" width="700">
	<tr>
	<th>About us</th>
	<th>Help</th>
	<th>Join</th>
	<th>Temp stuff</th>
	</tr>
	<tr>
	<td align="center"><a href="${pageContext.request.contextPath}/X9Team.jsp">The X9 Team</a></td>
	<td align="center"><a href="${pageContext.request.contextPath}/faq.jsp">FAQ</a></td>
	<td align="center"><a href="${pageContext.request.contextPath}/login.jsp">Register</a></td>
	<td align="center"><a href="${pageContext.request.contextPath}/hasher">jBCrypt</a></td>
	</tr>
	<tr>
	<td align="center"><a href="${pageContext.request.contextPath}/thisProject.jsp">This Project</a></td>
	<td align="center"></td>
	<td align="center"><a href="http://x9board.forumotion.com/">X9 Forum</a></td>
	<td align="center"><a href="${pageContext.request.contextPath}/dump-session">Dump Session</a></td>
	</tr>
	<tr>
	<td align="center"></td>
	<td align="center"></td>
	<td align="center"></td>
	<td align="center"><a href="${pageContext.request.contextPath}/dump-model">Dump Model</a></td>
	</tr>
	<tr>
	<td align="center"></td>
	<td align="center"></td>
	<td align="center"></td>
	<td align="center"></td>
	</tr>
	</table> 
	</center>
	</font>
<p>
		<!-- COMMENTING AWAY THIS STUFF, REMOVE COMMENT IF YOU WANT TO USE IT

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

  
</div> <!-- this closes content from  /WEB-INF/tags/headercontent.jsp -->
</div> <!-- this closes content_wrapper from  /WEB-INF/tags/headercontent.jsp -->
</div> <!-- this closes all from  /WEB-INF/tags/headercontent.jsp -->
</body>
</html>