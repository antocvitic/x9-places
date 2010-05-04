<%@ attribute name="id" required="true"%>
<%@ attribute name="css" required="true"%>

<div id="${id}" class="${css}">
	<script type="text/javascript">
		var mapid = "${id}";
		createMap("${id}");
		<jsp:doBody />
	</script>
</div>