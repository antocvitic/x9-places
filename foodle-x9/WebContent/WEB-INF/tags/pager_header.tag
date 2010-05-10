<%@ tag body-content="empty"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h"%>

<%@ tag import="java.util.*"%>
<%@ tag import="com.x9.foodle.datastore.*"%>
<%@ tag import="com.x9.foodle.util.*"%>

<%@ attribute name="mlist" type="com.x9.foodle.datastore.ModelList" required="true"%>
<%@ attribute name="hashAnchor" %>
<%
ModelList<?> cmlist = (ModelList<?>)mlist;
Pager pager = mlist.getPager();
String queryString = pager.generateOtherQueryString(request) + "&";
List<SortableFields> applicableFields = cmlist.getApplicableSortableFields();
%>
<div class="pager pager_header">
<%
for (SortableFields sf : applicableFields) {
    String sel = "";
    String link = request.getRequestURL() + "?" + queryString;
    if (sf == pager.getFirstSortField().field) {
        sel = " " + pager.getFirstSortField().order.display;
        link += new Pager(pager).setSortField(new SortField(sf, pager.getFirstSortField().order.getOpposite())).getAsParams();
    } else {
        link += new Pager(pager).setSortField(new SortField(sf)).getAsParams();
    }
    
    if (hashAnchor != null && !hashAnchor.isEmpty()) {
        link += "#" + hashAnchor;
    }
%>
<a href="<%= link %>"><%= sf.display %><%= sel %></a> | 
<% } %>

Show
<select class="pager_maxreturned_selector">
<%
for (int i : Pager.MAX_RETURNED_LIST) {
    String selected = "";
    if (i == pager.getMaxReturned()) {
        selected = "selected=\"selected\"";
    }
    String value = request.getRequestURL() + "?" + queryString + new Pager(pager).setOffset(0).setMaxReturned(i).getAsParams();
%>
    <option value="<%= value %>" <%= selected %>><%= i %></option>
<% } %>
</select>
items per page
</div>
<br/>