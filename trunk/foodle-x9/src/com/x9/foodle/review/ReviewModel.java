package com.x9.foodle.review;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

import com.x9.foodle.comment.CommentModel;
import com.x9.foodle.datastore.SolrRuntimeException;
import com.x9.foodle.datastore.SolrUtils;
import com.x9.foodle.model.exceptions.InvalidCreatorIDException;
import com.x9.foodle.model.exceptions.InvalidIDException;
import com.x9.foodle.model.exceptions.InvalidSolrModelException;
import com.x9.foodle.model.exceptions.InvalidTextException;
import com.x9.foodle.model.exceptions.InvalidTitleException;
import com.x9.foodle.model.exceptions.InvalidVenueReferenceException;
import com.x9.foodle.user.UserModel;
import com.x9.foodle.util.DateUtils;
import com.x9.foodle.venue.VenueModel;

/*
 * TOOD: constructor and solr stuff 
 */

public class ReviewModel {

	public static final String SOLR_TYPE = "reviewmodel";

	private String id;
	private String title;
	private String text;
	private Date timeAdded;
	private String venueID;
	private int creatorID;
	private int ranking;
	private Date lastUpdated;

	public static ReviewModel getFromSolr(String id) {
		try {
			SolrServer server = SolrUtils.getSolrServer();
			SolrQuery query = new SolrQuery();
			// TODO: make the query safe
			query.setQuery("id:" + id + " AND type:" + SOLR_TYPE);
			QueryResponse rsp = server.query(query);

			SolrDocumentList docs = rsp.getResults();
			if (docs.isEmpty()) {
				// no review with id found
				return null;
			}

			if (docs.size() > 1) {
				throw new SolrRuntimeException(
						"Too many results in ReviewModel.getFromSolr: "
								+ docs.size());
			}

			SolrDocument doc = docs.get(0);

			ReviewModel review = reviewFromSolrDocument(doc);
			return review;

		} catch (SolrServerException e) {
			throw new SolrRuntimeException("solr error in getFromSolr", e);
		}
	}

	/**
	 * @see ReviewModel#getFromSolrForVenue(String, int)
	 * @param venue
	 * @param maxReturned
	 * @return
	 */
	public static List<ReviewModel> getFromSolrForVenue(VenueModel venue,
			int maxReturned) {
		return getFromSolrForVenue(venue.getID(), maxReturned);
	}

	/**
	 * Returns at most {@code maxReturned} {@code ReviewModels} that refer to
	 * the venue with ID {@code venueID}. If no reviews are found, an empty list
	 * is returned.
	 * 
	 * @param venueID
	 *            the venue for which to return reviews
	 * @param maxReturned
	 *            the maximum number of reviews to be returned
	 * @return the list of venues, never null (might be empty)
	 */
	public static List<ReviewModel> getFromSolrForVenue(String venueID,
			int maxReturned) {
		try {
			SolrServer server = SolrUtils.getSolrServer();
			SolrQuery query = new SolrQuery();

			// TODO: make the query safe
			query.setQuery("reference:" + venueID + " AND type:" + SOLR_TYPE);
			query.setRows(maxReturned); // TODO: ?
			QueryResponse rsp = server.query(query);

			SolrDocumentList docs = rsp.getResults();
			if (docs.isEmpty()) {
				// no reviews found
				return new ArrayList<ReviewModel>();
			}

			List<ReviewModel> list = new ArrayList<ReviewModel>();
			for (SolrDocument doc : docs) {
				list.add(reviewFromSolrDocument(doc));
			}

			return list;

		} catch (SolrServerException e) {
			throw new SolrRuntimeException("solr error in getFromSolrForVenue",
					e);
		}
	}

	public String getID() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getText() {
		return text;
	}

	public Date getTimeAdded() {
		return timeAdded;
	}

	public String getVenueID() {
		return venueID;
	}

	public int getCreatorID() {
		return creatorID;
	}
	
