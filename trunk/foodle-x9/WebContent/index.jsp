<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h"%>
<h:header title="Spot X9 - The most awesome venue search"></h:header>
<h:headercontent />

<%@page import="com.x9.foodle.util.DateUtils"%>
<%@page import="com.x9.foodle.user.UserModel"%>
<%@page import="com.x9.foodle.user.UserUtils"%>
<%@page import="com.x9.foodle.search.*" %>
<%@page import="com.x9.foodle.venue.*" %>
<%@page import="org.apache.solr.common.SolrDocumentList" %>
<%@page import="org.apache.solr.common.SolrDocument" %>
<%@page import="java.util.*"%>

<%
	SolrDocumentList res = null;

	res = SearchController.query("*", "tags", null);
	TreeMap<String, Integer> tagmap = new TreeMap<String, Integer>();
	List<Map.Entry<String, Integer>> tag_freq_list = null;
	
	// Collect all tags from the search result
	if (res != null) {
		for (int i = 0; res.size() > i; i++) {
			try {

				VenueModel venue;
				//Determine type of the search result.
				SolrDocument doc = res.get(i);
				venue = VenueModel.venueFromSolrDocument(doc);
				
				for(String tag : venue.getTags()){
					if(tagmap.containsKey(tag))
						tagmap.put(tag, tagmap.get(tag) + 1);
					else
						tagmap.put(tag, 1);
				}
				
				
				tag_freq_list = new LinkedList<Map.Entry<String, Integer>>(tagmap.entrySet());
			    Collections.sort(tag_freq_list, new Comparator<Map.Entry<String, Integer>>() {
			          public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
			               return (o1.getValue()).compareTo(o2.getValue());
			          }
			     });


			} catch (Exception e) {
				
			}
		}
	}
%>

<% // START OF TAG CLOUD BLOCK ---------------------
if(res != null) {
%>
	<h:tagcloud tagmap="<%= tagmap %>" tag_freq_list="<%= tag_freq_list %>"/>
<%
} 
%>

<br/><h1>Local venue search and review service</h1><br />
<h4>Find any kind of venues in your proximity</h4><br />
<h5>Try it, enter something like <i>coffe in stockholm</i> in the search box.<br />
Or try the <a href="adv_search.jsp">Advanced search</a></h5>
<div id="resultarea">
<p>
You can rate venues and tag them with whatever you want,<br />
You can like- or dislike- and comment on reviews.<br /><br />
A registered user can 
<%
UserModel user = UserUtils.getCurrentUser(request, response);
if (user != null) { %>
<a href="${pageContext.request.contextPath}/venue/edit.jsp">add venues</a><% } else { %> add venues<% }%>, reviews and comments.<br />
As a registered user you can be the one to describe your venues, gain points<br />
by writing reviews, rating venues and other activities. <br />Your likes and dislikes will have
more importance for the overall ranking of reviews and venues.<br />
</p>
</div>
<h:footer />
