<%@ attribute name="address" required="true"%>
<%@ attribute name="title" required="true"%>
<%@ attribute name="id" required="true"%>

<script type="text/javascript">
	var info = (<r><![CDATA[<jsp:doBody />]]></r>).toString();
	addInfoMarker("${id}", "${address}", "${title}", info);
</script>