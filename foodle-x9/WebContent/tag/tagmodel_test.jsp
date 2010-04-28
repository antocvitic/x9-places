<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@page import="com.x9.foodle.venue.*"%>
<%@page import="com.x9.foodle.util.*"%>
<%

String tags = "";
String venueID = request.getParameter("venueID");
if (venueID != null) {
    VenueModel venue = VenueModel.getFromSolr(venueID);
    if (venue == null) {
        throw new RuntimeException("no venue with id: " + venueID);   
    }
    tags = StringUtils.join(venue.getTags(), " ");
} else {
    venueID = "";   
}

%>

<h:header title="Foodle X9 - Your profile"></h:header>
<h:headercontent />

<div id="contentarea">

<h3 class="my_header">Add venue with tags</h3>
<form action="${pageContext.request.contextPath}/tag/edit" method="POST">
<input name="redirect" type="hidden" value="${pageContext.request.contextPath}/tag/tagmodel_test.jsp" />
<table class="content_block">
    <tr>
        <td><label for="id">ID</label></td>
    </tr>
    <tr>
        <td><input class="editfield" name="id" id="id" type="text"
            value="<%= venueID %>" /></td>
    </tr>
    <tr>
        <td><label for="id">Tags, separated by spaces</label></td>
    </tr>
    <tr>
        <td><textarea class="editfield" name="tags" id="tags"><%= tags %></textarea></td>
    </tr>
    <tr>
        <td><input type="submit" value="Submit" /></td>
    </tr>

</table>
</form>
</div>


<h:footer />