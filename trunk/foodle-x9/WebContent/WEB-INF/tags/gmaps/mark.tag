<%@ attribute name="address" required="true"%>
<%@ attribute name="title" required="true"%>

	var info = (<r><![CDATA[<jsp:doBody />]]></r>).toString();
	addInfoMarker(mapid, "${address}", "${title}", info);