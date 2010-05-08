<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h"%>
<h:header title="Foodle X9 - FAQ"></h:header>
<h:headercontent />

<%@page import="com.x9.foodle.util.DateUtils"%>
<%@page import="com.x9.foodle.user.UserModel"%>
<%@page import="com.x9.foodle.user.UserUtils"%>
<%@page import="com.x9.foodle.search.*" %>
<%@page import="com.x9.foodle.venue.*" %>
<%@page import="org.apache.solr.common.SolrDocumentList" %>
<%@page import="org.apache.solr.common.SolrDocument" %>


<h1>FAQ</h1><br />
<h4>More elaborate version coming soon to a site near you.</h4><br />
<h5><u>Question:</u> How does the reputation system work?</h5>

<font size="2">
<br />
<strong><u>Answer:</u></strong><br />
*commenting on review - creator of review gets 10<br />
*ranking review up - creator of review gets 25 <br />
*ranking review down - creator looses 25 <br />
*adding venue - gets 15 <br />
*tagging venue - gets 1 - creator of venue gets 10 <br />
*writing review - gives 10m, venue creator gets 20 <br />
*rating a venue - gives +5 and creator gets +1 <br />



</font>


<h:footer />
