package com.x9.foodle.review;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.x9.foodle.model.exceptions.InvalidCreatorIDException;
import com.x9.foodle.model.exceptions.InvalidIDException;
import com.x9.foodle.model.exceptions.InvalidTextException;
import com.x9.foodle.model.exceptions.InvalidTitleException;
import com.x9.foodle.model.exceptions.InvalidVenueReferenceException;
import com.x9.foodle.user.UserUtils;

@SuppressWarnings("serial")
public class EditController extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String title = req.getParameter("title");
		String text = req.getParameter("text");
		String venueID = req.getParameter("venueID");

		String redirect = req.getParameter("redirect");

		try {
			ReviewModel.Builder builder = new ReviewModel.Builder();

			builder.setTitle(title);
			builder.setText(text);
			builder.setVenueID(venueID);
			builder.setCreator(UserUtils.getCurrentUser(req, resp));
			builder.apply();

			resp.sendRedirect(redirect);
		} catch (InvalidIDException e) {
			throw new RuntimeException(e);
		} catch (InvalidTitleException e) {
			throw new RuntimeException(e);
		} catch (InvalidTextException e) {
			throw new RuntimeException(e);
		} catch (InvalidVenueReferenceException e) {
			throw new RuntimeException(e);
		} catch (InvalidCreatorIDException e) {
			throw new RuntimeException(e);
		}
	}
}
