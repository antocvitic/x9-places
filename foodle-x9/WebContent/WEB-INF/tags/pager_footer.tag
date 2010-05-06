<%@ tag body-content="empty"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h"%>

<%@ tag import="java.util.*"%>
<%@ tag import="com.x9.foodle.datastore.*"%>
<%@ tag import="com.x9.foodle.util.*"%>

<%@ attribute name="mlist" type="com.x9.foodle.datastore.ModelList"
    required="true"%>
<%
ModelList<?> cmlist = (ModelList<?>)mlist;
Pager pager = mlist.getPager();
String queryString = pager.generateOtherQueryString(request) + "&";
%>

<%
/*
out.println(pager.toString());
out.println("<br/>");

out.println (mlist.getOffset() + "_" + mlist.getResultsReturned() + "_" + mlist.getResultsFound());
out.println("<br/>");

out.println (mlist.getCurrentPage() + "_" + mlist.getNumberOfPages());
out.println("<br/>");
*/
%>

<div class="pager pager_footer">
Pages: 
<%
Pager pagePager = new Pager(pager);
pagePager.setOffset(0);
for (int page = 1; page <= mlist.getNumberOfPages(); page++) {
    pagePager.setOffset((page-1)*pagePager.getMaxReturned());
    String link = request.getRequestURL() + "?" + queryString + pagePager.getAsParams();
%>
<%
if (page == mlist.getCurrentPage()) {
%>
<span class="pager_page_number pager_current_page_number">
    <%= page %>
</span>
<% } else { %>
<span class="pager_page_number pager_other_page_number">
    <a href="<%= link %>"><%= page %></a>
</span>
<% } %>
<% } %>
</div>