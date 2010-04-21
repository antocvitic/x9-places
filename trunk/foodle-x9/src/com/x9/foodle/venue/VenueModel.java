package com.x9.foodle.venue;

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
import com.x9.foodle.venue.exceptions.InvalidAddressException;
import com.x9.foodle.venue.exceptions.InvalidAverageRatingException;
import com.x9.foodle.venue.exceptions.InvalidCreatorIDException;
import com.x9.foodle.venue.exceptions.InvalidDescriptionException;
import com.x9.foodle.venue.exceptions.InvalidIDException;
import com.x9.foodle.venue.exceptions.InvalidNumberOfRatingsException;
import com.x9.foodle.venue.exceptions.InvalidTitleException;
import com.x9.foodle.venue.exceptions.InvalidVenueException;

public class VenueModel {

	public static final String SOLR_TYPE = "venuemodel";

	private String id; //It is an "int" in the ADD
	private String title;
	private String address;
	private String description;
	// private List<String> photos;

	private int numberOfRatings;
	private double averageRating;

	private boolean closed;

	private Date timeAdded;
	private int creatorID;

	private Date lastUpdated;

	public static VenueModel getFromSolr(String id) {
		try {
			SolrServer server = SolrUtils.getSolrServer();
			SolrQuery query = new SolrQuery();
			// TODO: make the query safe
			query.setQuery("id:" + id + " AND type:" + SOLR_TYPE);
			QueryResponse rsp = server.query(query);

			SolrDocumentList docs = rsp.getResults();
			if (docs.isEmpty()) {
				// no venue with id found
				return null;
			}

			if (docs.size() > 1) {
				throw new SolrRuntimeException(
						"Too many results in VenueModel.getFromSolr: "
								+ docs.size());
			}

			SolrDocument doc = docs.get(0);

			VenueModel venue = venueFromSolrDocument(doc);
			return venue;

		} catch (SolrServerException e) {
			throw new SolrRuntimeException("Solr Server Exception", e);
		}
	}

	public String getID() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getAddress() {
		return address;
	}

	public String getDescription() {
		return description;
	}

	public int getNumberOfRatings() {
		return numberOfRatings;
	}

	public double getAverageRating() {
		return averageRating;
	}

	public boolean isClosed() {
		return closed;
	}

	public Date getTimeAdded() {
		return timeAdded;
	}

	public int getCreatorID() {
		return creatorID;
	}

	public UserModel getCreator() {
		return UserModel.getFromDbByID(creatorID);
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public Builder getEditable() {
		return new Builder(this);
	}

	public static class Builder {

		private VenueModel venue;
		private VenueModel editMe;

		public Builder() {
			this.venue = new VenueModel();
		}

		private Builder(VenueModel editMe) {
			this.venue = new VenueModel(editMe);
			this.editMe = editMe;
		}

		public void setId(String id) {
			venue.id = id;
		}

		public void setTitle(String title) {
			venue.title = title;
		}

		public void setAddress(String address) {
			venue.address = address;
		}

		public void setDescription(String description) {
			venue.description = description;
		}

		public void setNumberOfRatings(int numberOfRatings) {
			venue.numberOfRatings = numberOfRatings;
		}

		public void setAverageRating(double averageRating) {
			venue.averageRating = averageRating;
		}

		public void setClosed(boolean closed) {
			venue.closed = closed;
		}

		public void setCreator(UserModel user) {
			venue.creatorID = user.getUserID();
		}

		/**
		 * Will throw a subclass of {@link InvalidVenueException} if any of the
		 * parameters for this venue is invalid.
		 * 
		 * @throws InvalidIDException
		 * @throws InvalidTitleException
		 * @throws InvalidAddressException
		 * @throws InvalidDescriptionException
		 * @throws InvalidNumberOfRatingsException
		 * @throws InvalidAverageRatingException
		 * @throws InvalidCreatorIDException
		 */
		public void validate() throws InvalidIDException,
				InvalidTitleException, InvalidAddressException,
				InvalidDescriptionException, InvalidNumberOfRatingsException,
				InvalidAverageRatingException, InvalidCreatorIDException {
			Validator.validate(venue);
		}

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
		public VenueModel apply() throws InvalidIDException,
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
		}
	}

	public static class Validator {

		/**
		 * Validates {@code venue}, throws exception on invalidity.
		 * 
		 * @param venue
		 * @throws InvalidIDException
		 * @throws InvalidTitleException
		 * @throws InvalidAddressException
		 * @throws InvalidDescriptionException
		 * @throws InvalidNumberOfRatingsException
		 * @throws InvalidAverageRatingException
		 * @throws InvalidCreatorIDException
		 */
		public static void validate(VenueModel venue)
				throws InvalidIDException, InvalidTitleException,
				InvalidAddressException, InvalidDescriptionException,
				InvalidNumberOfRatingsException, InvalidAverageRatingException,
				InvalidCreatorIDException {
			validateID(venue);
			validateTitle(venue);
			validateAddress(venue);
			validateDescription(venue);
			validateNumberOfRatings(venue);
			validateAverageRating(venue);
			validateCreatorID(venue);
		}

		public static void validateID(VenueModel venue)
				throws InvalidIDException {
			if (venue.id == null) {
				throw new InvalidIDException("venue id is null");
			}
		}

