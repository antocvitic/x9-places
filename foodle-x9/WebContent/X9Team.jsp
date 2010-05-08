<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h"%>
<h:header title="Foodle X9 - The X9 Team"></h:header>
<h:headercontent />

<%@page import="com.x9.foodle.util.DateUtils"%>
<%@page import="com.x9.foodle.user.UserModel"%>
<%@page import="com.x9.foodle.user.UserUtils"%>
<%@page import="com.x9.foodle.search.*" %>
<%@page import="com.x9.foodle.venue.*" %>
<%@page import="org.apache.solr.common.SolrDocumentList" %>
<%@page import="org.apache.solr.common.SolrDocument" %>


<h1>The X9 Team</h1><br />
<h5>The X9 Team is a group of nine people which was formed during the KTH course
<a href="http://www.csc.kth.se/utbildning/kth/kurser/DD1365/mvk09/">mvk09</a>.<br />
</h5>
<br />
<center>
<table border="0" cellspacing="8">
<tr>
<th>Member</th>
<th>Role</th>
</tr>
<tr>
<td align="center">Adam</td>
<td align="center">Lead Programmer, Server Administrator, GUI / Interaction / Documentation Group</td>
</tr>
<tr>
<td align="center">Anto</td>
<td align="center">Project Leader, Programming Group</td>
</tr>
<tr>
<td align="center">Calle</td>
<td align="center">Programming Group</td>
</tr>
<tr>
<td align="center">David</td>
<td align="center">Programming / GUI / Interaction / Documentation Group</td>
</tr>
<tr>
<td align="center">Kristoffer</td>
<td align="center">Customer Account Manager, Server Administrator</td>
</tr>
<tr>
<td align="center">Quentin</td>
<td align="center">Designer, Programming / GUI / Interaction Group</td>
</tr>
<tr>
<td align="center">Remi</td>
<td align="center">Programming / GUI / Interaction Group</td>
</tr>
<tr>
<td align="center">Sohof</td>
<td align="center">Report Writer, Project Secretary, Documentation Group</td>
</tr>
<tr>
<td align="center">William</td>
<td align="center">Tester, Programming / Documentation Group</td>
</tr>
</table> 
</center>
<br />
<h5>We are a friendly bunch of computer science students and this website is our project.</h5>


</div>
<h:footer />