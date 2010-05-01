<%@tag import="java.util.Enumeration"%>


<div id="footer">
    <p>Copyleft 2010. X9 Team.</p>
    <p>Some links: <a href="${pageContext.request.contextPath}/venue/view.jsp?venueID=">venue/view.jsp</a> |
    <a href="${pageContext.request.contextPath}/venue/edit.jsp?venueID=">venue/edit.jsp</a> | 
    <a href="${pageContext.request.contextPath}/test.jsp">test.jsp</a> | 
    <a href="${pageContext.request.contextPath}/tag_test.jsp">tag_test.jsp</a> |
    <a href="${pageContext.request.contextPath}/10_venues.jsp">10_venues.jsp</a> |
    <a href="${pageContext.request.contextPath}/profile.jsp">profile.jsp</a>
    </p>
    <p>
    Debug:
    <a href="${pageContext.request.contextPath}/hasher">hash stuff with jBCrypt</a> |
    <a href="${pageContext.request.contextPath}/dump-session">dump current session</a> |
    <a href="${pageContext.request.contextPath}/dump-model">dump model data</a>
    </p>

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
    
</div> <!-- this closes content from  /WEB-INF/tags/headercontent.jsp -->
</div> <!-- this closes content_wrapper from  /WEB-INF/tags/headercontent.jsp -->
</div> <!-- this closes all from  /WEB-INF/tags/headercontent.jsp -->
</body>
</html>