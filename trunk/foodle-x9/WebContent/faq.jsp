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
<h4>What is Foodle?</h4>
Foodle is an online service to serve as a city guide that helps people find 
cool places to eat, shop, drink, relax and play. The guide is based on submitted 
venues and reviews from an active community. The main capabilities are searching 
for venues, tags and reading reviews or comments with the goal to find useful information 
for any type of activity you would like to do.<br />
<br />
<h4>Is Foodle free?</h4>
Yes, it is free!
<br />
<br />
<h4>Why use Foodle?</h4>
It is an easy and fun way for locals and tourists of a city to share opinions 
on the places they know and visit. You can find out about the gems of your
city by your fellow neighbours, colleagues and friends. 
The reviews are written by real people, not magazines or
newspapers who might have hidden agendas or biased opinions.
<br />
<br />
<h4>Who uses Foodle?</h4>
There are two broad categories of users. First there are the people who want to 
share information about their favorite places or activities. Or who simply want 
to share their experiences and opinions about places they visited.
<br />
<br />
The second category are people who are not that active in sharing their own opinions 
but who want to find new and interesting places to visit or activities they could do. 
And they rely more on real peoples opinons rather than bying different types of guides 
which are not free of charge or could be biased.
<br />
<br />
<h4>What should I be reviewing on Foodle?</h4>
Anything or any place that you feel you want to share with other people.
<br />
<br />
<h4>Who can see me and my reviews?</h4>
Everyone who uses the website can see your reviews, they don´t however need to know 
who you are, unless you want to. Simply post reviews using an alias.
<br />
<br />
<h4>Can business owners message me after I review their business?</h4>
No, they can´t.
<br />
<br />
<h4>Is Foodle in my hometown?</h4>
If it is not it could be, just submit a review for any type of venue you wish.
<br />
<br />
<h4>Can business owners publicly comment on my reviews?</h4>
Business owners can create user profiles like anyone else, and in that respect they can comment on your review. But there is no special category for business owners.
<br />
<br />
<h4>Will Foodle remove bad reviews if a business pays for sponsorship?</h4>
No, that would go against the real purpose and spirit of Foodle.
<br />
<br />
<h4>Does Foodle allow sponsors to move bad reviews to the bottom of their page?</h4>
No, that would go against the real purpose and spirit of Foodle. 
<br />
<br />
<h4>How does the reputation system work?</h4>
*commenting on review - creator of review gets 10<br />
*ranking review up - creator of review gets 25 <br />
*ranking review down - creator looses 25 <br />
*adding venue - gets 15 <br />
*tagging venue - gets 1 - creator of venue gets 10 <br />
*writing review - gives 10m, venue creator gets 20 <br />
*rating a venue - gives +5 and creator gets +1 <br />
<br />

<h:footer />
