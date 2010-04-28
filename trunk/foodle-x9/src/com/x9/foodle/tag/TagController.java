package com.x9.foodle.tag;

import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.x9.foodle.datastore.SolrRuntimeException;
import com.x9.foodle.user.UserModel;
import com.x9.foodle.venue.VenueModel;
import com.x9.foodle.venue.exceptions.InvalidAddressException;
import com.x9.foodle.venue.exceptions.InvalidAverageRatingException;
import com.x9.foodle.venue.exceptions.InvalidCreatorIDException;
import com.x9.foodle.venue.exceptions.InvalidDescriptionException;
import com.x9.foodle.venue.exceptions.InvalidIDException;
import com.x9.foodle.venue.exceptions.InvalidNumberOfRatingsException;
import com.x9.foodle.venue.exceptions.InvalidTitleException;

@SuppressWarnings("serial")
public class TagController extends HttpServlet {

	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String id = req.getParameter("id");
		String title = req.getParameter("title");
		String address = req.getParameter("address");
		String description = req.getParameter("description");
		
		StringTokenizer tags = new StringTokenizer(req.getParameter("tags"));
		Vector<String> tagsVector = new Vector<String>();
		while(tags.hasMoreElements()){
			tagsVector.add(tags.nextToken());
		}
		
		String redirect = req.getParameter("redirect");

		try {
			VenueModel.Builder builder = new VenueModel.Builder();

			builder.setId(id);
			builder.setTitle(title);
			builder.setAddress(address);
			builder.setDescription(description);
			builder.setCreator(UserModel.getFromDbByID(1));
			builder.setTags(tagsVector);
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
