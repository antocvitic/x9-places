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
			               return (o1.getValue())
			              .compareTo(o2.getValue());
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
	<div id="tagcloud" class="msg_msg">
	<h3>Tag cloud - most common tags</h3>
	<%
	// Print tag cloud with tagsize weighted according to tagfrequency (beta)
	ArrayList<Integer> tagcount = new ArrayList<Integer>(tagmap.values());
	if(tagcount.size() > 0){	
			Collections.sort(tagcount);
			int most_freq_tag = tagcount.get(tagcount.size()-1);
			
			// Skip to 10 most common tags
			Iterator<Map.Entry<String, Integer>> it = tag_freq_list.iterator();
			for(int tsize = tag_freq_list.size(); tsize > 10; tsize--){
				it.next();
			}
			
			while (it.hasNext() ) { 
				Map.Entry<String, Integer> entry = it.next(); %>
				<a href="${pageContext.request.contextPath}/adv_search.jsp?search_term=<%=entry.getKey()%>&adv_opt=tags" style="font-size: <%=6*tagmap.get(entry.getKey())/most_freq_tag+8%>pt"><%=entry.getKey()%></a>&nbsp;
			<% 
			}
	%></div>
	<%
	}
} // END OF TAG CLOUD BLOCK ---------------------
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
