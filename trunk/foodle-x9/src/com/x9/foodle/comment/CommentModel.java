package com.x9.foodle.comment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

import com.x9.foodle.datastore.ModelList;
import com.x9.foodle.datastore.Pager;
import com.x9.foodle.datastore.SolrRuntimeException;
import com.x9.foodle.datastore.SolrUtils;
import com.x9.foodle.datastore.SortableFields;
import com.x9.foodle.datastore.SortableFieldsConstraints;
import com.x9.foodle.model.exceptions.InvalidCreatorIDException;
import com.x9.foodle.model.exceptions.InvalidIDException;
import com.x9.foodle.model.exceptions.InvalidReviewReferenceException;
import com.x9.foodle.model.exceptions.InvalidSolrModelException;
import com.x9.foodle.model.exceptions.InvalidTextException;
import com.x9.foodle.review.ReviewModel;
import com.x9.foodle.user.UserModel;
import com.x9.foodle.util.DateUtils;

public class CommentModel {

	public static final String SOLR_TYPE = "commentmodel";

	private String id;
	private String text;
	private Date timeAdded;
	private String reviewID;
	private int creatorID;

	public static CommentModel getFromSolr(String id) {
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
						"Too many results in CommentModel.getFromSolr: "
								+ docs.size());
			}

			SolrDocument doc = docs.get(0);

			CommentModel comment = commentFromSolrDocument(doc);
			return comment;

		} catch (SolrServerException e) {
			throw new SolrRuntimeException("solr error in getFromSolr", e);
		}
	}

	public static ModelList<CommentModel> getFromSolrForReview(
			ReviewModel review, Pager pager) {
		return getFromSolrForReview(review.getID(), pager);
	}

	public static ModelList<CommentModel> getFromSolrForReview(String reviewID,
			Pager pager) {
		try {
			SolrServer server = SolrUtils.getSolrServer();
			SolrQuery query = new SolrQuery();

			// TODO: make the query safe
			query.setQuery("reference:" + reviewID + " AND type:" + SOLR_TYPE);
			pager.apply(query);
			QueryResponse rsp = server.query(query);

			SolrDocumentList results = rsp.getResults();
			if (results.isEmpty()) {
				// no comments found
				return new ModelList<CommentModel>();
			}

			List<CommentModel> list = new ArrayList<CommentModel>();
			for (SolrDocument doc : results) {
				list.add(commentFromSolrDocument(doc));
			}

			return new ModelList<CommentModel>(pager,
					new CommentSortableFields(), list, results.getStart(), list
							.size(), results.getNumFound());

		} catch (SolrServerException e) {
			throw new SolrRuntimeException(
					"solr error in getFromSolrForReview", e);
		}
	}

	public static ModelList<CommentModel> getFromSolrCreatedBy(UserModel user,
			Pager pager) {
		try {
			SolrServer server = SolrUtils.getSolrServer();
			SolrQuery query = new SolrQuery();

			// TODO: make the query safe
			query
					.setQuery("type:" + SOLR_TYPE + " AND creator:"
							+ user.getID());
			pager.apply(query);
			QueryResponse rsp = server.query(query);

			SolrDocumentList results = rsp.getResults();
			if (results.isEmpty()) {
				// no venues found
				return new ModelList<CommentModel>();
			}

			List<CommentModel> list = new ArrayList<CommentModel>();
			for (SolrDocument doc : results) {
				list.add(commentFromSolrDocument(doc));
			}

			return new ModelList<CommentModel>(pager,
					new CommentSortableFields(), list, results.getStart(), list
							.size(), results.getNumFound());

		} catch (SolrServerException e) {
			throw new SolrRuntimeException(
					"solr error in getFromSolrCreatedBy", e);
		}
	}

	public String getID() {
		return id;
	}

	public String getText() {
		return text;
	}

	public Date getTimeAdded() {
		return timeAdded;
	}

	public String getReviewID() {
		return reviewID;
	}

	public int getCreatorID() {
		return creatorID;
	}

	public UserModel getCreator() {
		UserModel user = UserModel.getFromDbByID(creatorID);
		if (user == null) {
			throw new RuntimeException("null creator for comment: " + id);
		}
		return user;
	}

	public Builder getEditable() {
		return new Builder(this);
	}

	public static class Builder {

		private CommentModel comment;
		private CommentModel editMe;

		public Builder() {
			this.comment = new CommentModel();
		}

		public Builder(CommentModel editMe) {
			this.comment = new CommentModel(editMe);
			this.editMe = editMe;
		}

		public void setText(String text) {
			comment.text = text;
		}

		public void setReviewID(String reviewID) {
			comment.reviewID = reviewID;
		}

		public void setCreator(UserModel user) {
			comment.creatorID = user.getID();
		}

		/**
		 * Will throw a subclass of {@link InvalidSolrModelException} if any of
		 * the parameters for this comment is invalid.
		 * 
		 * @throws InvalidIDException
		 * @throws InvalidTextException
		 * @throws InvalidCreatorIDException
		 * @throws InvalidReviewReferenceException
		 */
		public void validate() throws InvalidIDException, InvalidTextException,
				InvalidReviewReferenceException, InvalidCreatorIDException {
			Validator.validate(comment);
		}

		public CommentModel apply() throws InvalidIDException,
				InvalidTextException, InvalidCreatorIDException,
				InvalidReviewReferenceException {
			if (editMe == null) {
				// we are creating a new review
				comment.id = SolrUtils.generateUniqueID();
				comment.timeAdded = DateUtils.getNowUTC();
			}

			// throws on bad data
			validate();

			try {
				SolrServer server = SolrUtils.getSolrServer();
				SolrInputDocument doc = new SolrInputDocument();

				doc.addField("id", comment.id);
				doc.addField("type", SOLR_TYPE);
				doc.addField("description", comment.text);
				doc.addField("timeAdded", DateUtils
						.dateToSolrDate(comment.timeAdded));
				doc.addField("reference", comment.reviewID);
				doc.addField("creator", comment.creatorID);

				server.add(doc);
				server.commit();

				if (editMe != null) {
					copy(editMe, comment);
					return editMe;
				} else {
					return comment;
				}
			} catch (SolrServerException e) {
				throw new SolrRuntimeException("Solr Server Exception", e);
			} catch (IOException e) {
				throw new SolrRuntimeException("Solr IOException", e);
			}
		}
	}

	public static class Validator {

		public static final int COMMENT_TEXT_MAX_LENGTH = 256;

		/**
		 * Validates {@code comment}, throws exception on invalidity.
		 * 
		 * @param comment
		 * @throws InvalidIDException
		 * @throws InvalidTextException
		 * @throws InvalidReviewReferenceException
		 * @throws InvalidCreatorIDException
		 */
		public static void validate(CommentModel comment)
				throws InvalidIDException, InvalidTextException,
				InvalidReviewReferenceException, InvalidCreatorIDException {
			validateID(comment);
			validateText(comment);
			validateReviewReference(comment);
			validateCreator(comment);
		}

		public static void validateID(CommentModel comment)
				throws InvalidIDException {
			if (comment.id == null || comment.id.isEmpty()) {
				throw new InvalidIDException("Internal error (empty id)");
			}
		}

		/**
		 * Validates the comment's text body. Will trim the text
		 * 
		 * @param comment
		 *            the comment which's text should be validated
		 * @throws InvalidTextException
		 *             on invalid text
		 */
		public static void validateText(CommentModel comment)
				throws InvalidTextException {
			if (comment.text == null) {
				throw new InvalidTextException("Comment is empty");
			}
			comment.text = comment.text.trim();
			if (comment.text.length() > COMMENT_TEXT_MAX_LENGTH) {
				throw new InvalidTextException("Comment is too long, ("
						+ comment.text.length()
						+ " characters), it has to be less than "
						+ COMMENT_TEXT_MAX_LENGTH + " characters");
			}
			if (comment.text.isEmpty()) {
				throw new InvalidTextException("Comment is empty");
			}
		}

		public static void validateReviewReference(CommentModel comment)
				throws InvalidReviewReferenceException {
			if (comment.reviewID == null) {
				throw new InvalidReviewReferenceException(
						"Internal error (no review reference)");
			}
		}

		/**
		 * Validates the comment's creator id. Does not check that it actually
		 * exists in the MySQL database.
		 * 
		 * @param comment
		 *            the comment which's creator id should be validated
		 * @throws InvalidCreatorIDException
		 *             on invalid creator id
		 */
		public static void validateCreator(CommentModel comment)
				throws InvalidCreatorIDException {
			if (comment.creatorID <= 0) {
				throw new InvalidCreatorIDException(
						"coment creator id is negative or zero: "
								+ comment.creatorID);
			}
		}
	}

	private CommentModel() {
		this.id = null;
		this.text = null;
		this.timeAdded = null;
		this.creatorID = -1;
	}

	private CommentModel(CommentModel copyMe) {
		this();
		copy(this, copyMe);
	}

	private static void copy(CommentModel dest, CommentModel src) {
		dest.id = src.id;
		dest.text = src.text;
		dest.timeAdded = src.timeAdded;
		dest.creatorID = src.creatorID;
	}

	public static CommentModel commentFromSolrDocument(SolrDocument doc) {
		CommentModel comment = new CommentModel();
		comment.id = (String) doc.get("id");
		comment.text = (String) doc.get("description");
		comment.timeAdded = (Date) doc.get("timeAdded");
		comment.reviewID = (String) doc.get("reference");
		comment.creatorID = (Integer) doc.get("creator");
		return comment;
	}

	public static class CommentSortableFields implements
			SortableFieldsConstraints {
		private final static List<SortableFields> sortableFields = new LinkedList<SortableFields>();

		static {
			sortableFields.add(SortableFields.SCORE);
			sortableFields.add(SortableFields.TIME_ADDED);
		}

		@Override
		public List<SortableFields> getApplicableSortableFields() {
			return sortableFields;
		}
	}

	/**
	 * For debug purposes only.
	 */
	@Override
	public String toString() {
		return "CommentModel [id=" + id + ", text=" + text + ", timeAdded="
				+ timeAdded + ", reviewID=" + reviewID + ", creatorID="
				+ creatorID + "]";
	}

}
