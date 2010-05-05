package com.x9.foodle.venue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

import com.x9.foodle.datastore.ModelList;
import com.x9.foodle.datastore.SolrRuntimeException;
import com.x9.foodle.datastore.SolrUtils;
import com.x9.foodle.datastore.SortField;
import com.x9.foodle.datastore.SortField.Order;
import com.x9.foodle.model.exceptions.InvalidAddressException;
import com.x9.foodle.model.exceptions.InvalidAverageRatingException;
import com.x9.foodle.model.exceptions.InvalidCreatorIDException;
import com.x9.foodle.model.exceptions.InvalidDescriptionException;
import com.x9.foodle.model.exceptions.InvalidIDException;
import com.x9.foodle.model.exceptions.InvalidNumberOfRatingsException;
import com.x9.foodle.model.exceptions.InvalidSolrModelException;
import com.x9.foodle.model.exceptions.InvalidTitleException;
import com.x9.foodle.review.ReviewModel;
import com.x9.foodle.user.UserModel;
import com.x9.foodle.util.DateUtils;

public class VenueModel {

	public static final String SOLR_TYPE = "venuemodel";

	public static enum SortableField {
		// uses solr field title_sortable because you can't sort on title
		TITLE("title_sortable"), // 
		TIME_ADDED("timeAdded"), // 
		AVERAGE_RATING("averageRating"), //
		NUMBER_OF_RATINGS("numberOfRatings"), //
		CREATOR_ID("creator"), //
		LAST_UPDATED("lastModified"); //

		final String field;

		private SortableField(String field) {
			this.field = field;
		}
	}

	public static SortField<SortableField> sf(SortableField field, Order order) {
		return new SortField<SortableField>(field, order);
	}

	private String id;
	private String title;
	private String address;
	private String description;
	// private List<String> photos;
	private ArrayList<String> tags;

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
			throw new SolrRuntimeException("solr error in getFromSolr", e);
		}
	}

	public static ModelList<VenueModel> getFromSolrCreatedBy(UserModel user,
			int offset, int maxReturned, SortField<SortableField> sort) {
		try {
			SolrServer server = SolrUtils.getSolrServer();
			SolrQuery query = new SolrQuery();

			// TODO: make the query safe
			query
					.setQuery("type:" + SOLR_TYPE + " AND creator:"
							+ user.getID());
			query.setStart(offset);
			query.setRows(maxReturned);
			query.setSortField(sort.field.field, sort.order.order);
			QueryResponse rsp = server.query(query);

			SolrDocumentList results = rsp.getResults();
			if (results.isEmpty()) {
				// no venues found
				return new ModelList<VenueModel>();
			}

			List<VenueModel> list = new ArrayList<VenueModel>();
			for (SolrDocument doc : results) {
				list.add(venueFromSolrDocument(doc));
			}

			return new ModelList<VenueModel>(list, results.getStart(), list
					.size(), results.getNumFound());

		} catch (SolrServerException e) {
			throw new SolrRuntimeException(
					"solr error in getFromSolrCreatedBy", e);
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
		UserModel user = UserModel.getFromDbByID(creatorID);
		if (user == null) {
			throw new RuntimeException("null creator for venue: " + id);
		}
		return user;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	/**
	 * 
	 * @return never null
	 */
	public ArrayList<String> getTags() {
		return this.tags;
	}

	public ModelList<ReviewModel> getReviews(int offset, int maxReturned,
			SortField<ReviewModel.SortableField> sort) {
		return ReviewModel.getFromSolrForVenue(this, offset, maxReturned, sort);
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
			venue.creatorID = user.getID();
		}

		public void setTags(ArrayList<String> tags) {
			venue.tags = tags;
		}

		/**
		 * Will throw a subclass of {@link InvalidSolrModelException} if any of
		 * the parameters for this venue is invalid.
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
		 * inserts them as a venue in Solr. If the builder was created using the
		 * default constructor, a new venue is inserted. If it was created using
		 * the {@link VenueModel#getEditable()} method, that venue will be
		 * updated, and all references to that object will be updated.
		 * 
		 * Will throw subclasses of {@link InvalidSolrModelException} if any
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
				// we are creating a new venue
				venue.id = SolrUtils.generateUniqueID();
				venue.timeAdded = DateUtils.getNowUTC();
			}
			venue.lastUpdated = DateUtils.getNowUTC();

			// throws on bad data
			validate();

			try {
				SolrServer server = SolrUtils.getSolrServer();
				SolrInputDocument doc = new SolrInputDocument();

				// if (venue.tags != null && venue.tags.size() != 0) {
				// doc.addField("tags", venue.tags.get(0));
				// for (int i = 1; i < venue.tags.size(); i++) {
				// doc.addField("tags", venue.tags.get(i));
				// }
				// }

				for (String tag : venue.tags) {
					doc.addField("tags", tag);
				}

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
				throw new InvalidIDException("Internal error (no id)");
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
				throw new InvalidTitleException("Empty title");
			}
			venue.title = venue.title.trim();
			if (venue.title.isEmpty()) {
				throw new InvalidTitleException("Empty title");
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
				throw new InvalidAddressException("Empty address");
			}
			venue.address = venue.address.trim();
			if (venue.address.isEmpty()) {
				throw new InvalidAddressException("Empty address");
			}
		}

		/**
		 * Validates the venue's description. Will trim the description.
		 * 
		 * @param venue
		 *            the venue which's description should be validated
		 * @throws InvalidDescriptionException
		 *             on invalid description
		 */
		public static void validateDescription(VenueModel venue)
				throws InvalidDescriptionException {
			if (venue.description == null) {
				throw new InvalidDescriptionException("Empty description");
			}
			venue.description = venue.description.trim();
			if (venue.description.isEmpty()) {
				throw new InvalidDescriptionException("Empty description");
			}
		}

		public static void validateNumberOfRatings(VenueModel venue)
				throws InvalidNumberOfRatingsException {
			if (venue.numberOfRatings < 0) {
				throw new InvalidNumberOfRatingsException(
						"Internal error (venue number of ratings is negative: "
								+ venue.numberOfRatings + ")");
			}
			if (venue.averageRating != 0.0f && venue.numberOfRatings == 0) {
				throw new InvalidNumberOfRatingsException(
						"Internal error (venue number of ratings zero while still having a average rating: "
								+ venue.averageRating + ")");
			}
		}

		public static void validateAverageRating(VenueModel venue)
				throws InvalidAverageRatingException {
			if (venue.averageRating < 0.0f) {
				throw new InvalidAverageRatingException(
						"Internal error (venue average rating is negative: "
								+ venue.averageRating + ")");
			}
			if (venue.averageRating > 5.0f) {
				throw new InvalidAverageRatingException(
						"Internal error (venue average rating to large: "
								+ venue.averageRating + ")");
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
						"Internal error (venue creator id is negative or zero: "
								+ venue.creatorID + ")");
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

		this.tags = new ArrayList<String>();
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

		dest.tags = new ArrayList<String>();
		dest.tags.addAll(src.tags);
	}

	public static VenueModel venueFromSolrDocument(SolrDocument doc) {
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

		venue.tags = uglyAsHell(doc);

		return venue;
	}

	@SuppressWarnings("unchecked")
	private static ArrayList<String> uglyAsHell(SolrDocument doc) {
		ArrayList<String> tags = new ArrayList<String>();
		Collection c = (Collection) doc.getFieldValues("tags");
		if (c != null) {
			tags.addAll(c);
		}
		return tags;
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
