<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@ taglib tagdir="/WEB-INF/tags" prefix="h"%>

<%@page import="com.x9.foodle.util.*"%>
<%@page import="com.x9.foodle.user.*"%>

<%
    UserModel user = UserUtils.getCurrentUser(request, response);
 %>


<h:header title="Account preferences - Foodle X9">

<script type="text/javascript">
    $(function(){
        // init tabs
        $('#tabs').tabs();
    });
</script>


</h:header>
<h:headercontent />
<h2>Change account settings and preferences</h2>
<br/>

<%= user.getName() %>

<div id="tabs">
    <ul>
        <li><a href="#general">General</a></li>
        <li><a href="#password">Password</a></li>
        <li><a href="#delete">Delete account</a></li>
        <li><a href="#privacy">Privacy...</a></li>
    </ul>
    <div id="general">
        <form id="generalt_edit_form" action="${pageContext.request.contextPath}/user/edit" method="POST">
        <input type="hidden" name="editWhat" value="general" />
        <table>
            <tr>
                <td><label for="email">Email</label></td>
            </tr>
            <tr>
                <td><input class="loginfield placeholder" id="email" name="email" type="text"
                    onFocus="removePlaceholder(this)" onBlur="addPlaceholder(this)" value="<%= user.getEmail() %>" /></td>
            </tr>
            <tr>
                <td><label for="name">Name</label></td>
            </tr>
            <tr>
                <td><input class="loginfield placeholder" id="name" name="name" type="text"
                    onFocus="removePlaceholder(this)" onBlur="addPlaceholder(this)" value="<%= user.getName() %>" /></td>
            </tr>
            <tr>
                <td><label for="location">Location</label></td>
            </tr>
            <tr>
                <td><input class="loginfield placeholder" id="location" name="location" type="text"
                    onFocus="removePlaceholder(this)" onBlur="addPlaceholder(this)" value="<%= user.getLocation() %>" /></td>
            </tr>
            <tr>
                <td><input type="submit" value="Save" /></td>
            </tr>
        </table>
        </form>
    </div>
    
    <div id="password">
        <form id="password_edit_form" action="${pageContext.request.contextPath}/user/edit" method="POST">
        <input type="hidden" name="editWhat" value="password" />
        <table>
            <tr>
                <td><label for="current_password">Current password</label></td>
            </tr>
            <tr>
                <td><input class="loginfield placeholder password_placeholder" id="current_password" name="current_password" 
                type="password" onFocus="removePlaceholder(this)" onBlur="addPlaceholder(this)" /></td>
            </tr>
            <tr>
                <td><label for="new_password">New password</label></td>
            </tr>
            <tr>
                <td><input class="loginfield placeholder password_placeholder" id="new_password" name="new_password"
                type="password" onFocus="removePlaceholder(this)" onBlur="addPlaceholder(this)" /></td>
            </tr>
            <tr>
                <td><label for="new_password2">Retype new password</label></td>
            </tr>
            <tr>
                <td><input class="loginfield" id="new_password2"
                    name="new_password2" type="password" /></td>
            </tr>
        
            <tr>
                <td><input type="submit" value="Set new password" /></td>
            </tr>
        </table>
        </form>
    </div>

    <div id="delete">
        If you wish to delete your user from this site, fill in your email and password below.<br />
        You will receive a confirmation email only after 12 hours have passed. This is for your security,
        if you account has been compromised, you can cancel the delete request in 12 hours.
        WARNING: All your user-data will be deleted, forever!
        <form id="user_delete_form" action="${pageContext.request.contextPath}/user/delete" method="POST">
        <input type="hidden" name="what" value="del_request" />
        <table>
             <tr>
                <td><label for="email">Email</label></td>
            </tr>
            <tr>
                <td><input class="loginfield placeholder" id="email" name="email" type="text"
                    onFocus="removePlaceholder(this)" onBlur="addPlaceholder(this)" /></td>
            </tr>
            <tr>
                <td><label for="current_password">Current password</label></td>
            </tr>
            <tr>
                <td><input class="loginfield placeholder password_placeholder" id="current_password" name="current_password" 
                type="password" onFocus="removePlaceholder(this)" onBlur="addPlaceholder(this)" /></td>
            </tr>
            <tr>
                <td><input type="submit" value="Delete this acount" /></td>
            </tr>
            </table>
        </form>
    </div>
    
    <div id="privacy">
        Privacy is very important.....
    </div>


</div>

<h:footer />