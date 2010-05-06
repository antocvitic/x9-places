<%@ tag body-content="empty" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h"%>

<%@ tag import="java.util.*" %>
<%@ tag import="com.x9.foodle.review.*" %>
<%@ tag import="com.x9.foodle.datastore.*" %>
<%@ attribute name="reviews" type="com.x9.foodle.datastore.ModelList" required="true"%>
<%
@SuppressWarnings("unchecked")
ModelList<ReviewModel> mlist = (ModelList<ReviewModel>)reviews;
%>
<%
if (reviews.isEmpty()) {
    %>
    No reviews
    <%
} else {
    %>
    <h:pager_header mlist="<%= reviews%>"/>
    <% 
    for (ReviewModel review : mlist.getList()) {
    %>
        <h:review review="<%= review %>" />
        <br />
    <% } %>
    <h:pager_footer mlist="<%= reviews%>"/>
<% } %>