package com.x9.foodle.search;

import java.net.MalformedURLException;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import com.x9.foodle.datastore.SolrUtils;

public class SearchController {

	public static final String SOLR_TYPE = "venuemodel";

	public static SolrDocumentList query(String q, String choice, String highRating)
			throws MalformedURLException, SolrServerException {
		SolrServer server = SolrUtils.getSolrServer();
		SolrQuery query = new SolrQuery();
		
		if(choice.equals("all")) {
			// TODO: FIX BETTER QUERY AND MAKE IT SAFER
			query.setQuery("title:" + q + " OR address:" + q + " OR description:"
					+ q + " OR tags:" + q);
		}
		else if(choice.equals("venue")) {
			query.setQuery("title:" + q);
		}
		else if(choice.equals("street")) {
			query.setQuery("address:" + q);
		}
		else if(choice.equals("review")) {
			query.setQuery("type:reviewmodel AND (title:" +q +" OR description:" +q +")");
		}
		else if(choice.equals("comment")) {
			query.setQuery("type:commentmodel AND description:" +q);
		}
		else if(choice.equals("tags")) {
			String[] tags = q.split(" ");
			String tag_query = "";
			for(int i = 0; tags.length-1 > i; i++) {
				tag_query += "tags:" + tags[i] + " AND ";
			}
			tag_query += "tags:" + tags[tags.length-1];
			query.setQuery(tag_query);
		}
		else {
			return null;
		}
		//high rating search
	 	if(highRating != null){
	 	String temper = query.getQuery();
	 	temper += " AND averageRating:[4 TO *]";
	 	query.setQuery(temper);
	 	}
		QueryResponse response = server.query(query);

		SolrDocumentList docs = response.getResults();

		if (docs.isEmpty()) {
			return null;
		} else {
			return docs;
		}
	}

}