package com.x9.foodle.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.x9.foodle.sql.DBUtils;
import com.x9.foodle.user.exceptions.BadEmailException;
import com.x9.foodle.user.exceptions.BadPasswordException;
import com.x9.foodle.user.exceptions.BadUsernameException;
import com.x9.foodle.user.exceptions.InvalidUserException;
import com.x9.foodle.util.SQLRuntimeException;

public class UserModel {

	private int userID;
	private String username;
	private String passwordHash;
	private String email;
	private String name;
	private int reputationLevel;
	private boolean isConnectedToFacebook;

	public static UserModel getFromDbByID(int userID) {

		Connection conn = null;
		try {
			conn = DBUtils.openConnection();
			PreparedStatement stm = conn
					.prepareStatement("select * from users where userID = ?");
			stm.setInt(1, userID);
			boolean success = stm.execute();
			if (!success) {
				return null;
			}

			ResultSet result = stm.getResultSet();

			if (!result.next()) {
				return null;
			}

			UserModel user = userFromResultSet(result);

			result.close();
			stm.close();

			return user;

		} catch (SQLException e) {
			throw new SQLRuntimeException("Bad SQL syntax getting user by id",
					e);
		} finally {
			DBUtils.closeConnection(conn);
		}
	}

	public static UserModel getFromDbByUsername(String username) {
		Connection conn = null;
		try {
			conn = DBUtils.openConnection();

			PreparedStatement stm = conn
					.prepareStatement("select * from users where username = ?");
			stm.setString(1, username);
			boolean success = stm.execute();
			if (!success) {
				return null;
			}

			ResultSet result = stm.getResultSet();

			if (!result.next()) {
				return null;
			}

			UserModel user = userFromResultSet(result);

			result.close();
			stm.close();

			return user;

		} catch (SQLException e) {
			throw new SQLRuntimeException(
					"Bad SQL syntax getting user by username", e);
		} finally {
			DBUtils.closeConnection(conn);
		}
	}

