<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h"%>
<h:header title="Foodle X9 - The most awesome venue search"></h:header>
<h:headercontent />

<%@page import="com.x9.foodle.util.DateUtils"%>
<body>
<%@page import="com.x9.foodle.search.*" %>
<%@page import="com.x9.foodle.venue.*" %>
<%@page import="org.apache.solr.common.SolrDocumentList" %>
<%@page import="org.apache.solr.common.SolrDocument" %>



	<% if(request.getParameter("searchfield") == null) { %>


	<% 
	}
	else { %>
	<%!	String temp;
		SolrDocumentList res;
		VenueModel venue; %>
	<%
		//SearchController.skapaTestfall();
		//SearchController.skapaTestfall2();
		//SearchController.createTest2();
		temp = request.getParameter("searchfield");
		res = SearchController.query(temp);
		if( res != null )
		{
			for(int i=0; res.size() > i; i++) {
				try {
					SolrDocument doc = res.get(i);
					
					venue = VenueModel.venueFromSolrDocument(doc);
				
			%>
			
			<h3>Title: <%= venue.getTitle()%></h3>
			<h4>ID: <%= venue.getID() %> </h4>
			<h4>Address: <%= venue.getAddress() %></h4>
			<BR>
			<%
				}
				catch(Exception e) {
				}
			}
		} %>
		<H2>Du sökte efter <%= temp %>  </H2> <%
	}
	%>
	
	
</body>

<h:footer />
