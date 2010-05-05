package com.x9.foodle.venue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.x9.foodle.model.exceptions.InvalidAddressException;
import com.x9.foodle.model.exceptions.InvalidAverageRatingException;
import com.x9.foodle.model.exceptions.InvalidCreatorIDException;
import com.x9.foodle.model.exceptions.InvalidDescriptionException;
import com.x9.foodle.model.exceptions.InvalidIDException;
import com.x9.foodle.model.exceptions.InvalidNumberOfRatingsException;
import com.x9.foodle.model.exceptions.InvalidTitleException;
import com.x9.foodle.user.UserUtils;
import com.x9.foodle.util.MessageDispatcher;

@SuppressWarnings("serial")
public class EditController extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String venueID = req.getParameter("venueID");
		String title = req.getParameter("title");
		String address = req.getParameter("address");
		String description = req.getParameter("description");
		String tags = req.getParameter("tags");

		ArrayList<String> tagsList = new ArrayList<String>();
		if (tags != null) {
			Scanner scanny = new Scanner(tags);
			while (scanny.hasNext()) {
				tagsList.add(scanny.next());
			}
		}

		String redirect = req.getParameter("redirect");

		String what = "";

		try {
			VenueModel.Builder builder = null;
			if (venueID != null && !venueID.isEmpty()) {
				VenueModel tempVenue = VenueModel.getFromSolr(venueID);
				if (tempVenue == null) {
					throw new RuntimeException("no venue with id " + venueID
							+ " to edit");
				}
				builder = tempVenue.getEditable();
				what = "Edit venue failed: ";
			} else {
				builder = new VenueModel.Builder();
				what = "Venue insertion failed: ";
			}

			builder.setTitle(title);
			builder.setAddress(address);
			builder.setDescription(description);
			builder.setCreator(UserUtils.getCurrentUser(req, resp));
			builder.setTags(tagsList);
			VenueModel venue = builder.apply();

			resp.sendRedirect(redirect + "?venueID=" + venue.getID());
		} catch (InvalidIDException e) {
			MessageDispatcher.sendMsgRedirect(req, resp,
					"/venue/edit.jsp?venueID=" + venueID, e.toMessage(what));
			// TODO: log error
		} catch (InvalidTitleException e) {
			MessageDispatcher.sendMsgRedirect(req, resp,
					"/venue/edit.jsp?venueID=" + venueID, e.toMessage(what));
			// TODO: log error
		} catch (InvalidAddressException e) {
			MessageDispatcher.sendMsgRedirect(req, resp,
					"/venue/edit.jsp?venueID=" + venueID, e.toMessage(what));
			// TODO: log error
		} catch (InvalidDescriptionException e) {
			MessageDispatcher.sendMsgRedirect(req, resp,
					"/venue/edit.jsp?venueID=" + venueID, e.toMessage(what));
			// TODO: log error
		} catch (InvalidNumberOfRatingsException e) {
			MessageDispatcher.sendMsgRedirect(req, resp,
					"/venue/edit.jsp?venueID=" + venueID, e.toMessage(what));
			// TODO: log error
		} catch (InvalidAverageRatingException e) {
			MessageDispatcher.sendMsgRedirect(req, resp,
					"/venue/edit.jsp?venueID=" + venueID, e.toMessage(what));
			// TODO: log error
		} catch (InvalidCreatorIDException e) {
			MessageDispatcher.sendMsgRedirect(req, resp,
					"/venue/edit.jsp?venueID=" + venueID, e.toMessage(what));
			// TODO: log error
		}
	}

}
