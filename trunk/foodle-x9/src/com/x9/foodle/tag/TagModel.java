package com.x9.foodle.tag;

import java.util.Vector;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;

import com.x9.foodle.datastore.SolrRuntimeException;
import com.x9.foodle.datastore.SolrUtils;

public class TagModel {

	// TODO ?
	public void getFromSolr(String venueID) {

	}

	public SolrDocumentList getVenueFromSolr(Vector<String> tags) {
		try {
			SolrServer server = SolrUtils.getSolrServer();
			SolrQuery query = new SolrQuery();
			// TODO: make the query safe
			String queryString = "";
			for (int i = 0; i < tags.size(); i++) {
				if (i != tags.size() - 1) {
					queryString += "tag:" + tags.get(i) + " AND";
				} else {
					queryString += "tag:" + tags.get(i);
				}
			}

			query.setQuery(queryString);
			QueryResponse rsp = server.query(query);
			SolrDocumentList docs = rsp.getResults();

			if (docs.isEmpty()) {
				return null;
			}

			return docs;

		} catch (SolrServerException e) {
			throw new SolrRuntimeException("Solr Server Exception", e);
		}
	}

}
