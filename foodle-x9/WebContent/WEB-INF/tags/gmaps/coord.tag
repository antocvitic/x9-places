<%@ attribute name="id" required="true"%>
<%@ attribute name="lat" required="true"%>
<%@ attribute name="lng" required="true"%>

<div id="${id}" style="width: 500px; height: 500px">
	<script type="text/javascript">
		coordMap("${id}", ${lat}, ${lng});
	</script>
</div>
