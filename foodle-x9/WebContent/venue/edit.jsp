<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>

<%@ taglib tagdir="/WEB-INF/tags" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@ page import="com.x9.foodle.venue.*" %>
<%@ page import="com.x9.foodle.util.*" %>
<%@ page import="com.x9.foodle.util.MessageDispatcher.*" %>
<%@ page import="java.util.*" %>
<% 
String venueID = request.getParameter("venueID");

String title = "";
String address = "";
String description = "";

VenueModel venue = null;

if (venueID != null && !venueID.isEmpty()) {
    venue = VenueModel.getFromSolr(venueID);
    if (venue == null) {
        MessageDispatcher.sendMsgRedirect(request, response, "/", new ErrorMessage("No venue with id: " + venueID));
    	return;
    }
    title = venue.getTitle();
    address = venue.getAddress();
    description = venue.getDescription();
} else {
    venueID = "";   
}
%>

<h:header title="Venue edit - Foodle X9"></h:header>
<h:headercontent />

<% if (venue != null) { %>
<h3 class="my_header">Edit venue</h3>
<a href="${pageContext.request.contextPath}/venue/view.jsp?venueID=<%=venueID %>">view this venue</a>
<% } else { %>
<h3 class="my_header">Add new venue</h3>
<% } %>
<center> <!-- sorry for the center tag --> 
<form action="${pageContext.request.contextPath}/venue/edit" method="POST">
<input name="redirect" type="hidden" value="${pageContext.request.contextPath}/venue/view.jsp" />
<input name="venueID" type="hidden" value="<%= venueID %>" />
<table>
    <tr>
        <td><label for="title">Title</label></td>
    </tr>
    <tr>
        <td><input class="editfield" name="title" id="title"
            type="text" value="<%= title %>" maxlength="<%= VenueModel.Validator.VENUE_TITLE_MAX_LENGTH %>"/></td>
    </tr>
    <tr>
        <td><label for="address">Address</label></td>
    </tr>
    <tr>
        <td><input class="editfield" name="address" id="address"
            type="text" value="<%= address %>" maxlength="<%= VenueModel.Validator.VENUE_ADDRESS_MAX_LENGTH %>"/></td>
    </tr>
    <tr>
        <td><label for="description">Description</label></td>
    </tr>
    <tr>
        <td><textarea class="editfield" name="description" id="description" 
            onkeypress="return imposeMaxLength(this, <%= VenueModel.Validator.VENUE_DESCRIPTION_MAX_LENGTH %>);"><%= description %></textarea></td>
    </tr>
    <tr>
        <td><label for="id">Tags, separated by spaces</label></td>
    </tr>
    <tr>
        <td><textarea class="editfield" name="your_tags" id="tags"></textarea></td>
    </tr>
    <tr>
        <td><input type="submit" value="Submit" /></td>
    </tr>
</table>
</form>
</center>



<h:footer />