	public static UserModel getFromDbByLogin(String username,
			String passwordHash) {
		// TODO: check if this method works
		Connection conn = null;
		try {
			conn = DBUtils.openConnection();

			PreparedStatement stm = conn
					.prepareStatement("select * from users where username = ? and passwordHash = ?");
			stm.setString(1, username);
			stm.setString(2, passwordHash);
			boolean success = stm.execute();
			if (!success) {
				return null;
			}

			ResultSet result = stm.getResultSet();

			if (!result.next()) {
				return null;
			}

			UserModel user = userFromResultSet(result);

			result.close();
			stm.close();

			return user;

		} catch (SQLException e) {
			throw new SQLRuntimeException(
					"Bad SQL syntax getting user by username", e);
		} finally {
			DBUtils.closeConnection(conn);
		}
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

	public String getName() {
		return name;
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

		public Builder setUsername(String username) {
			user.username = username;
			return this;
		}

		public Builder setPasswordHash(String passwordHash) {
			user.passwordHash = passwordHash;
			return this;
		}

		public Builder setEmail(String email) {
			user.email = email;
			return this;
		}

		public Builder setName(String name) {
			user.name = name;
			return this;
		}

		public Builder setReputationLevel(int reputationLevel) {
			user.reputationLevel = reputationLevel;
			return this;
		}

		public Builder setConnectedToFacebook(boolean isConnectedToFacebook) {
			user.isConnectedToFacebook = isConnectedToFacebook;
			return this;
		}

		/**
		 * Will throw <some-exception> if any parameters for this user is
		 * invalid.
		 * 
		 * @throws BadUsernameException
		 *             if the username is invalid.
		 * @throws BadPasswordException
		 *             if the password is invalid
		 * @throws BadEmailException
		 *             if the email address is invalid
		 * 
		 */
		public void validate() throws BadUsernameException,
				BadPasswordException, BadEmailException {
			Validator.validate(user);
		}

		/**
		 * Calls {@code validate()} to check the parameters, and upon success
		 * inserts them as a user in the database. If the builder was created
		 * using the default constructor, a new user is inserted. If it was
		 * created using the {@link UserModel#getEditable()} method, that user
		 * will be updated, and all objects referencing to that user will be
		 * updated.
		 * 
		 * Will throw subclasses of {@link InvalidUserException} if any
		 * parameter is invalid.
		 * 
		 * A user model (with a correct user id) will be returned if no
		 * exceptions are thrown.
		 * 
		 * @return The user model, never null.
		 * @throws BadUsernameException
		 *             {@link #validate()}
		 * @throws BadPasswordException
		 *             {@link #validate()}
		 * @throws BadEmailException
		 *             {@link #validate()}
		 * @throws SQLRuntimeException
		 *             if an sql error occurs.
		 */
		public UserModel apply() throws BadUsernameException,
				BadPasswordException, BadEmailException, SQLRuntimeException {
			// TODO: check if this works
			// throws on bad data
			validate();

			Connection conn = null;
			PreparedStatement stm = null;
			ResultSet generatedKeys = null;
			try {

				conn = DBUtils.openConnection();

				if (editMe != null) {
					// edit an existing user
					stm = conn
							.prepareStatement("update users set username = ?, passwordHash = ?, "
									+ "email = ?, name = ?, repLevel = ?, isFBConnected = ? where userID = ?");
					stm.setInt(7, user.userID);
				} else {
					// insert a new user
					stm = conn
							.prepareStatement(
									"insert into users (username, passwordHash, email, name, repLevel, isFBConnected) "
											+ "values (?, ?, ?, ?, ?, ?)",
									Statement.RETURN_GENERATED_KEYS);

				}

				stm.setString(1, user.username);
				stm.setString(2, user.passwordHash);
				stm.setString(3, user.email);
				stm.setString(4, user.name);
				stm.setInt(5, user.reputationLevel);
				stm.setBoolean(6, user.isConnectedToFacebook);
				stm.execute();

				if (editMe != null) {
					copy(editMe, user);
				} else {
					// retrieve the value of the auto_increment 'userID' column
					generatedKeys = stm.getGeneratedKeys();
					if (generatedKeys.next()) {
						user.userID = generatedKeys.getInt(1);
					} else {
						throw new SQLRuntimeException(
								"Got no user id for new user");
					}

				}

				return user;

			} catch (SQLException e) {
				throw new SQLRuntimeException(
						"Bad SQL syntax inserting/updating user", e);
			} finally {
				DBUtils.closeResultSet(generatedKeys);
				DBUtils.closeStatement(stm);
				DBUtils.closeConnection(conn);
			}
		}
	}

	public static class Validator {
		public static void validateUsername(String username)
				throws BadUsernameException {
			if (username == null) {
				throw new BadUsernameException("no username");
			}
			if (username.length() < 4 || username.length() > 20) {
				throw new BadUsernameException("username.length = "
						+ username.length());
			}
		}

		public static void validatePasswordHash(String passwordHash)
				throws BadPasswordException {
			if (passwordHash == null) {
				throw new BadPasswordException("no password");
			}

			// TODO: where do we check password length/strength?
		}

		public static void validateEmail(String email) throws BadEmailException {
			if (email == null) {
				throw new BadEmailException("no email");
			}
			// TODO: validate email
		}

		public static void validate(UserModel user)
				throws BadUsernameException, BadPasswordException,
				BadEmailException {
			validateUsername(user.username);
			validatePasswordHash(user.passwordHash);
			validateEmail(user.email);

			// TODO: if account is connected to facebook, what more do we need?
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
		this.name = null;
		this.reputationLevel = 0;
		this.isConnectedToFacebook = false;
	}

	/**
	 * Private copy constructor.
	 * 
	 * @param copyMe
	 *            the user to be copied.
	 */
	private UserModel(UserModel copyMe) {
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
	private static void copy(UserModel dest, UserModel src) {
		dest.userID = src.userID;
		dest.username = src.username;
		dest.passwordHash = src.passwordHash;
		dest.email = src.email;
		dest.name = src.name;
		dest.reputationLevel = src.reputationLevel;
		dest.isConnectedToFacebook = src.isConnectedToFacebook;
	}

	/**
	 * Creates a new user, initializing it with the data from {@code result}.
	 * {@code result} must therefore be a valid row from the users table.
	 * 
	 * @param result
	 *            the data to be put into the new user.
	 * @return a user with data taken from {@code result}.
	 * @throws SQLException
	 *             if any SQL error occurs.
	 */
	private static UserModel userFromResultSet(ResultSet result)
			throws SQLException {
		UserModel user = new UserModel();
		user.userID = result.getInt(result.findColumn("userID"));
		user.username = result.getString(result.findColumn("username"));
		user.passwordHash = result.getString(result.findColumn("passwordHash"));
		user.email = result.getString(result.findColumn("email"));
		user.name = result.getString(result.findColumn("name"));
		user.reputationLevel = result.getInt(result.findColumn("repLevel"));
		user.isConnectedToFacebook = result.getBoolean(result
				.findColumn("isFBConnected"));
		return user;
	}
}
