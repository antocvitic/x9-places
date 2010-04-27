This readme explains the usage of Google Maps tags.

A map can be created with 3 different tags:

NOTE#1: See examples in the file /WebContent/tag_test.jsp

NOTE#2: The argument "css" works the same as html attribute "class", use the defined style "gmaps_div"
from the general.css file.

<gmaps:coord lat="<float>" lng="<float> id="<unique id>" css="<classname>"></gmaps:coord>
This tag creates a map and places a marker on the target coordinate. 

<gmaps:address address="<address>" id="<unique id>" css="<classname>"></gmaps:address>
This tag creates a map and places a marker on the target address.

<gmaps:center address="<address>" id="<unique id>" css="<classname>"></gmaps:center>
This tag creates a map and centers the map on the target address (Note that no marker is shown).

<gmaps:mark address="<address>" title="<title>">
	<HTML CODE>
</gmaps:mark>
This tag can be placed inside the body of any of the map-creating tags. A marker will be
placed on the target address and the marker will be clickable. When clicking a these markers
a infowindow will open and render the specified <HTML CODE>. When a marker from this tag is placed
on the map, the map will automatically adjust its viewport to include all available markers.