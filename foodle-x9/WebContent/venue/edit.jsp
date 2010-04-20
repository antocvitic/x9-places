<jsp:include page="/includes/header.jsp" />

<div id="contentarea">

<h3 class="my_header">Add or edit venue</h3>
<form action="${pageContext.request.contextPath}/venue/edit"
    method="POST"><input name="redirect" type="hidden"
    value="${pageContext.request.contextPath}/venue/edit.jsp?ok" />
<table class="content_block">
    <tr>
        <td><label for="id">ID</label></td>
    </tr>
    <tr>
        <td><input class="editfield" name="id" id="id" type="text" /></td>
    </tr>
    <tr>
        <td><label for="title">Title</label></td>
    </tr>
    <tr>
        <td><input class="editfield" name="title" id="title"
            type="text" /></td>
    </tr>
    <tr>
        <td><label for="address">Address</label></td>
    </tr>
    <tr>
        <td><input class="editfield" name="address" id="address"
            type="text" /></td>
    </tr>
    <tr>
        <td><label for="description">Description</label></td>
    </tr>
    <tr>
        <td><textarea class="editfield" name="description"
            id="description"></textarea></td>
    </tr>
    <tr>
        <td><input type="submit" value="Submit" /></td>
    </tr>

</table>
</form>
</div>


<jsp:include page="/includes/footer.jsp" /> 