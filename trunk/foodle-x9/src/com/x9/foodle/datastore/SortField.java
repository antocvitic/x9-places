package com.x9.foodle.datastore;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;

public class SortField<E> {
	public static enum Order {
		ASC(SolrQuery.ORDER.asc), //
		DESC(SolrQuery.ORDER.desc);

		public SolrQuery.ORDER order;

		private Order(ORDER order) {
			this.order = order;
		}

	}

	public final E field;
	public final Order order;

	public SortField(E field, Order order) {
		super();
		this.field = field;
		this.order = order;
	}

}
