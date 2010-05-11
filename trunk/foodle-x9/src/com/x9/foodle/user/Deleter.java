package com.x9.foodle.user;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mindrot.jbcrypt.BCrypt;

import com.x9.foodle.datastore.DBUtils;
import com.x9.foodle.datastore.SQLRuntimeException;
import com.x9.foodle.util.EmailUtils;
import com.x9.foodle.util.MessageDispatcher;
import com.x9.foodle.util.URLUtils;
import com.x9.foodle.util.MessageDispatcher.ErrorMessage;
import com.x9.foodle.util.MessageDispatcher.OkMessage;

@SuppressWarnings("serial")
public class Deleter extends HttpServlet {
	Timer timer;

	public class Emailtoken extends TimerTask {
		String tolink = null;
		String toemail = null;

		public Emailtoken(String link, String email) {
			this.tolink = link;
			this.toemail = email;
		}

		@Override
		public void run() {
			// send the email
			EmailUtils.sendEmail(getServletContext(), toemail,
					"Spot Account Deletion",
					"Click on the following link to delete your account:\n"
							+ tolink);
			timer.cancel();
		}
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String what = req.getParameter("what");
		what = what == null ? "" : what;

		UserModel user = UserUtils.getCurrentUser(req, resp);
		if (user != null) {

			String email = req.getParameter("email");
			email = email == null ? "" : email;
			String password = req.getParameter("current_password");
			password = password == null ? "" : password;

			if (what.equals("del_request")) {

				if (!BCrypt.checkpw(password, user.getPasswordHash())
						&& email.equals(user.getEmail())) {
					MessageDispatcher
							.sendMsgRedirect(
									req,
									resp,
									"/user/preferences.jsp#delete",
									new ErrorMessage(
											"Deletion request denied: Wrong password or email"));
					return;
				}

				// make a token from UUID + Random
				String uuid = UUID.randomUUID().toString();
				Random random = new Random(System.currentTimeMillis());
				String token = BCrypt.hashpw(Long.toString(random.nextLong()),
						BCrypt.gensalt());
				String confirmtoken = token + uuid;
				// Save confirmtoken to db, since it will be read only in 12+
				// hours to confirm deletion request.
				Connection conn = null;
				PreparedStatement stm = null;
				ResultSet result = null;
				try {
					conn = DBUtils.openConnection();
					stm = conn
							.prepareStatement("update users set deleteToken = ? where userID = ?");
					stm.setString(1, confirmtoken);
					stm.setInt(2, user.getID());

					if (stm.executeUpdate() == 0) {
						MessageDispatcher.sendMsgRedirect(req, resp,
								"/user/preferences.jsp#delete",
								new ErrorMessage("Oops, should not happen."));
						return;
					}

				} catch (SQLException e) {
					throw new SQLRuntimeException(
							"Bad SQL syntax inserting/updating user", e);
				} finally {
					DBUtils.closeResultSet(result);
					DBUtils.closeStatement(stm);
					DBUtils.closeConnection(conn);
				}

				// String confirmlink =
				// "http://localhost:8080/"+req.getContextPath()+"/user/delete?confirm_del="+confirmtoken;
				String confirmlink = URLUtils.getServerURL(req)
						+ "/user/delete?confirm_del=" + confirmtoken;
				System.out.println("CONFIRMlink : " + confirmlink);

				timer = new Timer();
				timer.schedule(new Emailtoken(confirmlink, user.getEmail()),
						8 * 1000); // 8 seconds for demo

			}// if del_request end
			MessageDispatcher.sendMsgRedirect(req, resp, "/logout",
					new OkMessage(
							"An email has been sent to you for confirmation."));
		} // if user != null
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String confirm_del = req.getParameter("confirm_del");

		if (confirm_del != null) {
			confirm_del = confirm_del == null ? "" : confirm_del;
			Connection conn = null;
			PreparedStatement stm = null;
			ResultSet result = null;
			try {
				conn = DBUtils.openConnection();
				stm = conn
						.prepareStatement("update users set username = null, passwordHash = '', "
								+ "email = null, name = '', repLevel = null, isFBConnected = false,"
								+ "location = '', sessionToken = null where deleteToken = ?");

				stm.setString(1, confirm_del);

				if (stm.executeUpdate() == 0) {
					MessageDispatcher.sendMsgRedirect(req, resp,
							"/user/preferences.jsp#delete", new ErrorMessage(
									"Deletion request failed: key not found"));
					return;
				}

			} catch (SQLException e) {
				throw new SQLRuntimeException(
						"Bad SQL syntax inserting/updating user", e);
			} finally {
				DBUtils.closeResultSet(result);
				DBUtils.closeStatement(stm);
				DBUtils.closeConnection(conn);
			}

			MessageDispatcher.sendMsgRedirect(req, resp, "/", new OkMessage(
					"Account has been deleted."));
		}
	}
}
