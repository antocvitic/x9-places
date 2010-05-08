<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h"%>
<h:header title="Foodle X9 - The most awesome venue search"></h:header>
<h:headercontent />

<%@page import="com.x9.foodle.util.DateUtils"%>
<%@page import="com.x9.foodle.user.UserModel"%>
<%@page import="com.x9.foodle.user.UserUtils"%>
<%@page import="com.x9.foodle.search.*" %>
<%@page import="com.x9.foodle.venue.*" %>
<%@page import="org.apache.solr.common.SolrDocumentList" %>
<%@page import="org.apache.solr.common.SolrDocument" %>


<h1>This Project</h1><br />
<h4>This project is part of a bachelor's degree in computer science in the KTH course
<a href="http://www.csc.kth.se/utbildning/kth/kurser/DD1365/mvk09/">mvk09</a>.<br />
<br /><br /></h4>
<font size="3">
The project is actually called "Local search and review" and is built for the company
<a href="http://www.findwise.se/ Findwise">Findwise</a>. <br />
The website is built with Java EE, Apache Tomcat, Apache Solr and MySQL.
</font>
<br />
</div>
<h:footer />