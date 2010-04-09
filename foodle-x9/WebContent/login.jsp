<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Login Page - Foodle-X9</title>

<style>
.loginfield {
	width: 20em;
}
</style>
</head>
<body>

<div id="content">

<div id="login_div" style="float: left;">
<form action="login" method="POST">Login<br />
<table>
    <tr>
        <td><label for="login_username">Username</label></td>
    </tr>
    <tr>
        <td><input class="loginfield" id="login_username" name="username"
            type="text" value="abel" /></td>
    </tr>
    <tr>
        <td><label for="login_password">Password</label></td>
    </tr>
    <tr>
        <td><input class="loginfield" id="login_password" name="password"
            type="password" /></td>
    </tr>
    <tr>
        <td><input type="submit" value="Log in" /></td>
    </tr>
</table>
</form>

</div>

<div id="register_div">
<form action="register" method="POST">Register<br />
<table>
    <tr>
        <td><label for="username">Username</label></td>
    </tr>
    <tr>
        <td><input class="loginfield" id="username" name="username"
            type="text" /></td>
    </tr>
    <tr>
        <td><label for="email">Email</label></td>
    </tr>
    <tr>
        <td><input class="loginfield" id="email" name="email"
            type="text" /></td>
    </tr>
    <tr>
        <td><label for="name">Name</label></td>
    </tr>
    <tr>
        <td><input class="loginfield" id="name" name="name"
            type="text" /></td>
    </tr>
    <tr>
        <td><label for="password">Password</label></td>
    </tr>
    <tr>
        <td><input class="loginfield" id="password" name="password"
            type="password" /></td>
    </tr>
    <tr>
        <td><label for="password2">Retype password</label></td>
    </tr>
    <tr>
        <td><input class="loginfield" id="password2"
            name="password2" type="password" /></td>
    </tr>

    <tr>
        <td><input type="submit" value="Register" /></td>
    </tr>
</table>
</form>
</div>

</div>

</body>
</html>