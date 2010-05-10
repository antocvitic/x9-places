<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@ taglib tagdir="/WEB-INF/tags" prefix="h"%>
<%@ taglib tagdir="/WEB-INF/tags/gmaps" prefix="gmaps"%>

<%@ page import="java.util.*" %>
<%@ page import="com.x9.foodle.user.*" %>
<%@ page import="com.x9.foodle.venue.*" %>
<%@ page import="com.x9.foodle.review.*" %>
<%@ page import="com.x9.foodle.datastore.*" %>
<%@ page import="com.x9.foodle.util.*" %>
<%@ page import="com.x9.foodle.util.MessageDispatcher.*" %>

<%
    String redirect = request.getParameter("redirect");
    redirect = redirect == null ? "" : URLUtils.decode(redirect);

    TreeMap<String, Integer> tagmap;
    ArrayList<Integer> tagcount;
    Integer most_freq_tag;
    
VenueModel venue = null;
ModelList<ReviewModel> reviews = null;

UserModel user = UserUtils.getCurrentUser(request, response);
String venueID = request.getParameter("venueID");

if (venueID != null && !venueID.isEmpty()) {
	venue = VenueModel.getFromSolr(venueID);
	if (venue != null) {
		reviews = venue.getReviews(new Pager(request, "r" + venueID));
	} else {
		MessageDispatcher.pushMsg(request, new ErrorMessage("No venue with id: " + venueID));
    }
    
} else {
	MessageDispatcher.pushMsg(request, new ErrorMessage("No such venue"));
}
%>
<h:header title="View venue - Foodle X9">
    <gmaps:include />
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/venue.js"></script>
</h:header>
<h:headercontent />

<!-- Text content -->
<% if (venue != null) { %>
<div class="venue">
	<div id="venue_text">
		<div id="venue_title_div">
			<h1 id="venue_title"><%=venue.getTitle()%></h1>
			<div id="hide-long-title">a</div>
            <% if (user != null && user.getReputationLevel() >= UserUtils.EDIT_LEVEL) { %>
    			<a href="${pageContext.request.contextPath}/venue/edit.jsp?venueID=<%=venueID %>" id="venue_edit">Edit</a>
            <% } %>
		</div>
		<div id="venue_info_div">
			
			<h:venue_rater venue="<%= venue %>" />
			
			<p id="address"><%=venue.getAddress()%></p>
            <br/>
            <h:share />
		</div>
		
		<div id="venue_description_div">
			<p><%=venue.getDescription()%></p>
		</div>

		<div id=venue_tags>
			
			<!-- getTags() returns an ArrayList (1 May 2010) -->
			<%
			tagmap = new TreeMap<String, Integer>();
			
			// Collect all tags for the venue
					for(String tag : venue.getTags()){
						if(tagmap.containsKey(tag))
							tagmap.put(tag, tagmap.get(tag) + 1);
						else
							tagmap.put(tag, 1);
					}
		%>

			<div id="tagcloud" class="msg_msg" 
				style="background-color:transparent;text-align:left;width:75%;margin-left:0;border:0px;">
			<%
			// Print tag cloud with tagsize weighted according to tagfrequency (beta)
		if (tagmap != null && !tagmap.isEmpty()) {
			tagcount = new ArrayList<Integer>(tagmap.values());
			Collections.sort(tagcount);
			most_freq_tag = tagcount.get(tagcount.size()-1);
			
			for (String tag : tagmap.navigableKeySet()) { %>
				<a href="${pageContext.request.contextPath}/adv_search.jsp?search_term=<%=tag%>&adv_opt=tags" style="font-size: <%=6*tagmap.get(tag)/most_freq_tag+8%>pt"><%=tag%></a>&nbsp;
			<% 
			} 
		}
			%>
			
			</div> <!-- tagcloud -->
		 
		 
		</div><!-- venue_tags close -->
		
        <% if (user != null) { %>
        <br />
        <form id="tag_venue" action="${pageContext.request.contextPath}/venue/edit" method="POST">
		<input name="redirect" type="hidden" value="${pageContext.request.contextPath}/venue/view.jsp" />
		<input name="venueID" type="hidden" value="<%= venueID %>" />
		<input name="what" type="hidden" value="addtags" />
		<p style="text-align:left;">Tag this venue, separate by spaces</p>
		<table>
    	<tr>
        <td><input class="tagfield placeholder" id="tagad" name="your_tags" type="text"
            onFocus="removePlaceholder(this)" onBlur="addPlaceholder(this)"  />
            <input type="submit" value="Tag it" /></td>
    	</tr> 
    	</table>
    	</form>
    		<a href="${pageContext.request.contextPath}/review/edit.jsp?venueID=<%=venueID %>" id="venue_review">Write a review</a>
        <% } %>
		
	</div> <!-- end of venue_text -->
	
	<!-- map for the venue -->
	<div id="venue_map">
		<p><gmaps:address address="<%=venue.getAddress()%>" id="gmaps_view1" css="gmaps_div" /></p>
	</div>
	
	
	
</div> <!-- end of venue -->

<h:reviews reviews="<%=reviews %>" enableComments="true" enableNewComments="<%= user == null ? \"false\" : \"true\" %>"/>
<% } %>

<h:footer />