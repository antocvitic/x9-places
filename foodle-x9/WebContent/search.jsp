<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h"%>
<h:header title="Foodle X9 - The most awesome venue search"></h:header>
<h:headercontent />

<%@page import="com.x9.foodle.util.DateUtils"%>
<body>
<%@page import="com.x9.foodle.search.*"%>
<%@page import="com.x9.foodle.venue.*"%>
<%@page import="org.apache.solr.common.SolrDocumentList"%>
<%@page import="org.apache.solr.common.SolrDocument"%>
<%@page import="java.util.TreeMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Collections"%>

<%
	if (request.getParameter("search_term") == null) {
%>


<%
	} else {
%>
<%!String temp;
	SolrDocumentList res;
	VenueModel venue;
	String temp_two;
	
	TreeMap<String, Integer> tagmap;
	ArrayList<Integer> tagcount;
	Integer most_freq_tag;

%>
<%
	//TODO: FIX FOR Å, Ä, Ö
		temp = request.getParameter("search_term");
		res = SearchController.query(temp, "all");
		tagmap = new TreeMap<String, Integer>();
		
		// Collect all tags from the search result
		if (res != null) {
			for (int i = 0; res.size() > i; i++) {
				try {

					venue = VenueModel.venueFromSolrDocument(res.get(i));
					
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
<h3> Tag cloud </h3>
<%
		// Print tag cloud with tagsize weighted according to tagfrequency (beta)
		tagcount = new ArrayList<Integer>(tagmap.values());
		Collections.sort(tagcount);
		most_freq_tag = tagcount.get(tagcount.size()-1);
		
		for (String tag : tagmap.navigableKeySet()) { %>
			<a href="${pageContext.request.contextPath}/adv_search.jsp?search=<%=tag%>&adv_opt=tags" style="font-size: <%=6*tagmap.get(tag)/most_freq_tag+8%>pt"><%=tag%></a>&nbsp;
		<% 
		}
	}
%>
</div>
<%		
		if (res != null) {
			for (int i = 0; res.size() > i; i++) {
				try {
					SolrDocument doc = res.get(i);

					venue = VenueModel.venueFromSolrDocument(doc);
					temp_two = venue.getID();				
%>

<h3>Title: <%=venue.getTitle()%></h3>
<h4><a href="${pageContext.request.contextPath}/venue/view.jsp?venueID=<%=temp_two%>">Show venue</a></h4>
<h4>Address: <%=venue.getAddress()%></h4>
<BR>
<%
	} catch (Exception e) {
				}
			}
		}
%>
<H2>Du sökte efter <%=temp%></H2>
<%
	}
%>


</body>

<h:footer />
