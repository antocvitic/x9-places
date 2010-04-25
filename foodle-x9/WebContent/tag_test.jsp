<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h"%>
<%@ taglib tagdir="/WEB-INF/tags/gmaps" prefix="gmaps"%>

<h:header title="Tag test header of gmaps view">
    <style>
		p {
			color: blue;
		}
	</style>
	<gmaps:include />
</h:header>
<h:headercontent />

<h:content>
    <p>Testing</p>
	<gmaps:address address="Götgatan, Stockholm" id="gmaps_view1"/>
	
	<gmaps:coord lng="150.644" lat="-34.397" id="gmaps_view2"/>
	
	<gmaps:center address="Stockholm" id="gmaps_view3"/>
	<gmaps:mark address="Osquars Backe, Stockholm" title="KTH" id="gmaps_view3">
		<b> This is KTH </b> <br/>
		We study boring stuff. <br/>
		<a href="http://kth.se">Visit KTH</a>
	</gmaps:mark>
	<gmaps:mark address="Folkungagatan 63, Stockholm" title="Folkets" id="gmaps_view3">
		<h1> NICE KEBAB </h1> <br/>
		Food food <br/>
		<a href="http://www.folketskebab.com/">Folketskebab</a>
	</gmaps:mark>
	
	<gmaps:center address="Ireland" id="gmaps_view4"/>
<!--	Test that map is centered correctly around marks-->
	<gmaps:mark address="Kiruna" title="Kiruna" id="gmaps_view4">
		foobar
	</gmaps:mark>
	<gmaps:mark address="Malmö" title="Malmö" id="gmaps_view4">
		foobar
	</gmaps:mark>
	
</h:content>

<h:footer />
