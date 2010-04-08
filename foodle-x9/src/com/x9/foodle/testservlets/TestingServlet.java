package com.x9.foodle.testservlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import com.mysql.jdbc.Connection;

/**
 * Servlet implementation class for UserTest: HelloServlet
 * 
 */
@SuppressWarnings("serial")
public class TestingServlet extends javax.servlet.http.HttpServlet implements
		javax.servlet.Servlet {

	public TestingServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String Name = request.getParameter("Name");

		response.setContentType("text/html");
		ServletOutputStream out = response.getOutputStream();
		out.println("<html>");
		out.println("<head><title>Hello World</title></head>");
		out.println("<body>");

		try {
			// out.println("load mysql driver");
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception E) {
			out.println("Unable to load driver.");
			E.printStackTrace();
		}

		out.println("<br><hr>");

		// Get a connection
		try {
			Connection Conn = DriverManager.getConnection(
					"jdbc:mysql://localhost/x9db", "x9user", "x9pwd");

			/*
			 * A PreparedStatement then execute it and catch result in ResultSet
			 */
			PreparedStatement pStatement = Conn
					.prepareStatement("Select * From Users where FirstName LIKE ? OR LastName Like ? OR UserName LIKE ?");

			pStatement.setString(1, Name);
			pStatement.setString(2, Name);
			pStatement.setString(3, Name);

			ResultSet RS = pStatement.executeQuery();
			// Show it
			while (RS.next()) {
				out.println("Full name: " + RS.getString(4) + " "
						+ RS.getString(5) + " Age: " + RS.getString(2));
			}
			// Clean up after ourselves
			RS.close();
			pStatement.close();
			Conn.close();
		} catch (SQLException E) {
			out.println("SQLException: " + E.getMessage());
			out.println("SQLState:     " + E.getSQLState());
			out.println("VendorError:  " + E.getErrorCode());
		}
		out.println("<br><hr>");

		out.println("</body></html>");
	}
}
