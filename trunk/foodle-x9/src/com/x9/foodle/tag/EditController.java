package com.x9.foodle.tag;

import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.x9.foodle.datastore.SolrRuntimeException;
import com.x9.foodle.model.exceptions.InvalidAddressException;
import com.x9.foodle.model.exceptions.InvalidAverageRatingException;
import com.x9.foodle.model.exceptions.InvalidCreatorIDException;
import com.x9.foodle.model.exceptions.InvalidDescriptionException;
import com.x9.foodle.model.exceptions.InvalidIDException;
import com.x9.foodle.model.exceptions.InvalidNumberOfRatingsException;
import com.x9.foodle.model.exceptions.InvalidTitleException;
import com.x9.foodle.user.UserModel;
import com.x9.foodle.venue.VenueModel;

/**
 * This is a copy of venue.EditController.java in which I added
 * some code lines to deal with tags entry
 * @author Rémi
 *
 */

@SuppressWarnings("serial")
public class EditController extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String id = req.getParameter("id");
		String title = req.getParameter("title");
		String address = req.getParameter("address");
		String description = req.getParameter("description");
		
		//StringTokenizer that seperates the tags input fields into
		//separate tags.
		StringTokenizer tags = new StringTokenizer(req.getParameter("tags"));
		ArrayList<String> tagsList = new ArrayList<String>();
		while(tags.hasMoreElements()){
			tagsList.add(tags.nextToken());
		}
		
		String redirect = req.getParameter("redirect");

		try {
			VenueModel.Builder builder = new VenueModel.Builder();

			builder.setId(id);
			builder.setTitle(title);
			builder.setAddress(address);
			builder.setDescription(description);
			builder.setCreator(UserModel.getFromDbByID(1));
			builder.setTags(tagsList);
			builder.apply();

			resp.sendRedirect(redirect);
		} catch (InvalidIDException e) {
			throw new RuntimeException(e);
		} catch (InvalidTitleException e) {
			throw new RuntimeException(e);
		} catch (InvalidAddressException e) {
			throw new RuntimeException(e);
		} catch (InvalidDescriptionException e) {
			throw new RuntimeException(e);
		} catch (InvalidNumberOfRatingsException e) {
			throw new RuntimeException(e);
		} catch (InvalidAverageRatingException e) {
			throw new RuntimeException(e);
		} catch (InvalidCreatorIDException e) {
			throw new RuntimeException(e);
		} catch (SolrRuntimeException e) {
			throw new RuntimeException(e);
		}
	}

}
