package com.x9.foodle.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mindrot.jbcrypt.BCrypt;

import com.x9.foodle.datastore.SQLRuntimeException;
import com.x9.foodle.model.exceptions.BadEmailException;
import com.x9.foodle.model.exceptions.BadLocationException;
import com.x9.foodle.model.exceptions.BadNameException;
import com.x9.foodle.model.exceptions.BadPasswordException;
import com.x9.foodle.model.exceptions.BadUsernameException;
import com.x9.foodle.util.MessageDispatcher;
import com.x9.foodle.util.MessageDispatcher.ErrorMessage;
import com.x9.foodle.util.MessageDispatcher.OkMessage;


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
			String location = req.getParameter("location");
			location= location == null ? "" : location;
			
			UserModel.Builder builder = user.getEditable();

			builder.setEmail(email);
			builder.setName(name);
			builder.setLocation(location);
			
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
						"/user/preferences.jsp#general", e
								.toMessage("Preferences not updated: "));
				return;
			} catch (BadNameException e) {
				MessageDispatcher.sendMsgRedirect(req, resp,
					"/user/preferences.jsp#general", e
							.toMessage("Preferences not updated: "));
			} catch (BadLocationException e) {
				MessageDispatcher.sendMsgRedirect(req, resp,
					"/user/preferences.jsp#general", e
							.toMessage("Preferences not updated: "));
			} catch (SQLRuntimeException e) {
				throw e;
			}

			MessageDispatcher.sendMsgRedirect(req, resp,
					"/user/preferences.jsp#general", new OkMessage(
							"Preferences updated"));
		} else if (editWhat.equals("password")) {
			String current_password = req.getParameter("current_password");
			String new_password = req.getParameter("new_password");
			String new_password2 = req.getParameter("new_password2");

			if (!BCrypt.checkpw(current_password, user.getPasswordHash())) {
				MessageDispatcher.sendMsgRedirect(req, resp,
						"/user/preferences.jsp#password", new ErrorMessage(
								"Password not updated: Wrong password"));
				return;
			}

			try {
				UserModel.Validator.validatePassword(new_password,
						new_password2);
				UserModel.Builder builder = user.getEditable();
				builder.setPasswordHash(BCrypt.hashpw(new_password, BCrypt
						.gensalt()));
				builder.apply();
			} catch (BadUsernameException e) {
				throw new RuntimeException(
						"got bad username when editing password", e);
			} catch (BadPasswordException e) {
				MessageDispatcher.sendMsgRedirect(req, resp,
						"/user/preferences.jsp#password", e
								.toMessage("Preferences not updated: "));
				return;
			} catch (BadEmailException e) {
				throw new RuntimeException(
					"got bad email when editing password", e);
			} catch (BadNameException e) {
				MessageDispatcher.sendMsgRedirect(req, resp,
					"/user/preferences.jsp#general", e
						.toMessage("Preferences not updated: "));
			} catch (BadLocationException e) {
				MessageDispatcher.sendMsgRedirect(req, resp,
					"/user/preferences.jsp#general", e
						.toMessage("Preferences not updated: "));
			} catch (SQLRuntimeException e) {
				throw e;
			}

			MessageDispatcher.sendMsgRedirect(req, resp,
					"/user/preferences.jsp#password", new OkMessage(
							"Password updated"));
		} else if (editWhat.equals("deletion")) {
		
			String email = req.getParameter("email");
			email = email == null ? "" : email;
			String password = req.getParameter("password");
			password = password == null ? "" : password;
			
			if (!BCrypt.checkpw(password, user.getPasswordHash()) 
					&& email.equals(user.getEmail())) {
				MessageDispatcher.sendMsgRedirect(req, resp,
						"/user/preferences.jsp#delete", new ErrorMessage(
								"Deletion request denied: Wrong password or email"));
				return;
			}
			
			//Send an email in 12 hours with a token
			/*SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();
			try {
			Scheduler sched = schedFact.getScheduler();
			
			
			JobDetail jobDetail = new JobDetail("job", null, EmailUtils.class);

			Trigger trigger = new SimpleTrigger("trigger1", "group1", new Date()); // fire now
			trigger.setStartTime(TriggerUtils.getEvenHourDate(new Date()));  // start on the next even hour
			//trigger.setName("trigger");

			
				sched.scheduleJob(jobDetail, trigger);
				sched.start();
			} catch (SchedulerException e) {
				// TODO Auto-generated catch block
				MessageDispatcher.sendMsgRedirect(req, resp,
						"/user/preferences.jsp#delete", 
						new MessageDispatcher.ErrorMessage("Scheduling error, contact administrators: "));
			}*/
			
			
			UserModel.Builder builder = user.getEditable();
			builder.setDeleted(true);

			try {
				builder.apply();
			} catch (BadUsernameException e) {
				throw new RuntimeException(
						"got bad username when editing user info", e);
			} catch (BadPasswordException e) {
				throw new RuntimeException(
						"got bad password when editing user info", e);
			} catch (BadEmailException e) {
				MessageDispatcher.sendMsgRedirect(req, resp,
						"/user/preferences.jsp#delete", e
								.toMessage("Preferences not updated: "));
				return;
			} catch (BadNameException e) {
				MessageDispatcher.sendMsgRedirect(req, resp,
					"/user/preferences.jsp#delete", e
							.toMessage("Preferences not updated: "));
			} catch (BadLocationException e) {
				MessageDispatcher.sendMsgRedirect(req, resp,
					"/user/preferences.jsp#delete", e
							.toMessage("Preferences not updated: "));
			} catch (SQLRuntimeException e) {
				throw e;
			}
			MessageDispatcher.sendMsgRedirect(req, resp,
					"/user/preferences.jsp#delete", new OkMessage(
							"Deletion email has been sent"));

		}
		else {
			throw new RuntimeException("unknown editWhat: " + editWhat);
		}
	}

}
