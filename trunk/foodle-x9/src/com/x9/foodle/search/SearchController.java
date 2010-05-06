package com.x9.foodle.search;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;

import com.x9.foodle.datastore.SolrRuntimeException;
import com.x9.foodle.datastore.SolrUtils;
import com.x9.foodle.model.exceptions.InvalidAddressException;
import com.x9.foodle.model.exceptions.InvalidAverageRatingException;
import com.x9.foodle.model.exceptions.InvalidCreatorIDException;
import com.x9.foodle.model.exceptions.InvalidDescriptionException;
import com.x9.foodle.model.exceptions.InvalidIDException;
import com.x9.foodle.model.exceptions.InvalidNumberOfRatingsException;
import com.x9.foodle.model.exceptions.InvalidTitleException;
import com.x9.foodle.util.DateUtils;
import com.x9.foodle.venue.VenueModel;

public class SearchController {

	public static final String SOLR_TYPE = "venuemodel";

	public static SolrDocumentList query(String q)
			throws MalformedURLException, SolrServerException {
		SolrServer server = SolrUtils.getSolrServer();
		SolrQuery query = new SolrQuery();

		// TODO: Fixa B�TTRE och s�krare query!
		query.setQuery("title:" + q + " OR address:" + q + " OR description:"
				+ q + " OR tags:" + q);
		// query.setQuery("title:" + q);
		QueryResponse response = server.query(query);

		SolrDocumentList docs = response.getResults();

		// venueFromSolrDocument(doc);
		if (docs.isEmpty()) {
			return null;
		} else {
			return docs;
		}
	}

	
}