<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h"%>
<h:header title="Foodle X9 - The most awesome venue search"></h:header>
<h:headercontent />

<%@page import="com.x9.foodle.util.DateUtils"%>
<%@page import="com.x9.foodle.search.*"%>
<%@page import="com.x9.foodle.venue.*"%>
<%@page import="org.apache.solr.common.SolrDocumentList"%>
<%@page import="org.apache.solr.common.SolrDocument"%>



<%
	if (request.getParameter("search") == null) {
%>

<FORM METHOD="GET" ACTION="adv_search.jsp">
<table cellspacing="5" width="400" cellpadding="0" border="0">
	<tr>
		<td><B>Sök:</B></td>
		<td><input type="text" name="search" size=15></td>
	</tr>
	<tr>
	</tr>
	<tr>
		<td><input type="radio" name="adv_opt" value="all" CHECKED>All</td>

		<td><input type="radio" name="adv_opt" value="venue">Venue</td>

		<td><input type="radio" name="adv_opt" value="street">Street</td>

		<td><input type="radio" name="adv_opt" value="review">Review</td>

		<td><input type="radio" name="adv_opt" value="comment">Comment</td>

		<td><input type="radio" name="adv_opt" value="tags">Tags</td>

		<td><input type="submit" value="Search"></td>
	</tr>
</table>
</FORM>

<%
	} else {
%>

<%!String search;
	String choice;
	SolrDocumentList res;
	VenueModel venue;%>
<%
	//TODO: FIX FOR Å, Ä, Ö

		search = request.getParameter("search");
		choice = request.getParameter("adv_opt");
		res = SearchController.query(search, choice);
		%>
		<H2>You searched for <%=search%></H2>
		<%
		if (res == null) {
%>
<h5>No matching results found or invalid input</h5>
<%
	} else {
			for (int i = 0; res.size() > i; i++) {
				try {
					SolrDocument doc = res.get(i);

					venue = VenueModel.venueFromSolrDocument(doc);
%>

<h3>Title: <%=venue.getTitle()%></h3>
<h4>ID: <%=venue.getID()%></h4>
<h4>Address: <%=venue.getAddress()%></h4>
<BR>
<%
	} catch (Exception e) {
				}
			}
		}
	}
%>


<h:footer />
