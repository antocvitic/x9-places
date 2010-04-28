package com.x9.foodle.datastore;

import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;

public class SolrUtils {

	public static final String SOLR_URL = "http://localhost:8983/solr";

	public static SolrServer getSolrServer() {
		try {
			CommonsHttpSolrServer server = new CommonsHttpSolrServer(SOLR_URL);
			return server;
		} catch (MalformedURLException e) {
			throw new SolrRuntimeException("Malformed url", e);
		}
	}

	public static synchronized String generateUniqueID() {
		Connection conn = null;
		PreparedStatement stm = null;
		ResultSet result = null;
		try {
			conn = DBUtils.openConnection();
			stm = conn.prepareStatement("select id from unique_id_table");
			boolean success = stm.execute();
			if (!success) {
				return null;
			}

			result = stm.getResultSet();

			if (!result.next()) {
				return null;
			}

			long id = result.getLong(result.findColumn("id"));

			long newID = id + 1;

			result.close();
			stm.close();

			stm = conn
					.prepareStatement("update unique_id_table set id = ? where id = ?");
			stm.setLong(1, newID);
			stm.setLong(2, id);
			
			stm.execute();

			return Long.toString(newID);
		} catch (SQLException e) {
			throw new SQLRuntimeException(
					"Bad SQL syntax generating a new unique id", e);
		} finally {
			DBUtils.closeResultSet(result);
			DBUtils.closeStatement(stm);
			DBUtils.closeConnection(conn);
		}
	}
}
