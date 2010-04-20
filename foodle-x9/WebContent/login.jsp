<jsp:include page="/includes/header.jsp" /> 

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
 	out.println("test" + QuickURLEncoder.decode(QuickURLEncoder.encode("едц")));
 	out.println("a valuable message on coolnes in registering with a div cool not errordiv" + QuickURLEncoder.decode("ab%3Fba"));
 	%>
 	<br />
 	<%=request.getCharacterEncoding()%>
 </div>

<div id="login_div" class="pad">
	<form action="${pageContext.request.contextPath}/login" method="POST">
		<h3 class="login_box_text" style="float:left;">
			Keep me logged in. Forgot password?
		</h3>
		<br /><br />
		<label for="login_username">Username</label>
		<input class="loginfield" id="login_username" name="username" type="text" value="abel" /><br />
		<label for="login_password">Password</label>
		<input class="loginfield" id="login_password" name="password" type="password" />
		<input type="submit" value="Log in" />
	</form><br />
	<h3 class="login_box_text" style="float:left;"> 
		<input type="button" value="Connect using Facebook" disabled="disabled" />
	</h3>
</div>

<div id="vertical_separator" class="nodivborder"></div>

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

<jsp:include page="includes/footer.jsp" /> 