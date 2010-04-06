package com.x9.foodle.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.x9.foodle.sql.DBUtils;

public class UserModel {

	private int userID;
	private String username;
	private String passwordHash;
	private String email;
	private int reputationLevel;
	private boolean isConnectedToFacebook;

	public static UserModel getFromDb(int userID) {
		// TODO:
		UserModel user = null;
		Connection conn = null;
		try {
			conn = DBUtils.openConnection();
			PreparedStatement stm = conn
					.prepareStatement("select * from users where userID = ?");
			stm.setInt(0, userID);
			boolean success = stm.execute();
			if (!success) {
				return null;
			}

			ResultSet result = stm.getResultSet();
			result.next();

			user = userFromResultSet(result);

			result.close();
			stm.close();

			return user;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtils.closeConnection(conn);
		}
		return user;
	}

	public static UserModel getFromDb(String username) {
		// TODO:
		return null;
	}

	public static UserModel getFromDb(String username, String passwordHash) {
		// TODO:
		return null;
	}

	public int getUserID() {
		return userID;
	}

	public String getUsername() {
		return username;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public String getEmail() {
		return email;
	}

	public int getReputationLevel() {
		return reputationLevel;
	}

	public boolean isConnectedToFacebook() {
		return isConnectedToFacebook;
	}

	public Builder getEditable() {
		return new Builder(this);
	}

	/**
	 * Class for creating a new user and inserting it into the db.
	 * 
	 * @author tgwizard
	 * 
	 */
	public static class Builder {

		private UserModel user;
		private UserModel editMe;

		public Builder() {
			this.user = new UserModel();
		}

		private Builder(UserModel editMe) {
			this.user = new UserModel(editMe);
			this.editMe = editMe;
		}

		public void setUserID(int userID) {
			user.userID = userID;
		}

		public void setUsername(String username) {
			user.username = username;
		}

		public void setPasswordHash(String passwordHash) {
			user.passwordHash = passwordHash;
		}

		public void setEmail(String email) {
			user.email = email;
		}

		public void setReputationLevel(int reputationLevel) {
			user.reputationLevel = reputationLevel;
		}

		public void setConnectedToFacebook(boolean isConnectedToFacebook) {
			user.isConnectedToFacebook = isConnectedToFacebook;
		}

		/**
		 * Will throw <some-exception> if any parameters for this user is
		 * invalid.
		 * 
		 */
		public void validate() {
			if (user.username == null) {
				// TODO: better exception
				throw new IllegalArgumentException("no username");
			}
			if (user.username.length() < 4 || user.username.length() > 20) {
				// TODO: better exception
				throw new IllegalArgumentException("username.length = "
						+ user.username.length());
			}

			if (user.passwordHash == null) {
				// TODO: better exception
				throw new IllegalArgumentException("no password");
			}

			// TODO: where do we check password length/strength?

			if (user.email == null) {
				// TODO: better exception
				throw new IllegalArgumentException("no email");
			}
			// TODO: validate email

			// TODO: if account is connected to facebook, what more do we need?

		}

		/**
		 * Calls {@code validate()} to check the parameters, and upon success
		 * inserts them as a user in the database. If the builder was created
		 * using the default constructor, a new user is inserted. If it was
		 * created using the {@link UserModel#getEditable()} method, that user
		 * will be updated, and all objects referencing to that user will be
		 * updated.
		 * 
		 * Will throw <some-exception> if any parameter is invalid, and will
		 * throw <some-other-exception> if an error connecting to the database
		 * occurs.
		 * 
		 * A user model (with a correct user id) will be returned if no
		 * exceptions are thrown.
		 * 
		 * @return The user model, never null.
		 */
		public UserModel apply() {
			validate();

			if (editMe != null) {
				// TODO: edit an existing user
				// TODO: update editMe
				return null;
			} else {
				// TODO: insert a new user
				return null;
			}
		}

	}

	/**
	 * Private constructor, use {@link Builder} create new users.
	 */
	private UserModel() {
		this.userID = -1;
		this.username = null;
		this.passwordHash = null;
		this.email = null;
		this.reputationLevel = 0;
		this.isConnectedToFacebook = false;
	}

	/**
	 * Private copy constructor.
	 * 
	 * @param copyMe
	 */
	private UserModel(UserModel copyMe) {
		this.userID = copyMe.userID;
		this.username = copyMe.username;
		this.passwordHash = copyMe.passwordHash;
		this.email = copyMe.email;
		this.reputationLevel = copyMe.reputationLevel;
		this.isConnectedToFacebook = copyMe.isConnectedToFacebook;
	}

	private static UserModel userFromResultSet(ResultSet result)
			throws SQLException {
		UserModel user = new UserModel();
		user.userID = result.getInt(result.findColumn("userID"));
		user.username = result.getString(result.findColumn("username"));
		user.passwordHash = result.getString(result.findColumn("passwordHash"));
		user.email = result.getString(result.findColumn("email"));
		user.reputationLevel = result.getInt(result.findColumn("repLevel"));
		user.isConnectedToFacebook = result.getBoolean(result
				.findColumn("isFBConnected"));
		return user;
	}
}
