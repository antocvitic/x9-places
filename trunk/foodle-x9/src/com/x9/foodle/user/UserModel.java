package com.x9.foodle.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.regex.Pattern;

import org.mindrot.jbcrypt.BCrypt;

import com.x9.foodle.sql.DBUtils;
import com.x9.foodle.sql.SQLRuntimeException;
import com.x9.foodle.user.exceptions.BadEmailException;
import com.x9.foodle.user.exceptions.BadPasswordException;
import com.x9.foodle.user.exceptions.BadUsernameException;
import com.x9.foodle.user.exceptions.InvalidUserException;

public class UserModel {

	private int userID;
	private String username;
	private String passwordHash;
	private String email;
	private String name;
	private int reputationLevel;
	private String sessionToken;
	private boolean isConnectedToFacebook;

	public static UserModel getFromDbByID(int userID) {
		Connection conn = null;
		PreparedStatement stm = null;
		ResultSet result = null;
		try {
			conn = DBUtils.openConnection();
			stm = conn.prepareStatement("select * from users where userID = ?");
			stm.setInt(1, userID);
			boolean success = stm.execute();
			if (!success) {
				return null;
			}

			result = stm.getResultSet();

			if (!result.next()) {
				return null;
			}

			UserModel user = userFromResultSet(result);

			return user;

		} catch (SQLException e) {
			throw new SQLRuntimeException("Bad SQL syntax getting user by id",
					e);
		} finally {
			DBUtils.closeResultSet(result);
			DBUtils.closeStatement(stm);
			DBUtils.closeConnection(conn);
		}
	}

	public static UserModel getFromDbByUsername(String username) {
		if (username == null)
			throw new IllegalArgumentException("username was null!");
		Connection conn = null;
		PreparedStatement stm = null;
		ResultSet result = null;
		try {
			conn = DBUtils.openConnection();

			stm = conn
					.prepareStatement("select * from users where username = ?");
			stm.setString(1, username);
			boolean success = stm.execute();
			if (!success) {
				return null;
			}

			result = stm.getResultSet();

			if (!result.next()) {
				return null;
			}

			UserModel user = userFromResultSet(result);

			return user;

		} catch (SQLException e) {
			throw new SQLRuntimeException(
					"Bad SQL syntax getting user by username", e);
		} finally {
			DBUtils.closeResultSet(result);
			DBUtils.closeStatement(stm);
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

	public String getSessionToken() {
		return sessionToken;
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

		// public Builder setSessionToken(String sessionToken) {
		// user.sessionToken = sessionToken;
		// return this;
		// }

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
			// throws on bad data
			validate();

			Connection conn = null;
			PreparedStatement stm = null;
			ResultSet result = null;
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
									"insert into users (username, passwordHash, email, name, repLevel, isFBConnected, sessionToken) "
											+ "values (?, ?, ?, ?, ?, ?, ?)",
									Statement.RETURN_GENERATED_KEYS);

					// generate new session token
					Random random = new Random(System.currentTimeMillis());
					String token = BCrypt.hashpw(Long.toString(random
							.nextLong()), BCrypt.gensalt());
					stm.setString(7, token);
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
					result = stm.getGeneratedKeys();
					if (result.next()) {
						user.userID = result.getInt(1);
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
				DBUtils.closeResultSet(result);
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

			Pattern p = Pattern.compile("[a-zA-Z][a-zA-Z0-9_-]*");
			if (!p.matcher(username).matches()) {
				throw new BadUsernameException(
						"username contained invalid characters: " + username);
			}

		}

		public static void validatePassword(String password)
				throws BadPasswordException {
			if (password == null) {
				throw new BadPasswordException("no password");
			}

			if (password.length() < 4 || password.length() > 40) {
				throw new BadPasswordException("password.length = "
						+ password.length());
			}

			// TODO: check password strength?
		}

		public static void validatePasswordHash(String passwordHash)
				throws BadPasswordException {
			if (passwordHash == null) {
				throw new BadPasswordException("no password hash");
			}

		}

		public static void validateEmail(String email) throws BadEmailException {
			if (email == null) {
				throw new BadEmailException("no email");
			}
			// regex copied from: http://www.regular-expressions.info/email.html
			// with some modifications to allow for new TLDs
			// http://en.wikipedia.org/wiki/List_of_Internet_top-level_domains

			String ccTLD = "[A-Z]{2}";
			String gTLD = "aero|asia|biz|cat|com|coop|edu|gov|info|int|jobs|mil|mobi|museum|name|net|org|pro|tel|travel";

			Pattern p = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.(?:"
					+ ccTLD + "|" + gTLD + ")$", Pattern.CASE_INSENSITIVE);
			if (!p.matcher(email).matches()) {
				throw new BadEmailException("invalid email: " + email);
			}
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
		this.sessionToken = null;
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
		dest.sessionToken = src.sessionToken;
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
		user.sessionToken = result.getString(result.findColumn("sessionToken"));
		user.isConnectedToFacebook = result.getBoolean(result
				.findColumn("isFBConnected"));
		return user;
	}

	/**
	 * For debug purposes only.
	 */
	@Override
	public String toString() {
		return "UserModel [userID=" + userID + ", username=" + username
				+ ", passwordHash=" + passwordHash + ", email=" + email
				+ ", name=" + name + ", reputationLevel=" + reputationLevel
				+ ", sessionToken=" + sessionToken + ", isConnectedToFacebook="
				+ isConnectedToFacebook + "]";
	}

}
