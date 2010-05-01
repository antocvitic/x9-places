<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@ taglib tagdir="/WEB-INF/tags" prefix="h"%>

<%@page import="com.x9.foodle.util.*"%>

<%
    String redirect = request.getParameter("redirect");
    redirect = redirect == null ? "" : URLUtils.decode(redirect);
 %>


<h:header title="Foodle X9 - The most awesome venue search"></h:header>
<h:headercontent />



<h:error-msg parentRequest="<%= request %>" />

<div id="register_div">
<form action="${pageContext.request.contextPath}/register" method="POST">
<h3 class="login_type_header">Register</h3>
<table>
    <tr>
        <td><label for="username">Username</label></td>
    </tr>
    <tr>
        <td><input class="loginfield placeholder" id="username" name="username" type="text"
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
        <td><label for="password">Password</label></td>
    </tr>
    <tr>
        <td><input class="loginfield placeholder" id="password" name="password" type="password"
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
<form action="${pageContext.request.contextPath}/login" method="POST">
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
        <td><input class="loginfield placeholder" id="password" name="password" type="password"
        onFocus="removePlaceholder(this)" onBlur="addPlaceholder(this)"  /></td>
    </tr>
    <tr>
        <td><input type="submit" value="Login" /></td>
    </tr>
</table>
</form>
</div>

<h:footer />