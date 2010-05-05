package com.x9.foodle.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mindrot.jbcrypt.BCrypt;

import com.x9.foodle.model.exceptions.BadEmailException;
import com.x9.foodle.model.exceptions.BadPasswordException;
import com.x9.foodle.model.exceptions.BadUsernameException;
import com.x9.foodle.util.MessageDispatcher;
import com.x9.foodle.util.MessageDispatcher.ErrorMessage;

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

		try {
			UserModel.Builder builder = new UserModel.Builder();

			String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());

			builder.setUsername(username);
			builder.setPasswordHash(passwordHash);
			builder.setEmail(email);
			builder.setName(name);

			builder.validate();

			// we need to validate password here, as UserModel only cares about
			// the hash
			UserModel.Validator.validatePassword(password);

			builder.apply();

			// EmailUtils.sendEmail(getServletContext(),
			// "adam.renberg@gmail.com", "Testing emailing",
			// "User: " + username + " has been registered");

			if (redirect == null) {
				resp.sendRedirect(req.getContextPath() + "/profile.jsp");
			} else {
				resp.sendRedirect(redirect);
			}
		} catch (BadUsernameException e) {
			MessageDispatcher.sendMsgRedirect(req, resp, "/login.jsp",
					new ErrorMessage("Login failed: Bad username:" + username));
		} catch (BadPasswordException e) {
			// throw new RuntimeException(e);
			MessageDispatcher.sendMsgRedirect(req, resp, "/login.jsp",
					new ErrorMessage("Login failed: Bad password"));
		} catch (BadEmailException e) {
			// throw new RuntimeException(e);
			MessageDispatcher.sendMsgRedirect(req, resp, "/login.jsp",
					new ErrorMessage("Login failed: Bad email:" + email));
		}
	}

}
