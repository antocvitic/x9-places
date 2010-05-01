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

<h:msg />

<div id="tabs">
    <ul>
        <li><a href="#general">General</a></li>
        <li><a href="#password">Password</a></li>
        <li><a href="#tab-3">Notifications...</a></li>
        <li><a href="#tab-4">Privacy...</a></li>
    </ul>
    <div id="general">
        <form action="${pageContext.request.contextPath}/user/edit" method="POST">
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
                <td><input type="submit" value="Save" /></td>
            </tr>
        </table>
        </form>
    </div>
    
    <div id="password">
        <form action="${pageContext.request.contextPath}/user/edit" method="POST">
        <input type="hidden" name="editWhat" value="password" />
        <table>
            <tr>
                <td><label for="current_password">Current password</label></td>
            </tr>
            <tr>
                <td><input class="loginfield" id="current_password" name="current_password" type="password"/></td>
            </tr>
            <tr>
                <td><label for="new_password">New password</label></td>
            </tr>
            <tr>
                <td><input class="loginfield" id="new_password" name="new_password" type="password" /></td>
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

    <div id="tab-3">
        Nam dui erat, auctor a, dignissim quis, sollicitudin eu, felis. Pellentesque nisi urna, interdum eget, sagittis et, consequat vestibulum, lacus. Mauris porttitor ullamcorper augue.
    </div>
    
    <div id="tab-4">
        Tempetetojasdfasfd
    </div>


</div>

<h:footer />