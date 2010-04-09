<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="Expires" content="Tue, 01 Jan 1980 1:00:00 GMT" />
<title>Login Page - Foodle-X9</title>
</head>
<body>

<div id="content">

<div id="login_div" style="float: left;">
<form action="login" method="POST">
<table>
    <tr>
        <td>Username</td>
    </tr>
    <tr>
        <td><input id="username" name="username" type="text" value="cain"/></td>
    </tr>
    <tr>
        <td>Password</td>
    </tr>
    <tr>
        <td><input id="password" name="password" type="password" /></td>
    </tr>
    <tr>
        <td><input type="submit" value="Log in" /></td>
    </tr>
</table>
</form>

</div>

<div id="register_div">Here goes the register stuff</div>

</div>

</body>
</html>