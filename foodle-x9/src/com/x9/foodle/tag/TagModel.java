package com.x9.foodle.tag;

import java.io.IOException;
import java.util.Date;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

import com.x9.foodle.datastore.SolrRuntimeException;
import com.x9.foodle.datastore.SolrUtils;
import com.x9.foodle.user.UserModel;
import com.x9.foodle.util.DateUtils;
import com.x9.foodle.venue.VenueModel;
import com.x9.foodle.venue.VenueModel.Validator;
import com.x9.foodle.venue.exceptions.InvalidAddressException;
import com.x9.foodle.venue.exceptions.InvalidAverageRatingException;
import com.x9.foodle.venue.exceptions.InvalidCreatorIDException;
import com.x9.foodle.venue.exceptions.InvalidDescriptionException;
import com.x9.foodle.venue.exceptions.InvalidIDException;
import com.x9.foodle.venue.exceptions.InvalidNumberOfRatingsException;
import com.x9.foodle.venue.exceptions.InvalidTitleException;
import com.x9.foodle.venue.exceptions.InvalidVenueException;

public class TagModel {

	public static final String SOLR_TYPE = "tagmodel";

	private String venueID;
	private int userID;
	private String tagText;

	public TagModel(){
		venueID = null;
		userID = -1;
		tagText = null;
	}
	
	private TagModel(TagModel copyMe) {
		this();
		copy(this, copyMe);
	}

	public TagModel getFromSolr(String venueID){
		try {
			SolrServer server = SolrUtils.getSolrServer();
			SolrQuery query = new SolrQuery();
			// TODO: make the query safe
			query.setQuery("venueID:" + venueID + " AND type:" + SOLR_TYPE);
			QueryResponse rsp = server.query(query);
			SolrDocumentList docs = rsp.getResults();
			if (docs.isEmpty()) {
				// no tag corresponding to the venueID found
				return null;
			}

			/*if (docs.size() > 1) {
				throw new SolrRuntimeException(
						"Too many results in TagModel.getFromSolr: "
						+ docs.size());
			}*/

			SolrDocument doc = docs.get(0);

			TagModel tag = tagFromSolrDocument(doc); //Should it return a list of tags ?
			return tag;

		} catch (SolrServerException e) {
			throw new SolrRuntimeException("Solr Server Exception", e);
		}		
	}
	
	//TODO
	public void createNew(String venueID){
		
	}
	
	//TODO
	private TagModel tagFromSolrDocument(SolrDocument doc) {
		return null;
	}
	
	//TODO
	public static class Builder {
		
		private TagModel tag;
		private TagModel editMe;
		
		public Builder() {
			this.tag = new TagModel();
		}
		
		private Builder(TagModel editMe) {
			this.tag = new TagModel(editMe);
			this.editMe = editMe;
		}

		
		
		
		/**
		 * Will throw a subclass of {@link InvalidVenueException} if any of the
		 * parameters for this venue is invalid.
		 * 
		 * @throws InvalidUserIDException
		 * @throws InvalidVenueIDException
		 * @throws InvalidTagTextException
		 */
		
		/*public void validate() throws InvalidUserIDException, 
		 InvalidVenueIDException, 
		 InvalidTagTextException {
			
			Validator.validate(tag);
		}*/

		/**
		 * Calls {@code validate()} to check the parameters, and upon success
		 * inserts them as a venu in Solr. If the builder was created using the
		 * default constructor, a new venue is inserted. If it was created using
		 * the {@link VenueModel#getEditable()} method, that venue will be
		 * updated, and all references to that object will be updated.
		 * 
		 * Will throw subclasses of {@link InvalidVenueException} if any
		 * parameter is invalid.
		 * 
		 * A venue model will be returned if no exceptions are thrown.
		 * 
		 * @return the inserted or edited venue model, never null
		 * @throws InvalidIDException
		 * @throws InvalidTitleException
		 * @throws InvalidAddressException
		 * @throws InvalidDescriptionException
		 * @throws InvalidNumberOfRatingsException
		 * @throws InvalidAverageRatingException
		 * @throws InvalidCreatorIDException
		 * @throws SolrRuntimeException
		 */
		/*public VenueModel apply() throws InvalidIDException,
				InvalidTitleException, InvalidAddressException,
				InvalidDescriptionException, InvalidNumberOfRatingsException,
				InvalidAverageRatingException, InvalidCreatorIDException,
				SolrRuntimeException {
			if (editMe == null) {
				venue.timeAdded = DateUtils.getNowUTC();
			}
			venue.lastUpdated = DateUtils.getNowUTC();

			// throws on bad data
			validate();

			try {
				SolrServer server = SolrUtils.getSolrServer();
				SolrInputDocument doc = new SolrInputDocument();

				doc.addField("id", venue.id);
				doc.addField("type", SOLR_TYPE);
				doc.addField("title", venue.title);
				doc.addField("address", venue.address);
				doc.addField("description", venue.description);
				// doc.addField("photos", venue.photos);
				doc.addField("numberOfRatings", venue.numberOfRatings);
				doc.addField("averageRating", venue.averageRating);
				doc.addField("closed", venue.closed);
				doc.addField("timeAdded", DateUtils
						.dateToSolrDate(venue.timeAdded));
				doc.addField("creator", venue.creatorID);
				doc.addField("lastModified", DateUtils
						.dateToSolrDate(venue.lastUpdated));

				server.add(doc);
				server.commit();

				if (editMe != null) {
					copy(editMe, venue);
					return editMe;
				} else {
					return venue;
				}

			} catch (SolrServerException e) {
				throw new SolrRuntimeException("Solr Server Exception", e);
			} catch (IOException e) {
				throw new SolrRuntimeException("Solr IOException", e);
			}
		}*/

		
		

	}
	
	//TODO
	public static class Validator {
		
	}

	public String getVenueID() {
		return venueID;
	}

	public int getUserID() {
		return userID;
	}

	public String getTagText() {
		return tagText;
	}

	public static String getSolrType() {
		return SOLR_TYPE;
	}

	private void copy(TagModel tagModel, TagModel copyMe) {
		tagModel.tagText = copyMe.tagText;
		tagModel.userID = copyMe.userID;
		tagModel.venueID = copyMe.venueID;
		
	}

	public String toString() {
		return "TagModel [venueID=" + venueID + "," +
		"tagText=" + tagText + ", userID="
		+ userID+"]";
	}



}
