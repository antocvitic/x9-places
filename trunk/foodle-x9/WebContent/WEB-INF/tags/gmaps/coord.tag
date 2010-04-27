<%@ attribute name="id" required="true"%>
<%@ attribute name="lat" required="true"%>
<%@ attribute name="lng" required="true"%>
<%@ attribute name="css" required="true"%>

<div id="${id}" class="${css}">
	<script type="text/javascript">
		var mapid = "${id}";
		coordMap("${id}", ${lat}, ${lng});
		<jsp:doBody />
	</script>
</div>