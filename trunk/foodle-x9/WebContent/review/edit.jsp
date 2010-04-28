<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%

String venueID = request.getParameter("venueID");

if (venueID == null) {
    throw new RuntimeException("this page requires a venueID");
}

%>

<h:header title="Foodle X9 - Your profile"></h:header>
<h:headercontent />

<h3 class="my_header">Add or edit review</h3>
<form action="${pageContext.request.contextPath}/review/edit" method="POST">
<input name="redirect" type="hidden" value="${pageContext.request.contextPath}/venue/view.jsp" />
<input name="venueID" type="hidden" value="<%= venueID %>" />
<table class="content_block">
    <tr>
        <td>VenueID: <em><%= venueID %></em></td>
    </tr>
    <tr>
        <td><label for="title">Title</label></td>
    </tr>
    <tr>
        <td><input class="editfield" name="title" id="title"
            type="text" /></td>
    </tr>
    <tr>
        <td><label for="text">Text</label></td>
    </tr>
    <tr>
        <td><textarea class="editfield" name="text" id="text"></textarea></td>
    </tr>
    <tr>
        <td><input type="submit" value="Submit" /></td>
    </tr>

</table>
</form>

<h:footer />