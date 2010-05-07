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
<%@page import="java.util.regex.Pattern;"%>
<%
	String temp = request.getParameter("search_term");
	if (temp == null || temp == "") {
%>

<%
	} else {
%>
<%
SolrDocumentList res;
VenueModel venue;
String temp_two;

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

		res = SearchController.query(temp, "all");
		tagmap = new TreeMap<String, Integer>();
		
		// Collect all tags from the search result
		if (res != null) {
			for (int i = 0; res.size() > i; i++) {
				try {
					//Determine type of the search result.
					SolrDocument doc = res.get(i);
					
					
					if(((String)doc.get("type")).equals("reviewmodel")){
						venue = VenueModel.getFromSolr((String) doc.get("reference"));
					}
					else if(((String)doc.get("type")).equals("commentmodel")) {
						venue = VenueModel.getFromSolr(ReviewModel.getFromSolr((String) doc.get("reference")).getVenueID());
					} 
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
		}
	}
%>
</div>
<div id="resultarea">
<h3 style="text-align:center"> Search results for <%=temp%> </h3>
<%		
		if (res != null) {
			for (int i = 0; res.size() > i; i++) {
				try {
					SolrDocument doc = res.get(i);

					venue = VenueModel.venueFromSolrDocument(doc);
					temp_two = venue.getID();				
%>

<h3><%=venue.getTitle()%></h3>
<h5><a href="${pageContext.request.contextPath}/venue/view.jsp?venueID=<%=temp_two%>">Show venue</a></h5>

<h5>Address: <%=venue.getAddress()%></h5>
<br />
<%
	} catch (Exception e) {
				}
			}
		}
%>
</div>
<%
	}
%>
<h:footer />
