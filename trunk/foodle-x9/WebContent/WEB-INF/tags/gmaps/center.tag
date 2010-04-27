<%@ attribute name="address" required="true"%>
<%@ attribute name="id" required="true"%>
<%@ attribute name="css" required="true"%>

<div id="${id}" class="${css}">
	<script type="text/javascript">
		var mapid = "${id}";
		centerMap("${id}", "${address}");
		<jsp:doBody />
	</script>
</div>