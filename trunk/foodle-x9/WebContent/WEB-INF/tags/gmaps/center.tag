<%@ attribute name="address" required="true"%>
<%@ attribute name="id" required="true"%>

<div id="${id}" style="width: 500px; height: 500px">
	<script type="text/javascript">
		centerMap("${id}", "${address}");
	</script>
</div>
