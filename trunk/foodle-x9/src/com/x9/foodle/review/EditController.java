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
import com.x9.foodle.util.MessageDispatcher;

@SuppressWarnings("serial")
public class EditController extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String title = req.getParameter("title");
		String text = req.getParameter("text");
		String venueID = req.getParameter("venueID");
		String reviewID = req.getParameter("reviewID");

		String redirect = req.getParameter("redirect");

		String what = "";

		try {
			ReviewModel.Builder builder = null;
			if (reviewID != null && !reviewID.isEmpty()) {
				// edit existing review
				ReviewModel tempReview = ReviewModel.getFromSolr(reviewID);
				if (tempReview == null) {
					throw new RuntimeException("no review with id: " + reviewID);
				}
				builder = tempReview.getEditable();
				what = "Review edit failed: ";
			} else {
				// create new venue
				builder = new ReviewModel.Builder();
				what = "Review insertion failed: ";
			}

			builder.setTitle(title);
			builder.setText(text);
			builder.setVenueID(venueID);
			builder.setCreator(UserUtils.getCurrentUser(req, resp));
			builder.apply();

			resp.sendRedirect(redirect + "?venueID=" + venueID);
		} catch (InvalidIDException e) {
			MessageDispatcher.sendMsgRedirect(req, resp,
					"/review/edit.jsp?venueID=" + venueID, e.toMessage(what));
			// TODO: log error
		} catch (InvalidTitleException e) {
			MessageDispatcher.sendMsgRedirect(req, resp,
					"/review/edit.jsp?venueID=" + venueID, e.toMessage(what));
			// TODO: log error
		} catch (InvalidTextException e) {
			MessageDispatcher.sendMsgRedirect(req, resp,
					"/review/edit.jsp?venueID=" + venueID, e.toMessage(what));
			// TODO: log error
		} catch (InvalidVenueReferenceException e) {
			MessageDispatcher.sendMsgRedirect(req, resp,
					"/review/edit.jsp?venueID=" + venueID, e.toMessage(what));
			// TODO: log error
		} catch (InvalidCreatorIDException e) {
			MessageDispatcher.sendMsgRedirect(req, resp,
					"/review/edit.jsp?venueID=" + venueID, e.toMessage(what));
			// TODO: log error
		}
	}
}
