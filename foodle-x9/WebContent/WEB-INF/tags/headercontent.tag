


<body>
<div id="all"> <!-- this div closed in /includes/footer.jsp -->

	<div id="header">
		<div id="logo">
			<div id="login_div">
			<% 
			if (com.x9.foodle.user.UserUtils.getCurrentUser(request) == null) {
				%>
				<form action="${pageContext.request.contextPath}/login" method="POST">
					<h3 class="login_box_text">
						<input id="checkbox_field" type="checkbox" name="givecookie" value="givecookie">
						<label for="checkbox_field">Keep me logged in.</label>
						<a href="">Forgot password?</a>
					</h3>
					<label for="login_username">Username:</label>
					<input class="loginfield" id="login_username" name="username" type="text" 
					value="abel" />
					<label for="login_password">Password:</label>
					<input class="loginfield" id="login_password" name="password" type="password" />
					<input type="submit" value="Log in" />
				</form>
				<h3 class="login_box_text">Login, <a href="${pageContext.request.contextPath}/login.jsp">register</a>
					or <input type="button" value="Connect using Facebook" disabled="disabled" />
				</h3>
			<% } else {
					%><h3 class="login_box_text">
					<a href="">Home ...</a> |
					<a href="">Preferences etc</a> |
					<a href="${pageContext.request.contextPath}/profile.jsp">Profile</a> |
					<a href="${pageContext.request.contextPath}/logout">logout</a>
					</h3>
				<% }
			%>
			</div>
			<a href="${pageContext.request.contextPath}">
				<img src="${pageContext.request.contextPath}/images/foodlelogo.png" border="0" alt="Foodle logo" width="200px" height="100px"/>
			</a>
			<p>Veni Vidi, Review</p>
		</div>
	</div>
	
	<jsp:doBody />