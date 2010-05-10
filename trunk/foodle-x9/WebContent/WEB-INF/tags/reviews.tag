<%@ tag body-content="empty" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h"%>

<%@ attribute name="reviews" type="com.x9.foodle.datastore.ModelList" required="true"%>
<%@ attribute name="enableComments" %>
<%@ attribute name="enableNewComments" %>
<%@ attribute name="hashAnchor" %>

<%@ tag import="java.util.*" %>
<%@ tag import="com.x9.foodle.review.*" %>
<%@ tag import="com.x9.foodle.datastore.*" %>


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
    <h:pager_header mlist="<%= reviews%>" hashAnchor="<%= hashAnchor %>"/>
    <% 
    for (ReviewModel review : mlist.getList()) {
    %>
        <h:review review="<%= review %>" enableComments="<%= enableComments %>" enableNewComments="<%= enableNewComments %>"/>
        <br />
    <% } %>
    <h:pager_footer mlist="<%= reviews%>" hashAnchor="<%= hashAnchor %>"/>
<% } %>