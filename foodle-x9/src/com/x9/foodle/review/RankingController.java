package com.x9.foodle.review;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.x9.foodle.datastore.DBUtils;
import com.x9.foodle.model.exceptions.InvalidCreatorIDException;
import com.x9.foodle.model.exceptions.InvalidIDException;
import com.x9.foodle.model.exceptions.InvalidTextException;
import com.x9.foodle.model.exceptions.InvalidTitleException;
import com.x9.foodle.model.exceptions.InvalidVenueReferenceException;
import com.x9.foodle.user.UserModel;
import com.x9.foodle.user.UserUtils;
import com.x9.foodle.util.MessageDispatcher.ErrorMessage;
import com.x9.foodle.util.MessageDispatcher.OkMessage;

@SuppressWarnings("serial")
public class RankingController extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		ServletOutputStream out = resp.getOutputStream();

		String reviewID = req.getParameter("reviewID");
		String ranking = req.getParameter("ranking");

		if (reviewID == null || reviewID.isEmpty()) {
			throw new RuntimeException("null or empty review id");
		}
		if (ranking == null || ranking.isEmpty()) {
			throw new RuntimeException("ranking is null or empty");
		}

		int intRanking = Integer.parseInt(ranking);

		if (intRanking < -1 || intRanking > 1) {
			throw new RuntimeException("ranking is out-of-range: " + intRanking);
		}

		try {
			Ranking r = setReviewRanking(UserUtils.getCurrentUser(req, resp),
					reviewID, intRanking);

			JSONObject json = new JSONObject(new OkMessage("Review ranked.")
					.toJSON());
			json.put("ranking", r.ranking);
			json.put("userRanking", intRanking);

			out.print(json.toString());
		} catch (Exception e) {
			// TODO: log exception
			out.print(new ErrorMessage("Ranking failed").toJSON());
		}
	}

	public static class Ranking {
		public int ranking;

		public Ranking(int ranking) {
			super();
			this.ranking = ranking;
		}
	}

	public static Ranking setReviewRanking(UserModel user, String reviewID,
			int rating) throws SQLException, InvalidIDException {
		Connection conn = null;
		PreparedStatement stm = null;
		ResultSet result = null;
		try {
			ReviewModel review = ReviewModel.getFromSolr(reviewID);
			if (review == null) {
				throw new InvalidIDException("no review with id: " + reviewID);
			}

			// set the user specific ranking in the db
			conn = DBUtils.openConnection();
			// delete any previous ranking
			stm = conn
					.prepareStatement("delete from rankings where userID = ? and reviewID = ?");
			stm.setInt(1, user.getID());
			stm.setString(2, reviewID);
			stm.execute();
			DBUtils.closeStatement(stm);
			// set new ranking
			stm = conn
					.prepareStatement("insert into rankings (userID, reviewID, ranking) values (?, ?, ?)");
			stm.setInt(1, user.getID());
			stm.setString(2, reviewID);
			stm.setInt(3, rating);
			stm.execute();
			DBUtils.closeStatement(stm);

			// get total ranking
			stm = conn
					.prepareStatement("select sum(ranking) from rankings where reviewID = ?");
			stm.setString(1, reviewID);
			stm.execute();

			result = stm.getResultSet();

			if (result == null) {
				throw new SQLException("error getting ranking for review");
			}

			result.next();

			try {
				ReviewModel.Builder builder = review.getEditable();

				Ranking r = new Ranking(result.getInt(1));

				builder.setRanking(r.ranking);
				builder.apply();
				return r;
				// TODO: don't throw runtime exceptions
			} catch (InvalidTitleException e) {
				throw new RuntimeException(e);
			} catch (InvalidTextException e) {
				throw new RuntimeException(e);
			} catch (InvalidVenueReferenceException e) {
				throw new RuntimeException(e);
			} catch (InvalidCreatorIDException e) {
				throw new RuntimeException(e);
			}

		} finally {
			DBUtils.closeResultSet(result);
			DBUtils.closeStatement(stm);
			DBUtils.closeConnection(conn);
		}
	}
}
