package com.x9.foodle.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.x9.foodle.util.SQLRuntimeException;

/**
 * A utility class for dealing the the MySQL database.
 * 
 * Details for the connection to MySQL, such as username and password, is
 * specified in /META-INF/context.xml.
 * 
 * @author tgwizard
 * 
 */
public class DBUtils {

	/**
	 * Acquires a connection to the MySQL database, details for which is
	 * specified in /META-INF/context.xml.
	 * 
	 * @return An open connection to the MySQL database, never null.
	 */
	public static Connection openConnection() {
		InitialContext ctx;
		try {
			ctx = new InitialContext();
			DataSource ds = (DataSource) ctx
					.lookup("java:comp/env/jdbc/MySQLDB");
			Connection conn = ds.getConnection();
			return conn;
		} catch (NamingException e) {
			throw new RuntimeException("Couldn't get InitialContext", e);
		} catch (SQLException e) {
			throw new SQLRuntimeException(
					"SQLException when attempting to open connection to database.",
					e);
		}
	}

	/**
	 * Closes the Connection {@code connection}.
	 * 
	 * @param connection
	 *            the Connection to be closed.
	 */
	public static void closeConnection(Connection connection) {
		if (connection == null)
			return;
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO: log this error
			// ignore exception, we can't do anything about it
		}
	}

	/**
	 * Closes the Statement {@code stm}.
	 * 
	 * @param stm
	 *            the Statement to be closed.
	 */
	public static void closeStatement(Statement stm) {
		if (stm == null)
			return;
		try {
			stm.close();
		} catch (SQLException e) {
			// TODO: log this error
			// ignore exception, we can't do anything about it
		}
	}

	/**
	 * Closes the ResultSet {@code result}.
	 * 
	 * @param result
	 *            the ResultSet to be closed.
	 */
	public static void closeResultSet(ResultSet result) {
		if (result == null)
			return;
		try {
			result.close();
		} catch (SQLException e) {
			// TODO: log this error
			// ignore exception, we can't do anything about it
		}
	}
}
