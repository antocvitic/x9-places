<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h"%>
<%@ taglib tagdir="/WEB-INF/tags/gmaps" prefix="gmaps"%>
<h:header title="Spot X9 - Advanced Search"></h:header>
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
<gmaps:include/>

<%
	String query = request.getParameter("search_term");
	if (query == null || query == "") {
%>

<FORM class="adv_search_form" METHOD="GET" ACTION="adv_search.jsp">
<h1>Advanced search</h1>
<div id="search">
         	<%
            String search_term = request.getParameter("search_term");
            search_term = search_term == null ? "" : search_term.trim();
            %>
     <img src="${pageContext.request.contextPath}/images/small-search.png" id="search_icon"/>
     <input type="text" id="searchfield" class="searchfield placeholder" name="search_term" 
            onFocus="removePlaceholder(this)" onBlur="addPlaceholder(this)" value="<%= search_term %>"/>
     <input type="submit" value="Find" id="search_button"/>
</div>




<table cellspacing="-1">
	<tr>
		<td>
            <input id="adv_opt_all" type="radio" name="adv_opt" value="all" checked="checked">
            <label for="adv_opt_all">All</label>
        </td>

		<td>
            <input id="adv_opt_venue" type="radio" name="adv_opt" value="venue">
            <label for="adv_opt_venue">Venue</label>
        </td>

		<td>
            <input id="adv_opt_address" type="radio" name="adv_opt" value="street">
            <label for="adv_opt_address">Address</label>
        </td>

		<td>
            <input id="adv_opt_review" type="radio" name="adv_opt" value="review">
            <label for="adv_opt_review">Review</label>
        </td>

		<td>
            <input id="adv_opt_comment" type="radio" name="adv_opt" value="comment">
            <label for="adv_opt_comment">Comment</label>
        </td>

		<td>
            <input id="adv_opt_tag" type="radio" name="adv_opt" value="tags">
            <label for="adv_opt_tag">Tags</label>
        </td>
		
		<td>
            <input id="rating_opt" type="checkbox" name="rating_opt" value="highRating">
            <label for="rating_opt">High Rating</label>
        </td>
		
		<td>
            <input id="geo_opt" type="checkbox" name="geo_opt" value="geo">
            <label for="geo_opt">Geographic view</label>
        </td>
	</tr>
</table>
</FORM>

<%
	} else {
	query = URLUtils.encode(query);
	query = query.replaceAll("%C3%83%C2%A5", "&aring;");
	query = query.replaceAll("%C3%83%C2%A4", "&auml;");
	query = query.replaceAll("%C3%83%C2%B6", "&ouml;");
	query = query.replaceAll("%C3%83%C2%85", "&Aring;");
	query = query.replaceAll("%C3%83%C2%84", "&Auml;");
	query = query.replaceAll("%C3%83%C2%96", "&Ouml;");
	query = query.replaceAll("\\+", " ");

	//search = request.getParameter("search");
	String choice = request.getParameter("adv_opt");
	TreeMap<String, Integer> tagmap = new TreeMap<String, Integer>();
	String highRating = request.getParameter("rating_opt");
	SolrDocumentList res = SearchController.query(query, choice, highRating);
    
	
    
    
	// Collect all tags from the search result
	if (res != null) {
		for (int i = 0; res.size() > i; i++) {
			try {
				//new stuff starts here
				SolrDocument doc = res.get(i);
				VenueModel venue = null;
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
ArrayList<Integer> tagcount = new ArrayList<Integer>(tagmap.values());
if(tagcount.size() > 0){	
		Collections.sort(tagcount);
		int most_freq_tag = tagcount.get(tagcount.size()-1);
		
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
<% if (choice.equals("tags")) { %>Most relevant venues tagged with <% out.println(query); } %>
<% if (choice.equals("review")) {%> Most relevant venues where review contains <% out.println(query); }%> 
</h3>
<%
	if (res == null || res.isEmpty()) {
%>
<div style="text-align:center">
<h5 >No matching results found or invalid input</h5>
</div>
<%
	} else {
			if(request.getParameter("geo_opt") != null){ %>
				<gmaps:map css="gmaps_div_big" id="gmaps_frame"/>
			<% }
		
			for (int i = 0; res.size() > i; i++) {
				try {
					SolrDocument doc = res.get(i);
					VenueModel venue = null;
					//added for specific search (review + comment)
					//this is a bit ugly
					if(choice.equalsIgnoreCase("review")){
						String reviewVenueID = (String) doc.get("reference"); //our venueID
						venue = VenueModel.getFromSolr(reviewVenueID);
					}
					else if(choice.equalsIgnoreCase("comment")) {
						String commentReviewID = (String) doc.get("reference"); //our reviewID
						String venueID = ReviewModel.getFromSolr(commentReviewID).getVenueID();
						venue = VenueModel.getFromSolr(venueID);
					}
					else {
						venue = VenueModel.venueFromSolrDocument(doc);						
					}
					if(request.getParameter("geo_opt") != null){ 
					    String gmaps_info = "<br/><h5>" + 
        				    venue.getTitle() + "</h5>Address: " + 
                            venue.getAddress() + "<br/><a href='" + 
                            request.getContextPath() + "/venue/view.jsp?venueID=" + 
                            venue.getID() + "'>Show venue</a><br/>Description: " + 
                            venue.getDescription();
                    %>
						<gmaps:mark_ id="gmaps_frame" address="<%= venue.getAddress()%>" title="<%= venue.getTitle() %>" info="<%= gmaps_info %>"/>
					<% } else { %>
						<div id="search-result">
						<h3><%=venue.getTitle()%></h3>
						<h5> <a href="${pageContext.request.contextPath}/venue/view.jsp?venueID=<%=venue.getID()%>">Show venue</a></h5>
						<h5>Address: <%=venue.getAddress()%></h5>
						</div>
						<br />
					<% }
					} catch (Exception e) {
								
					}
				}
			}
		}
%>
<br/>
<a id="new_button" href="${pageContext.request.contextPath}/venue/edit.jsp">Add a new venue</a>
</div>
<h:footer />
