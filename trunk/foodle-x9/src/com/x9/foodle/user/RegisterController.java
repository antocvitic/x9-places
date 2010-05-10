package com.x9.foodle.user;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mindrot.jbcrypt.BCrypt;

import com.x9.foodle.datastore.DBUtils;
import com.x9.foodle.datastore.SQLRuntimeException;
import com.x9.foodle.model.exceptions.BadEmailException;
import com.x9.foodle.model.exceptions.BadLocationException;
import com.x9.foodle.model.exceptions.BadNameException;
import com.x9.foodle.model.exceptions.BadPasswordException;
import com.x9.foodle.model.exceptions.BadUsernameException;
import com.x9.foodle.util.MessageDispatcher;
import com.x9.foodle.util.MessageDispatcher.OkMessage;

@SuppressWarnings("serial")
public class RegisterController extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String password2 = req.getParameter("password2");
		String email = req.getParameter("email");
		String name = req.getParameter("name");
		String location = req.getParameter("location");
		String redirect = req.getParameter("redirect");
		String register_token = req.getParameter("regtoken");
		redirect = redirect == null ? "" : redirect;

		if (register_token != null && password == null) {
			Connection conn = null;
			PreparedStatement stm = null;
			ResultSet result = null;
			try {
				conn = DBUtils.openConnection();

				stm = conn
						.prepareStatement("select * from users where sessionToken = ?");
				stm.setString(1, register_token);
				boolean success = stm.execute();
				if (!success) {
					return;
				}
				result = stm.getResultSet();
				if (!result.next()) {
					return;
				}
				String dbtok = result.getString("sessionToken");
				if (register_token.equals(dbtok)) {
					stm = conn
							.prepareStatement("update users set sessionToken = ? where userID = ?");
					stm.setString(1, dbtok.substring(4)); // removes deny from
															// sessionToken
					stm.setInt(2, result.getInt("userID"));
					if (!stm.execute()) {
						MessageDispatcher.sendMsgRedirect(req, resp,
								"/user/profile.jsp", new OkMessage(
										"Your account has been activated."));
					}
				} else {
					return;
				}
				// Auto-login user and send to profile
				UserModel user = UserModel.getFromDbByUsername(result
						.getString("username"));
				LoginController.doPostLoginStuff(user, req, resp, false);

			} catch (SQLException e) {
				throw new SQLRuntimeException(
						"Bad SQL syntax confirming registration", e);
			} finally {
				DBUtils.closeResultSet(result);
				DBUtils.closeStatement(stm);
				DBUtils.closeConnection(conn);
			}
		} else {
			try {
				UserModel.Builder builder = new UserModel.Builder();

				String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());

				builder.setUsername(username);
				builder.setPasswordHash(passwordHash);
				builder.setEmail(email);
				builder.setName(name);
				builder.setLocation(location);

				builder.validate();

				// we need to validate password here, as UserModel only cares
				// about
				// the hash
				UserModel.Validator.validatePassword(password, password2);

				// We can save the confirmation token temporarily in
				// sessionToken since
				// it is hard to guess anyway for hijacking a session before
				// confirmation.
				// the deny as prefix to token is to check if the user tries
				// login before confirmation.
				Random random = new Random(System.currentTimeMillis());
				String token = BCrypt.hashpw(Long.toString(random.nextLong()),
						BCrypt.gensalt());
				String deny = "deny" + token;
				builder.setSessionToken(deny);

				System.out.println("Confirmation link: "
						+ "http://localhost:8080" + req.getContextPath()
						+ "/register?regtoken=" + deny);
				// EmailUtils.sendEmail(getServletContext(),
				// email, "Registration confirmation link",
				// "Confirmation link: " +
				// "http://localhost:8080"+req.getContextPath()+"/register?regtoken="+deny
				// + " click to activate");

				builder.apply();

				MessageDispatcher
						.sendMsgRedirect(
								req,
								resp,
								redirect,
								new OkMessage(
										"An email has been sent to you for confirmation."));

			} catch (BadUsernameException e) {
				MessageDispatcher.sendMsgRedirect(req, resp, "/login.jsp", e
						.toMessage("Registration failed: "));
			} catch (BadPasswordException e) {
				MessageDispatcher.sendMsgRedirect(req, resp, "/login.jsp", e
						.toMessage("Registration failed: "));
			} catch (BadEmailException e) {
				MessageDispatcher.sendMsgRedirect(req, resp, "/login.jsp", e
						.toMessage("Registration failed: "));
			} catch (BadNameException e) {
				MessageDispatcher.sendMsgRedirect(req, resp, "/login.jsp", e
						.toMessage("Registration failed: "));
			} catch (BadLocationException e) {
				MessageDispatcher.sendMsgRedirect(req, resp, "/login.jsp", e
						.toMessage("Registration failed: "));
			}
		}
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
}
