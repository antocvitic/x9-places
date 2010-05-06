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



<%
	if (request.getParameter("search_term") == null) {
%>


<%
	} else {
%>
<%!String temp;
	SolrDocumentList res;
	VenueModel venue;
	String temp_two;%>
<%
	//TODO: FIX FOR Å, Ä, Ö
		temp = request.getParameter("search_term");
		res = SearchController.query(temp, "all");
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
