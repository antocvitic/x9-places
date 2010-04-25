<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h"%>
<h:header title="Foodle X9 - The most awesome venue search"></h:header>
<h:headercontent />

<%@page import="com.x9.foodle.util.QuickURLEncoder"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<div id="contentarea">

<div id="error_div" class="content_block">
	<%
	if (request.getParameter("error") != null) {
		String error = request.getParameter("error");
		String message = request.getParameter("reason");
		if (message == null)
			message = "Unknown reason";
		else
			message = QuickURLEncoder.decodeLatin(message);
	%>
	<strong>Error:</strong> <%=error%><br />
	<strong>Reason:</strong> <%=message%>
	<%
 	}
 	out.println("test" + QuickURLEncoder.decode(QuickURLEncoder.encode("åäö")));
 	out.println("a valuable message on coolnes in registering with a div cool not errordiv" + QuickURLEncoder.decode("ab%3Fba"));
 	%>
 	<br />
 	<%=request.getCharacterEncoding()%>
    
    
    <%=
        this.getServletContext().getInitParameter("foodlex9password")
     %>
 </div>

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
</div>

</div> <!--  content area div end -->
<h:footer />