<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="com.x9.foodle.util.QuickURLEncoder"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" charset="utf-8"
    href="${pageContext.request.contextPath}/style/main.css" />
<style>
.loginfield {
	width: 20em;
}

.login_type_header {
	text-align: center;
}

#login_div {
	float: left;
	height: 100%;
	margin: 20px;
}

#vertical_separator {
	float: left;
	border-left: 1px solid gray;
	height: 400px;
	margin: 50px 20px 100px 0px;
}

#register_div {
	height: 100%;
	margin: 20px;
}
</style>

<title>Login Page - Foodle-X9</title>
</head>
<body>

<div id="content">

<div id="error_div" class="content_block">
<%
	if (request.getParameter("error") != null) {
		String error = request.getParameter("error");
		String message = request.getParameter("reason");
		if (message == null)
			message = "Unknown reason";
		else
			message = QuickURLEncoder.decodeLatin(message);
%> <strong>Error:</strong> <%=error%><br />
<strong>Reason:</strong> <%=message%> <%
 	}

 	out.println("hejsan"
 			+ QuickURLEncoder.decode(QuickURLEncoder.encode("åäö")));
 	out.println("nsajeh" + QuickURLEncoder.decode("ab%3Fba"));
 %> <br /><%=request.getCharacterEncoding()%></div>

<div id="login_div">
<form action="${pageContext.request.contextPath}/login" method="POST">
<h3 class="login_type_header">Login</h3>
<table>
    <tr>
        <td><label for="login_username">Username</label></td>
    </tr>
    <tr>
        <td><input class="loginfield" id="login_username"
            name="username" type="text" value="abel" /></td>
    </tr>
    <tr>
        <td><label for="login_password">Password</label></td>
    </tr>
    <tr>
        <td><input class="loginfield" id="login_password"
            name="password" type="password" /></td>
    </tr>
    <tr>
        <td><input type="submit" value="Log in" /></td>
    </tr>
</table>
</form>

<table>
    <tr>
        <td><input type="button" value="Connect using Facebook"
            disabled="disabled" /></td>
    </tr>
</table>
</div>

<div id="vertical_separator"></div>

<div id="register_div">
<form action="${pageContext.request.contextPath}/register" method="POST">
<h3 class="login_type_header">Register</h3>
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
<br />
</div>
</div>

</body>
</html>