<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" charset="utf-8"
    href="style/main.css" />
<style>
.my_header {
	text-align: center;
}

.editfield {
	width: 20em;
}
</style>
<title>Edit venue</title>
</head>
<body>

<div id="content">

<h3 class="my_header">Add or edit venue</h3>
<form action="edit" method="POST"><input name="redirect"
    type="hidden" value="/venue/edit?ok" />
<table>
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
        <td><label for="creator_id">Creator ID</label></td>
    </tr>
    <tr>
        <td><input class="editfield" name="creator_id"
            id="creator_id" type="text" /></td>
    </tr>
    <tr>
        <td><input type="submit" value="Submit" /></td>
    </tr>

</table>
</form>
</div>


</body>
</html>