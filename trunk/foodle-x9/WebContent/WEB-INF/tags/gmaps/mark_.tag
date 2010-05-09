<%@ attribute name="address" required="true"%>
<%@ attribute name="title" required="true"%>
<%@ attribute name="id" required="true"%>
<%@ attribute name="info" required="true"%>
<script type="text/javascript">
	addInfoMarker("${id}", "${address}", "${title}", "${info}");
</script>