package com.x9.foodle.datastore;

import java.net.MalformedURLException;

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
}
