<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib tagdir="/WEB-INF/tags/gmaps" prefix="gmaps"%>

<% VenueModel venue= null; %>
<h:header>
<jsp:attribute name="title">
	<%@page import="com.x9.foodle.venue.VenueModel"%>
	<%
	//VenueModel venue = null;
	if (request.getParameter("id") != null) {
		venue = VenueModel.getFromSolr(request.getParameter("id"));
		if (venue != null)
			out.print(venue.getTitle()+" -Foodle by X9");
	} else {
		out.print("Foodle by X9");
	}
	//request.getParameter("id");
	//VenueModel venue = VenueModel.getFromSolr(venueID);
	
	%>
</jsp:attribute>
</h:header>
<h:headercontent />


<div id="contentarea">

<input type="text" value="Insert search terms here" />
<input type="submit" value="Search" />

<div id="main">
	<div id="left">
	<ul>
    	<li><% out.print(venue.getDescription());%></li>
    	<li><% out.print(venue.getAddress()); %></li>
    	<li>asdfasdf</li>
    	<li>asdfasdf</li>
    	<li>asdfasdf</li>
    	<li>asdfasdf</li>
	</ul>
	</div>
	
	<div id="right">Rightsidebar
		<div id="gmaps">A map:
		<gmaps:address id="gmaps_view1">
			<jsp:attribute name="address">
				<% out.print(venue.getAddress()); %>
			</jsp:attribute>
		</gmaps:address>
		<!-- <gmaps:coord lng="150.644" lat="-34.397" id="gmaps_view3"/> -->
		</div>
	</div>

	<div id="center"><h1>Venue</h1>Venue: </div>

</div>

<h:footer />