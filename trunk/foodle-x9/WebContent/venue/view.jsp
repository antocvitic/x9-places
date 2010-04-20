
<jsp:include page="/includes/header.jsp" /> 

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@page import="com.x9.foodle.venue.VenueModel"%>
<%
	String venueID = request.getParameter("id");
	VenueModel venue = VenueModel.getFromSolr(venueID);
%>

<%@ include file="/includes/header_includes.jspf"%>





<div id="content">

<div id="header">[....Logo... ] <input type="text"
    value="Insert search terms here" /> <input type="submit"
    value="Search" /></div>

<div id="middle">
<div id="left_sidebar">
<ul>
    <li>asdfasdf</li>
    <li>asdfasdf</li>
    <li>asdfasdf</li>
    <li>asdfasdf</li>
    <li>asdfasdf</li>
    <li>asdfasdf</li>
    <li>asdfasdf</li>
    <li>asdfasdf</li>
    <li>asdfasdf</li>
    <li>asdfasdf</li>
    <li>asdfasdf</li>
    <li>asdfasdf</li>
    <li>asdfasdf</li>
    <li>asdfasdf</li>
    <li>asdfasdf</li>
</ul>
hejsan
</div>
<div id="right_sidebar">Karta:
<div id="gmaps"></div>
</div>


<div id="center">

<h1>Venue</h1>

Venue: <%=venue%></div>
</div>
<div id="footer">
Detta är vår footer
</div>

</div>

</body>
</html>