	public UserModel getCreator() {
		UserModel user = UserModel.getFromDbByID(creatorID);
		if (user == null) {
			throw new RuntimeException("null creator for review: " + id);
		}
		return user;
	}

	public int getRanking() {
		return ranking;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	/**
	 * @see CommentModel#getFromSolrForReview(ReviewModel, int)
	 * @param maxReturned
	 * @return
	 */
	public List<CommentModel> getComments(int maxReturned) {
		return CommentModel.getFromSolrForReview(this, maxReturned);
	}

	public Builder getEditable() {
		return new Builder(this);
	}

	public static class Builder {

		private ReviewModel review;
		private ReviewModel editMe;

		public Builder() {
			this.review = new ReviewModel();
		}

		private Builder(ReviewModel editMe) {
			this.review = new ReviewModel(editMe);
			this.editMe = editMe;
		}

		public void setTitle(String title) {
			review.title = title;
		}

		public void setText(String text) {
			review.text = text;
		}

		public void setVenueID(String venueID) {
			review.venueID = venueID;
		}

		public void setCreator(UserModel user) {
			review.creatorID = user.getID();
		}

		public void setRanking(int ranking) {
			review.ranking = ranking;
		}

		/**
		 * Will throw a subclass of {@link InvalidSolrModelException} if any of
		 * the parameters for this review is invalid.
		 * 
		 * @throws InvalidIDException
		 * @throws InvalidTitleException
		 * @throws InvalidTextException
		 * @throws InvalidVenueReferenceException
		 * @throws InvalidCreatorIDException
		 */
		public void validate() throws InvalidIDException,
				InvalidTitleException, InvalidTextException,
				InvalidVenueReferenceException, InvalidCreatorIDException {
			Validator.validate(review);
		}

		/**
		 * Calls {@code validate()} to check the parameters, and upon success
		 * inserts them as a review in Solr. If the builder was created using
		 * the default constructor, a new review is inserted. If it was created
		 * using the {@link ReviewModel#getEditable()} method, that review will
		 * be updated, and all references to that object will be updated.
		 * 
		 * Will throw subclasses of {@link InvalidSolrModelException} if any
		 * parameter is invalid.
		 * 
		 * A review model will be returned if no exceptions are thrown.
		 * 
		 * @return the inserted or edited review model, never null
		 * @throws InvalidIDException
		 * @throws InvalidTitleException
		 * @throws InvalidTextException
		 * @throws InvalidVenueReferenceException
		 * @throws InvalidCreatorIDException
		 */
		public ReviewModel apply() throws InvalidIDException,
				InvalidTitleException, InvalidTextException,
				InvalidVenueReferenceException, InvalidCreatorIDException {
			if (editMe == null) {
				// we are creating a new review
				review.id = SolrUtils.generateUniqueID();
				review.timeAdded = DateUtils.getNowUTC();
			}
			review.lastUpdated = DateUtils.getNowUTC();

			// throws on bad data
			validate();

			try {
				SolrServer server = SolrUtils.getSolrServer();
				SolrInputDocument doc = new SolrInputDocument();

				doc.addField("id", review.id);
				doc.addField("type", SOLR_TYPE);
				doc.addField("title", review.title);
				doc.addField("description", review.text);
				doc.addField("timeAdded", DateUtils
						.dateToSolrDate(review.timeAdded));
				doc.addField("reference", review.venueID);
				doc.addField("creator", review.creatorID);
				doc.addField("ranking", review.ranking);
				doc.addField("lastModified", DateUtils
						.dateToSolrDate(review.lastUpdated));

				server.add(doc);
				server.commit();

				if (editMe != null) {
					copy(editMe, review);
					return editMe;
				} else {
					return review;
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
		 * Validates {@code review}, throws exception on invalidity.
		 * 
		 * @param review
		 * @throws InvalidIDException
		 * @throws InvalidTitleException
		 * @throws InvalidTextException
		 * @throws InvalidVenueReferenceException
		 * @throws InvalidCreatorIDException
		 */
		public static void validate(ReviewModel review)
				throws InvalidIDException, InvalidTitleException,
				InvalidTextException, InvalidVenueReferenceException,
				InvalidCreatorIDException {
			validateID(review);
			validateTitle(review);
			validateText(review);
			validateVenueReference(review);
			validateCreator(review);
		}

		public static void validateID(ReviewModel review)
				throws InvalidIDException {
			if (review.id == null) {
				throw new InvalidIDException("review id is null");
			}

		}

		/**
		 * Validates the venue's title. Will trim the title.
		 * 
		 * @param review
		 *            the review which's title should be validated.
		 * @throws InvalidTitleException
		 *             on invalid title
		 */
		public static void validateTitle(ReviewModel review)
				throws InvalidTitleException {
			if (review.title == null) {
				throw new InvalidTitleException("review title is null");
			}
			review.title = review.title.trim();
			if (review.title.isEmpty()) {
				throw new InvalidTitleException("review title is empty");
			}
		}

		/**
		 * Validates the reviews's text body. Will trim the text
		 * 
		 * @param review
		 *            the review which's text should be validated
		 * @throws InvalidTextException
		 *             on invalid text
		 */
		public static void validateText(ReviewModel review)
				throws InvalidTextException {
			if (review.text == null) {
				throw new InvalidTextException("review text is null");
			}
			review.text = review.text.trim();
			if (review.text.isEmpty()) {
				throw new InvalidTextException("review text is empty");
			}
		}

		public static void validateVenueReference(ReviewModel review)
				throws InvalidVenueReferenceException {
			if (review.venueID == null) {
				throw new InvalidVenueReferenceException(
						"venue reference is null");
			}
		}

		/**
		 * Validates the review's creator id. Does not check that it actually
		 * exists in the MySQL database.
		 * 
		 * @param review
		 *            the review which's creator id should be validated
		 * @throws InvalidCreatorIDException
		 *             on invalid creator id
		 */
		public static void validateCreator(ReviewModel review)
				throws InvalidCreatorIDException {
			if (review.creatorID <= 0) {
				throw new InvalidCreatorIDException(
						"review creator id is negative or zero: "
								+ review.creatorID);
			}
		}
	}

	/**
	 * Private constructor, use {@link Builder} create new reviews.
	 */
	private ReviewModel() {
		id = null;
		title = null;
		text = null;
		timeAdded = null;
		venueID = null;
		creatorID = -1;
		ranking = 0;
		lastUpdated = null;
	}

	/**
	 * Private copy constructor.
	 * 
	 * @param copyMe
	 *            the user to be copied.
	 */
	private ReviewModel(ReviewModel copyMe) {
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
	private static void copy(ReviewModel dest, ReviewModel src) {
		dest.id = src.id;
		dest.title = src.title;
		dest.text = src.text;
		dest.timeAdded = src.timeAdded;
		dest.venueID = src.venueID;
		dest.creatorID = src.creatorID;
		dest.ranking = src.ranking;
		dest.lastUpdated = src.lastUpdated;
	}

	public static ReviewModel reviewFromSolrDocument(SolrDocument doc) {
		ReviewModel review = new ReviewModel();
		review.id = (String) doc.get("id");
		review.title = (String) doc.get("title");
		review.text = (String) doc.get("description");
		review.timeAdded = (Date) doc.get("timeAdded");
		review.venueID = (String) doc.get("reference");
		review.creatorID = (Integer) doc.get("creator");
		review.ranking = (Integer) doc.get("ranking");
		review.lastUpdated = (Date) doc.get("lastModified");
		return review;
	}

	/**
	 * For debug purposes only.
	 */
	@Override
	public String toString() {
		return "ReviewModel [id=" + id + ", title=" + title + ", text=" + text
				+ ", venueID=" + venueID + ", creatorID=" + creatorID
				+ ", timeAdded=" + timeAdded + ", lastUpdated=" + lastUpdated
				+ ", ranking=" + ranking + "]";
	}

}
