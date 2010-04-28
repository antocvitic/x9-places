package com.x9.foodle.testservlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.x9.foodle.datastore.SQLRuntimeException;
import com.x9.foodle.model.exceptions.InvalidUserException;
import com.x9.foodle.user.UserModel;

@SuppressWarnings("serial")
public class AdamTest extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String url = "/foodle-x9/test.jsp?";

		String name = request.getParameter("name");

		if (name != null) {

			UserModel user = UserModel.getFromDbByUsername(name + "_un");
			UserModel.Builder builder;
			String email;
			if (user != null) {
				url += "edit&";
				builder = user.getEditable();
				email = "edit_" + name + "@fakemail.com";
			} else {
				url += "new&";
				builder = new UserModel.Builder();
				email = "new_" + name + "@fakemail.com";
			}

			builder.setUsername(name + "_un");
			builder.setPasswordHash("asdf");
			builder.setEmail(email);
			builder.setName(name);

			try {
				builder.apply();
			} catch (InvalidUserException e) {
				throw new RuntimeException(e);
			} catch (SQLRuntimeException e) {
				throw e;
			}

			url += "it_worked";
		} else {
			url += "no_name";
		}

		response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);

		response.setHeader("Location", url);
	}
}
