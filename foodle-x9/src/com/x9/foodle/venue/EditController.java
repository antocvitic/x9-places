package com.x9.foodle.venue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.x9.foodle.model.exceptions.InvalidAddressException;
import com.x9.foodle.model.exceptions.InvalidAverageRatingException;
import com.x9.foodle.model.exceptions.InvalidCreatorIDException;
import com.x9.foodle.model.exceptions.InvalidDescriptionException;
import com.x9.foodle.model.exceptions.InvalidIDException;
import com.x9.foodle.model.exceptions.InvalidNumberOfRatingsException;
import com.x9.foodle.model.exceptions.InvalidTitleException;
import com.x9.foodle.user.UserUtils;
import com.x9.foodle.util.MessageDispatcher;
import com.x9.foodle.util.MessageDispatcher.Message;
import com.x9.foodle.util.MessageDispatcher.OkMessage;

@SuppressWarnings("serial")
public class EditController extends HttpServlet {
	
	public static void checkLengths() {
		
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		

		
		String venueID = req.getParameter("venueID");
		String title = req.getParameter("title");
		String address = req.getParameter("address");
		String description = req.getParameter("description");
		String tags = req.getParameter("your_tags");
		String redirect = req.getParameter("redirect");
		String dowhat = req.getParameter("what");

		String what = "";

		ArrayList<String> tagsList = new ArrayList<String>();
		if (tags != null) {
			Scanner scanny = new Scanner(tags);
			while (scanny.hasNext()) {
				tagsList.add(scanny.next());
			}
		}
		try {
		if (dowhat != null && dowhat.equals("addtags")) {
			
				VenueModel.Builder builder = null;
				if (venueID != null && !venueID.isEmpty()) {
					VenueModel tempVenue = VenueModel.getFromSolr(venueID);
					if (tempVenue == null) {
						what = "Adding tags for this venue failed: ";
						throw new RuntimeException("no venue with id " + venueID
								+ " to edit");
							
					}
					builder = tempVenue.getEditable();
					tagsList.addAll(tempVenue.getTags());
					builder.setTags(tagsList);
					VenueModel venue = builder.apply();
					
					MessageDispatcher.sendMsgRedirect(req, resp,
							"/venue/view.jsp?venueID=" + venue.getID(), new OkMessage(
								"The venue has been tagged."));
				}
		}
		else {
			VenueModel.Builder builder = null;
			if (venueID != null && !venueID.isEmpty()) {
				what = "Editing venue failed: ";
				VenueModel tempVenue = VenueModel.getFromSolr(venueID);
				if (tempVenue == null) {
					throw new RuntimeException("no venue with id in Solr " + venueID + " to edit");
				}
				if (UserUtils.getCurrentUser(req, resp).getReputationLevel() < UserUtils.EDIT_LEVEL) {
					MessageDispatcher.sendMsgRedirect(req, resp,
							"/venue/view.jsp?venueID=" + venueID, 
							new MessageDispatcher.ErrorMessage("You do not have enough reputation points to edit this."));
				} else {
					builder = tempVenue.getEditable();
					builder.setTitle(title);
					builder.setAddress(address);
					builder.setDescription(description);
					//builder.setCreator(UserUtils.getCurrentUser(req, resp));
					tagsList.addAll(tempVenue.getTags());
					builder.setTags(tagsList);
					VenueModel venue = builder.apply();
					MessageDispatcher.sendMsgRedirect(req, resp,
						"/venue/view.jsp?venueID=" + venue.getID(), 
							new OkMessage("Venue edited."));
				}
			}else { //insert it
				what = "Inserting venue failed: ";
				builder = new VenueModel.Builder();
				builder.setTitle(title);
				builder.setAddress(address);
				builder.setDescription(description);
				builder.setCreator(UserUtils.getCurrentUser(req, resp));
				builder.setTags(tagsList);
				VenueModel venue = builder.apply();
				MessageDispatcher.sendMsgRedirect(req, resp,
					"/venue/view.jsp?venueID=" + venue.getID(), 
						new OkMessage("Venue added."));
				}
			}
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
