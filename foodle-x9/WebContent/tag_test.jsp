<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/jsptags" prefix="h"%>
<%@ taglib tagdir="/WEB-INF/jsptags/gmaps" prefix="gmaps"%>
<h:html>
<h:header title="This is the header">
    <style>
		p {
			color: blue;
		}
	</style>
	<gmaps:include />
</h:header>

<h:content>
    <p>Testing</p>
	<gmaps:address address="Svartbäcksvägen 26, Stockholm" id="gmaps_view1"/>
	<gmaps:address address="Osquars Backe, Stockholm" id="gmaps_view2"/>
	<gmaps:coord lng="150.644" lat="-34.397" id="gmaps_view3"/>
</h:content>
</h:html>