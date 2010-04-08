<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>test.jsp</title>
</head>
<body>
<%@page import="com.x9.foodle.user.UserModel"%>
<%
	UserModel user = UserModel.getFromDbByID(2);
	if (user != null) {
		out.println("Got a user by id: " + user.getUsername());
	} else {
		out.println("Got null user");
	}
%>
<br/>
<%
	UserModel user2 = UserModel.getFromDbByUsername("cain");
	if (user2 != null) {
		out.println("Got a user by name: " + user2.getUsername());
	} else {
		out.println("Got null user");
	}
%>
</body>
</html>