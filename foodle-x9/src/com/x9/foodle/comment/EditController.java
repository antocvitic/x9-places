package com.x9.foodle.comment;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.x9.foodle.model.exceptions.InvalidCreatorIDException;
import com.x9.foodle.model.exceptions.InvalidIDException;
import com.x9.foodle.model.exceptions.InvalidReviewReferenceException;
import com.x9.foodle.model.exceptions.InvalidTextException;
import com.x9.foodle.review.ReviewModel;
import com.x9.foodle.user.UserModel;
import com.x9.foodle.user.UserUtils;
import com.x9.foodle.util.MessageDispatcher;
import com.x9.foodle.util.MessageDispatcher.OkMessage;
import com.x9.foodle.util.MessageDispatcher.ErrorMessage;

@SuppressWarnings("serial")
public class EditController extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String commentID = req.getParameter("commentID");
		String text = req.getParameter("text");
		String reviewID = req.getParameter("reviewID");
		text = text == null ? "" : text;
		
		// only used to return on error
		String venueID = req.getParameter("venueID");
		String redirect = req.getParameter("redirect");

		if (reviewID == null) {
			MessageDispatcher.sendMsgRedirect(req, resp,
					"/review/edit.jsp?venueID=" + venueID, new ErrorMessage("Oops"));
		}
		try {
			CommentModel.Builder builder = null;
			if (commentID != null && !commentID.isEmpty()) {
				// edit existing comment
				CommentModel tempComment = CommentModel.getFromSolr(commentID);
				if (tempComment == null) {
					throw new RuntimeException("no comment with id: "
							+ commentID);
				}
				builder = tempComment.getEditable();
			} else {
				// create new comment
				builder = new CommentModel.Builder();
			}

			builder.setText(text);
			builder.setReviewID(reviewID);
			builder.setCreator(UserUtils.getCurrentUser(req, resp));
			builder.apply();
			
			//Increase the Reviewers repLevel
			ReviewModel rev = ReviewModel.getFromSolr(reviewID);
			if (rev.getCreatorID() != UserUtils.getCurrentUser(req, resp).getID()) {
				UserModel user = UserModel.getFromDbByID(rev.getCreatorID());
				if (user != null && user.getUsername() != null)
					user.applyReplevel(user.getReputationLevel() + 10);
			}
			
			MessageDispatcher.sendMsgRedirectAbsolute(req, resp, redirect,
					new OkMessage("Comment inserted successfully."));
			return;
		} catch (InvalidIDException e) {
			throw new RuntimeException("comment with invalid id?", e);
		} catch (InvalidTextException e) {
			MessageDispatcher.sendMsgRedirect(req, resp,
					"/venue/view.jsp?venueID=" + venueID, e
							.toMessage("Comment insertion failed: "));
			return;
		} catch (InvalidCreatorIDException e) {
			throw new RuntimeException("comment with invalid creator?", e);
		} catch (InvalidReviewReferenceException e) {
			throw new RuntimeException(
					"comment with invalid review reference?", e);
		}
	}

}
