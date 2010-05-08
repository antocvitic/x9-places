<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h"%>
<h:header title="Foodle X9 - The most awesome venue search"></h:header>
<h:headercontent />
<%@page import="com.x9.foodle.util.URLUtils"%>
<%@page import="com.x9.foodle.util.DateUtils"%>
<%@page import="com.x9.foodle.search.*"%>
<%@page import="com.x9.foodle.venue.*"%>
<%@page import="com.x9.foodle.review.*"%>
<%@page import="org.apache.solr.common.SolrDocumentList"%>
<%@page import="org.apache.solr.common.SolrDocument"%>
<%@page import="java.util.TreeMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Collections"%>

<%
	String temp = request.getParameter("search_term");
	if (temp == null || temp == "") {
%>

<FORM METHOD="GET" ACTION="adv_search.jsp">
<table cellspacing="5" width="400" cellpadding="0" border="0">
	<tr>
		<td><B>Search:</B></td>
		<td><input type="text" name="search_term" size=15></td>
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
		
		<td><input type="checkbox" name="rating_opt" value="highRating">High Rating</td>

		<td><input type="submit" value="Search"></td>
	</tr>
</table>
</FORM>

<%
	} else {
%>

<% 
//String search;
String choice;
SolrDocumentList res;
String temp_bleh;
String highRating;
VenueModel venue;

TreeMap<String, Integer> tagmap;
ArrayList<Integer> tagcount;
Integer most_freq_tag;
%>
<%
	temp = URLUtils.encode(temp);
	temp = temp.replaceAll("%C3%83%C2%A5", "&aring;");
	temp = temp.replaceAll("%C3%83%C2%A4", "&auml;");
	temp = temp.replaceAll("%C3%83%C2%B6", "&ouml;");
	temp = temp.replaceAll("%C3%83%C2%85", "&Aring;");
	temp = temp.replaceAll("%C3%83%C2%84", "&Auml;");
	temp = temp.replaceAll("%C3%83%C2%96", "&Ouml;");
	temp = temp.replaceAll("\\+", " ");

	//search = request.getParameter("search");
	choice = request.getParameter("adv_opt");
	tagmap = new TreeMap<String, Integer>();
	highRating = request.getParameter("rating_opt");
	res = SearchController.query(temp, choice, highRating);
	
	// Collect all tags from the search result
	if (res != null) {
		for (int i = 0; res.size() > i; i++) {
			try {
				//new stuff starts here
				SolrDocument doc = res.get(i);
				
				if(choice.equals("review")){
					venue = VenueModel.getFromSolr((String) doc.get("reference"));
				}
				else if(choice.equals("comment")) {
					venue = VenueModel.getFromSolr(ReviewModel.getFromSolr((String) doc.get("reference")).getVenueID());
				} //ends here
				else {
					venue = VenueModel.venueFromSolrDocument(doc);
				}
				
				for(String tag : venue.getTags()){
					if(tagmap.containsKey(tag))
						tagmap.put(tag, tagmap.get(tag) + 1);
					else
						tagmap.put(tag, 1);
				}
			} catch (Exception e) {
				
			}
		}
	 %>

<div id="tagcloud" class="msg_msg">
<h3>Tag cloud</h3>
<%
		// Print tag cloud with tagsize weighted according to tagfrequency (beta)
		tagcount = new ArrayList<Integer>(tagmap.values());
if(tagcount.size() > 0){	
		Collections.sort(tagcount);
		most_freq_tag = tagcount.get(tagcount.size()-1);
		
		for (String tag : tagmap.navigableKeySet()) { %>
			<a href="${pageContext.request.contextPath}/adv_search.jsp?search_term=<%=tag%>&adv_opt=tags" style="font-size: <%=6*tagmap.get(tag)/most_freq_tag+8%>pt"><%=tag%></a>&nbsp;
		<% 
		}
		%></div>
<%
	}
}
%>

<div id="resultarea">
<h3 style="text-align:center"> 
<% if (choice.equals("tags")) { %>Most relevant venues tagged with <% out.println(temp); } %>
<% if (choice.equals("review")) {%> Most relevant venues where review contains <% out.println(temp); }%> 
</h3>
<%
	if (res == null || res.isEmpty()) {
%>
<h5 style="text-align:center">No matching results found or invalid input</h5>
<%
	} else {
			for (int i = 0; res.size() > i; i++) {
				try {
					SolrDocument doc = res.get(i);
					
					//added for specific search (review + comment)
					//this is a bit ugly
					if(choice.equalsIgnoreCase("review")){
						temp = (String) doc.get("reference"); //our venueID
						venue = VenueModel.getFromSolr(temp);
					}
					else if(choice.equalsIgnoreCase("comment")) {
						temp_bleh = (String) doc.get("reference"); //our reviewID
						temp = ReviewModel.getFromSolr(temp_bleh).getVenueID();
						venue = VenueModel.getFromSolr(temp);
					}
					else {
						venue = VenueModel.venueFromSolrDocument(doc);
						temp = venue.getID();						
					}
%>
<div id="search-result">
<h3><%=venue.getTitle()%></h3>
<h5> <a href="${pageContext.request.contextPath}/venue/view.jsp?venueID=<%=temp%>">Show venue</a></h5>
<h5>Address: <%=venue.getAddress()%></h5>
</div>
<br />
<%
	} catch (Exception e) {
				}
			}
		}
	}
%>
</div>
<h:footer />
