package com.x9.foodle.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtils {
	/**
	 * TODO: was there a better way to do this?
	 */
	public static Connection openConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception e) {
			throw new DBException("Unable to load mysql driver.", e);
		}

		try {
			Connection connection = DriverManager.getConnection(
					"jdbc:mysql://localhost/x9db", "x9user", "x9pwd");
			return connection;
		} catch (SQLException e) {
			throw new DBException(
					"SQLException when attempting to open connection.", e);
		}
	}

	public static void closeConnection(Connection connection) {
		if (connection == null)
			return;
		try {
			connection.close();
		} catch (SQLException e) {
			throw new DBException(
					"SQLException when attempting to close connection.", e);
		}
	}

	@SuppressWarnings("serial")
	public static class DBException extends RuntimeException {

		public DBException() {
			super();
		}

		public DBException(String arg0, Throwable arg1) {
			super(arg0, arg1);
		}

		public DBException(String arg0) {
			super(arg0);
		}

		public DBException(Throwable arg0) {
			super(arg0);
		}

	}
}
