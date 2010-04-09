package com.x9.foodle.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mindrot.jbcrypt.BCrypt;

import com.x9.foodle.user.exceptions.BadEmailException;
import com.x9.foodle.user.exceptions.BadPasswordException;
import com.x9.foodle.user.exceptions.BadUsernameException;

@SuppressWarnings("serial")
public class RegisterController extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String email = req.getParameter("email");
		String name = req.getParameter("name");
		String redirect = req.getParameter("redirect");

		String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());

		UserModel.Builder builder = new UserModel.Builder();

		builder.setUsername(username);
		builder.setPasswordHash(passwordHash);
		builder.setEmail(email);
		builder.setName(name);

		try {
			builder.apply();
			resp.sendRedirect(redirect);
		} catch (BadUsernameException e) {
			throw new RuntimeException(e);
			//resp.sendRedirect("login.jsp?error=badusername");
		} catch (BadPasswordException e) {
			throw new RuntimeException(e);
			//resp.sendRedirect("login.jsp?error=badpassword");
		} catch (BadEmailException e) {
			throw new RuntimeException(e);
			//resp.sendRedirect("login.jsp?error=bademail");
		}
	}

}
