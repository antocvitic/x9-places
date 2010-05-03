<%@ tag import="com.x9.foodle.user.*" %>
<%@ tag import="com.x9.foodle.util.*" %>

<body>
<div id="all"> <!-- this div closed in /WEB-INF/tags/footer.jsp -->
	
	<div class="header">
		<!-- logo -->
        <div id="logo">
            <a href="${pageContext.request.contextPath}" id="header-no-opacity">
                <img src="${pageContext.request.contextPath}/images/spot-logo-veni.png" border="0" alt="spot logo" height="100px"/>
            </a>
        </div>
        
        <!-- search-field -->
        <div id="search">
        	<img src="${pageContext.request.contextPath}/images/small-search.png" id="search_icon"/>
        	<form name="search form">
        		<input type="text" class="searchfield"/>
        		<input type="submit" value="search" id="search_button"/>
        	</form>
        </div>
        
        <!-- login/logout -->
        <div id="header_login_div">
            <% 
            String redirect = request.getParameter("redirect");
            if (redirect != null) {
            	redirect = URLUtils.decode(redirect);
            } else {
                redirect = URLUtils.getCurrentURL(request);   
            }
            UserModel user = UserUtils.getCurrentUser(request, response);
            if (user == null) { %>
                <form action="${pageContext.request.contextPath}/login" method="POST" name="login_form">
                    <input type="hidden" name="redirect" value="<%= redirect %>" />
                    <div class="login_box_text">
                        <input id="rememberme" type="checkbox" name="rememberme" value="rememberme">
                        <label for="rememberme">Keep me logged in.</label>
                        <a href="">Forgot password?</a>
                    </div>
                     <!-- <label for="login_username">Username:</label>-->
                    <input class="loginfield placeholder" id="login_username" name="username" type="text" 
                    onFocus="removePlaceholder(this)" onBlur="addPlaceholder(this)" value="" />
                     <!-- <label for="login_password">Password:</label>-->
                    <input class="loginfield placeholder" id="login_password" name="password" type="password"
                    onFocus="removePlaceholder(this)" onBlur="addPlaceholder(this)"/>
                    <script language="JavaScript">loadPlaceholders();</script>
                    <input type="submit" value="Log in" />
                </form>
                <p class="login_box_text">Login, <a href="${pageContext.request.contextPath}/login.jsp">register</a>
                    or <input type="button" value="Connect using Facebook" disabled="disabled" />
                </p>
            <% } else { %>
                    <p class="login_box_text">
                        <a href="${pageContext.request.contextPath}/user/profile.jsp"><%= user.getName() %>'s profile</a> |
                        <a href="${pageContext.request.contextPath}/user/preferences.jsp">Preferences etc</a> |
                        <a href="${pageContext.request.contextPath}/logout">logout</a>
                    </p>
            <% } %>
        </div> <!-- end of header_login_div div -->
        
	</div> <!-- end of header div -->
	
	<div id="content_wrapper">  <!-- this div closed in /WEB-INF/tags/footer.jsp -->
	<jsp:doBody />