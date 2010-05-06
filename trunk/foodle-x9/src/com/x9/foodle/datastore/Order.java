/**
 * 
 */
package com.x9.foodle.datastore;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;

public enum Order {
	ASC("^", SolrQuery.ORDER.asc), //
	DESC("v", SolrQuery.ORDER.desc);

	public final String display;
	public final SolrQuery.ORDER order;

	private Order(String display, ORDER order) {
		this.display = display;
		this.order = order;
	}

	public Order getOpposite() {
		if (this == ASC)
			return DESC;
		return ASC;
	}

}