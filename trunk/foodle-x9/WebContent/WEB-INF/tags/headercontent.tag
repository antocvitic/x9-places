<%@tag import="com.x9.foodle.user.*"%>


<body>
<div id="all"> <!-- this div closed in /WEB-INF/tags/footer.jsp -->
	
<div id="main">  <!-- this div closed in /WEB-INF/tags/footer.jsp -->
	<!-- shadows -->
	<div id="left_gutter"></div>
	<div id="top_left_gutter"></div>
	<div id="top_gutter"></div> 
	<div id="top_right_gutter"></div> 
	<div id="header">
		<!-- logo -->
        <div id="logo">
            <a href="${pageContext.request.contextPath}">
                <img src="${pageContext.request.contextPath}/images/spot-logo-veni.png" border="0" alt="Foodle logo" height="100px"/>
            </a>
        </div>
        <!-- search-field -->
        
        <!-- login/logout -->
        <div id="header_login_div">
            <% 
            UserModel user = UserUtils.getCurrentUser(request, response);
            if (user == null) { %>
                <form action="${pageContext.request.contextPath}/login" method="POST" name="login_form">
                    <div class="login_box_text">
                        <input id="rememberme" type="checkbox" name="rememberme" value="rememberme">
                        <label for="rememberme">Keep me logged in.</label>
                        <a href="">Forgot password?</a>
                    </div>
                    <label for="login_username">Username:</label>
                    <input class="loginfield" id="login_username" name="username" type="text" 
                    onFocus="removePlaceholder(this)" onBlur="addPlaceholder(this)" value="abel" />
                    <label for="login_password">Password:</label>
                    <input class="loginfield" id="login_password" name="password" type="password"
                    onFocus="removePlaceholder(this)" onBlur="addPlaceholder(this)"/>
                    <script language="JavaScript">loadPlaceholders();</script>
                    <input type="submit" value="Log in" />
                </form>
                <p class="login_box_text">Login, <a href="${pageContext.request.contextPath}/login.jsp">register</a>
                    or <input type="button" value="Connect using Facebook" disabled="disabled" />
                </p>
            <% } else { %>
                    <p class="login_box_text">
                        <a href="${pageContext.request.contextPath}/profile.jsp"><%= user.getName() %>'s profile</a> |
                        <a href="#">Preferences etc</a> |
                        <a href="${pageContext.request.contextPath}/logout">logout</a>
                    </p>
            <% } %>
        </div> <!-- end of header_login_div div -->
        
	</div> <!-- end of header div -->
	
	<div id="content_wrapper">  <!-- this div closed in /WEB-INF/tags/footer.jsp -->
	<jsp:doBody />