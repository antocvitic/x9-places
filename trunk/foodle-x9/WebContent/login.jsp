<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@ taglib tagdir="/WEB-INF/tags" prefix="h"%>

<%@ page import="com.x9.foodle.util.*" %>
<%@ page import="com.x9.foodle.user.*" %>

<%
    String redirect = request.getParameter("redirect");
    redirect = redirect == null ? "" : URLUtils.decode(redirect);
 %>


<h:header title="Login or register - Foodle X9">

<script type="text/javascript">


</script>


</h:header>
<h:headercontent />



<h:msg />

<div id="register_div">
<form id="register" action="${pageContext.request.contextPath}/register" method="POST">
<input type="hidden" name="redirect" value="<%= redirect %>" />
<h3 class="login_type_header">Register</h3>
<table>
    <tr>
        <td><label for="username">Username</label></td>
    </tr>
    <tr>
        <td><input class="loginfield placeholder" id="username" name="username" type="text"
            maxlength="<%= UserModel.Validator.USER_USERNAME_MAX_LENGTH %>"
            onFocus="removePlaceholder(this)" onBlur="addPlaceholder(this)"  /></td>
    </tr>
    <tr>
        <td><label for="email">Email</label></td>
    </tr>
    <tr>
        <td><input class="loginfield placeholder" id="email" name="email" type="text"
            onFocus="removePlaceholder(this)" onBlur="addPlaceholder(this)"  /></td>
    </tr>
    <tr>
        <td><label for="name">Name</label></td>
    </tr>
    <tr>
        <td><input class="loginfield placeholder" id="name" name="name" type="text"
            onFocus="removePlaceholder(this)" onBlur="addPlaceholder(this)"  /></td>
    </tr>
    <tr>
        <td><label for="location">Location</label></td>
    </tr>
    <tr>
        <td><input class="loginfield placeholder" id="location" name="location" type="text"
            onFocus="removePlaceholder(this)" onBlur="addPlaceholder(this)"  /></td>
    </tr>
    <tr>
        <td><label for="password">Password</label></td>
    </tr>
    <tr>
        <td><input class="loginfield placeholder password_placeholder" id="password" name="password" type="password"
            maxlength="<%= UserModel.Validator.USER_PASSWORD_MAX_LENGTH %>"
            onFocus="removePlaceholder(this)" onBlur="addPlaceholder(this)"  /></td>
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

<div id="login_div">
<form id="login" action="${pageContext.request.contextPath}/login" method="POST">
<input type="hidden" name="redirect" value="<%= redirect %>" />
<h3 class="login_type_header">Login</h3>
<table>
    <tr>
        <td><label for="username">Username</label></td>
    </tr>
    <tr>
        <td><input class="loginfield placeholder" id="username" name="username" type="text"
            onFocus="removePlaceholder(this)" onBlur="addPlaceholder(this)"  /></td>
    </tr>
    <tr>
        <td><label for="password">Password</label></td>
    </tr>
    <tr>
        <td><input class="loginfield placeholder password_placeholder" id="password" name="password" type="password"
        onFocus="removePlaceholder(this)" onBlur="addPlaceholder(this)"  /></td>
    </tr>
    <tr>
        <td><input type="submit" value="Login" /></td>
    </tr>
</table>
</form>
</div>

<h:footer />