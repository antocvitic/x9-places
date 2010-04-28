package com.x9.foodle.tag;

import java.io.IOException;
import java.util.Date;
import java.util.Vector;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
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
import com.x9.foodle.model.exceptions.InvalidSolrModelException;
import com.x9.foodle.user.UserModel;
import com.x9.foodle.util.DateUtils;
import com.x9.foodle.venue.VenueModel;
import com.x9.foodle.venue.VenueModel.Validator;

public class TagModel {

	
	//TODO ?
	public void getFromSolr(String venueID){
		
	}
	
	public SolrDocumentList getVenueFromSolr(Vector<String> tags){
		try {
			SolrServer server = SolrUtils.getSolrServer();
			SolrQuery query = new SolrQuery();
			// TODO: make the query safe
			String queryString="";
			for(int i=0; i<tags.size(); i++){
				if(i!=tags.size()-1){
					queryString+="tag:"+tags.get(i)+" AND";
				}
				else{
					queryString+="tag:"+tags.get(i);
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