		/**
		 * Validates the venue's title. Will trim the title.
		 * 
		 * @param venue
		 *            the venue which's title should be validated.
		 * @throws InvalidTitleException
		 *             on invalid title
		 */
		public static void validateTitle(VenueModel venue)
				throws InvalidTitleException {
			if (venue.title == null) {
				throw new InvalidTitleException("venue title is null");
			}
			venue.title = venue.title.trim();
			if (venue.title.isEmpty()) {
				throw new InvalidTitleException("venue title is empty");
			}
		}

		/**
		 * Validates the venue's address. Will trim the address.
		 * 
		 * @param venue
		 *            the venue which's address should be validated
		 * @throws InvalidAddressException
		 *             on invalid address
		 */
		public static void validateAddress(VenueModel venue)
				throws InvalidAddressException {
			if (venue.address == null) {
				throw new InvalidAddressException("venue address is null");
			}
			venue.address = venue.address.trim();
			if (venue.address.isEmpty()) {
				throw new InvalidAddressException("venue address is empty");
			}
		}

		/**
		 * Validates the venue's description. Will trim the description
		 * 
		 * @param venue
		 *            the venue which's description should be validated
		 * @throws InvalidDescriptionException
		 *             on invalid description
		 */
		public static void validateDescription(VenueModel venue)
				throws InvalidDescriptionException {
			if (venue.description == null) {
				throw new InvalidDescriptionException(
						"venue description is null");
			}
			venue.description = venue.description.trim();
			if (venue.description.isEmpty()) {
				throw new InvalidDescriptionException(
						"venue description is empty");
			}
		}

		public static void validateNumberOfRatings(VenueModel venue)
				throws InvalidNumberOfRatingsException {
			if (venue.numberOfRatings < 0) {
				throw new InvalidNumberOfRatingsException(
						"venue number of ratings is negative: "
								+ venue.numberOfRatings);
			}
			if (venue.averageRating != 0.0f && venue.numberOfRatings == 0) {
				throw new InvalidNumberOfRatingsException(
						"venue number of ratings zero while still having a average rating: "
								+ venue.averageRating);
			}
		}

		public static void validateAverageRating(VenueModel venue)
				throws InvalidAverageRatingException {
			if (venue.averageRating < 0.0f) {
				throw new InvalidAverageRatingException(
						"venue average rating is negative: "
								+ venue.averageRating);
			}
			if (venue.averageRating > 5.0f) {
				throw new InvalidAverageRatingException(
						"venue average rating to large: " + venue.averageRating);
			}
		}

		/**
		 * Validates the venue's creator id. Does not check that it actually
		 * exists in the MySQL database.
		 * 
		 * @param venue
		 *            the venue which's creator id should be validated
		 * @throws InvalidCreatorIDException
		 *             on invalid creator id
		 */
		public static void validateCreatorID(VenueModel venue)
				throws InvalidCreatorIDException {
			if (venue.creatorID <= 0) {
				throw new InvalidCreatorIDException(
						"venue creator id is negative or zero: "
								+ venue.creatorID);
			}
		}
	}

	/**
	 * Private constructor, use {@link Builder} create new venues.
	 */
	private VenueModel() {
		this.id = null;
		this.title = null;
		this.address = "";
		this.description = "";
		// this.photos = null;

		this.numberOfRatings = 0;
		this.averageRating = 0.0f;

		this.closed = false;

		this.timeAdded = null;
		this.creatorID = -1;

		this.lastUpdated = null;
	}

	/**
	 * Private copy constructor.
	 * 
	 * @param copyMe
	 *            the user to be copied.
	 */
	private VenueModel(VenueModel copyMe) {
		this();
		copy(this, copyMe);
	}

	/**
	 * Copies all the fields of src into dest.
	 * 
	 * @param dest
	 *            the destination of the copy operation.
	 * @param src
	 *            the source of the copy operation.
	 */
	private static void copy(VenueModel dest, VenueModel src) {
		dest.id = src.id;
		dest.title = src.title;
		dest.address = src.address;
		dest.description = src.description;
		// dest.photos = src.photos;

		dest.numberOfRatings = src.numberOfRatings;
		dest.averageRating = src.averageRating;

		dest.closed = src.closed;

		dest.timeAdded = src.timeAdded;
		dest.creatorID = src.creatorID;

		dest.lastUpdated = src.lastUpdated;
	}

	private static VenueModel venueFromSolrDocument(SolrDocument doc) {
		VenueModel venue = new VenueModel();
		venue.id = (String) doc.get("id");
		venue.title = (String) doc.get("title");
		venue.address = (String) doc.get("address");
		venue.description = (String) doc.get("description");

		venue.numberOfRatings = (Integer) doc.get("numberOfRatings");
		venue.averageRating = (Float) doc.get("averageRating");

		venue.closed = (Boolean) doc.get("closed");

		venue.timeAdded = (Date) doc.get("timeAdded");
		venue.creatorID = (Integer) doc.get("creator");

		venue.lastUpdated = (Date) doc.get("lastModified");

		return venue;
	}

	/**
	 * For debug purposes only.
	 */
	public String toString() {
		return "VenueModel [id=" + id + ", title=" + title + ", address="
				+ address + ", description=" + description + ", averageRating="
				+ averageRating + ", numberOfRatings=" + numberOfRatings
				+ ", closed=" + closed + ", creatorID=" + creatorID
				+ ", lastUpdated=" + DateUtils.dateToSolrDate(lastUpdated)
				+ ", timeAdded=" + DateUtils.dateToSolrDate(timeAdded) + "]";
	}

}
