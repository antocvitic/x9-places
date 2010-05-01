package com.x9.foodle.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mindrot.jbcrypt.BCrypt;

import com.x9.foodle.datastore.SQLRuntimeException;
import com.x9.foodle.model.exceptions.BadEmailException;
import com.x9.foodle.model.exceptions.BadPasswordException;
import com.x9.foodle.model.exceptions.BadUsernameException;
import com.x9.foodle.util.MessageDispatcher;

@SuppressWarnings("serial")
public class EditController extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String editWhat = req.getParameter("editWhat");
		if (editWhat == null || editWhat.isEmpty()) {
			throw new IllegalArgumentException("editWhat is null or empty");
		}

		UserModel user = UserUtils.getCurrentUser(req, resp);

		if (editWhat.equals("general")) {
			String name = req.getParameter("name");
			name = name == null ? "" : name;
			String email = req.getParameter("email");
			email = email == null ? "" : email;

			UserModel.Builder builder = user.getEditable();

			builder.setEmail(email);
			builder.setName(name);

			try {
				builder.apply();
			} catch (BadUsernameException e) {
				throw new RuntimeException(
						"got bad username when editing general user info", e);
			} catch (BadPasswordException e) {
				throw new RuntimeException(
						"got bad password when editing general user info", e);
			} catch (BadEmailException e) {
				MessageDispatcher.sendMsgRedirect(req, resp,
						"/user/preferences.jsp#general", "bad email");
				return;
			} catch (SQLRuntimeException e) {
				throw e;
			}

			MessageDispatcher.sendMsgRedirect(req, resp,
					"/user/preferences.jsp#general", "ok");
		} else if (editWhat.equals("password")) {
			String current_password = req.getParameter("current_password");
			String new_password = req.getParameter("new_password");

			if (!BCrypt.checkpw(current_password, user.getPasswordHash())) {
				MessageDispatcher.sendMsgRedirect(req, resp,
						"/user/preferences.jsp#password",
						"wrong current password");
				return;
			}

			try {
				UserModel.Validator.validatePassword(new_password);
				UserModel.Builder builder = user.getEditable();
				builder.setPasswordHash(BCrypt.hashpw(new_password, BCrypt
						.gensalt()));
				builder.apply();
			} catch (BadUsernameException e) {
				throw new RuntimeException(
						"got bad username when editing password", e);
			} catch (BadPasswordException e) {
				MessageDispatcher.sendMsgRedirect(req, resp,
						"/user/preferences.jsp#password", "bad password");
				return;
			} catch (BadEmailException e) {
				throw new RuntimeException(
						"got bad email when editing password", e);
			} catch (SQLRuntimeException e) {
				throw e;
			}

			MessageDispatcher.sendMsgRedirect(req, resp,
					"/user/preferences.jsp#password", "ok");
		} else {
			throw new RuntimeException("unknown editWhat: " + editWhat);
		}
	}

}
