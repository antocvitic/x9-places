<%@tag import="com.x9.foodle.user.*"%>


<body>
<div id="all"> <!-- this div closed in /includes/footer.jsp -->

	<div id="header">
        
        <div id="header_login_div">
            <% 
            UserModel user = UserUtils.getCurrentUser(request, response);
            if (user == null) { %>
                <form action="${pageContext.request.contextPath}/login" method="POST">
                    <div class="login_box_text">
                        <input id="rememberme" type="checkbox" name="rememberme" value="rememberme">
                        <label for="rememberme">Keep me logged in.</label>
                        <a href="">Forgot password?</a>
                    </div>
                    <label for="login_username">Username:</label>
                    <input class="loginfield" id="login_username" name="username" type="text" 
                    value="abel" />
                    <label for="login_password">Password:</label>
                    <input class="loginfield" id="login_password" name="password" type="password" />
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
        </div>
        <div id="logo">
            <a href="${pageContext.request.contextPath}">
                <img src="${pageContext.request.contextPath}/images/foodlelogo.png" border="0" alt="Foodle logo" height="100px"/>
            </a>
            <p>Veni Vidi, Review</p>
        </div>
	</div>
	
	<jsp:doBody />