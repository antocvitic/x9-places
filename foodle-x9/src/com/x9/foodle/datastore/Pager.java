package com.x9.foodle.datastore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.solr.client.solrj.SolrQuery;

public class Pager {

	public static final int[] MAX_RETURNED_LIST = new int[] { 1, 2, 3, 4, 5, 10, 20, 25 };
	public static final int DEFAULT_MAX_RETURNED = 10;

	public static String SORT_FIELDS_PARAM_NAME = "f";
	public static String OFFSET_PARAM_NAME = "o";
	public static String MAX_RETURNED_PARAM_NAME = "m";

	private String paramPrefix;
	private List<SortField> sortFields;
	private int offset;
	private int maxReturned;

	public Pager(List<SortField> sortFields, int offset, int maxReturned) {
		this.paramPrefix = "";
		if (sortFields == null)
			throw new NullPointerException("null sortFields");
		if (sortFields.isEmpty())
			throw new IllegalArgumentException("cannot have empty sortfields");
		if (offset < 0)
			throw new IllegalArgumentException("negative offset: " + offset);
		if (maxReturned < 0)
			throw new IllegalArgumentException("negative maxReturned: "
					+ offset);
		this.sortFields = sortFields;
		this.offset = offset;
		this.maxReturned = maxReturned;
	}

	public Pager(SortField sortField, int offset, int maxReturned) {
		this.paramPrefix = "";
		if (sortField == null)
			throw new NullPointerException("null sortField");
		if (offset < 0)
			throw new IllegalArgumentException("negative offset: " + offset);
		if (maxReturned < 0)
			throw new IllegalArgumentException("negative maxReturned: "
					+ offset);

		this.sortFields = new ArrayList<SortField>();
		this.sortFields.add(sortField);
		this.offset = offset;
		this.maxReturned = maxReturned;
	}

	/**
	 * Same as {@code Pager(sortFields, 0, Pager.DEFAULT_MAX_RETURNED)}.
	 * 
	 * @see Pager#Pager(List, int, int)
	 * @param sortFields
	 */
	public Pager(List<SortField> sortFields) {
		this(sortFields, 0, DEFAULT_MAX_RETURNED);
	}

	/**
	 * Sam as {@code Pager(sortField, 0, Pager.DEFAULT_MAX_RETURNED)}.
	 * 
	 * @see Pager#Pager(SortField, int, int)
	 * @param sortField
	 */
	public Pager(SortField sortField) {
		this(sortField, 0, DEFAULT_MAX_RETURNED);
	}

	public Pager() {
		this.paramPrefix = "";
		this.sortFields = new ArrayList<SortField>();
		this.sortFields.add(new SortField(SortableFields.SCORE, Order.ASC));
		this.offset = 0;
		this.maxReturned = 10;
	}

	public Pager(Pager other) {
		this.paramPrefix = other.paramPrefix;
		this.sortFields = new ArrayList<SortField>(other.sortFields);
		this.offset = other.offset;
		this.maxReturned = other.maxReturned;
	}

	public Pager(HttpServletRequest request, String prefix, Pager defaults) {
		this();
		this.paramPrefix = prefix;

		// sortFields
		Object paramFields = request.getParameter(prefix
				+ SORT_FIELDS_PARAM_NAME);
		if (paramFields != null) {
			sortFields.clear();
			String sfields = (String) paramFields;
			String[] split = sfields.split(",");
			for (String sfp : split) {
				sortFields.add(new SortField(sfp));
			}
		} else {
			sortFields = new ArrayList<SortField>(defaults.sortFields);
		}

		// offset
		Object paramOffset = request.getParameter(prefix + OFFSET_PARAM_NAME);
		if (paramOffset != null) {
			this.offset = Integer.parseInt((String) paramOffset);
		} else {
			this.offset = defaults.offset;
		}

		// maxReturned
		Object paramMax = request
				.getParameter(prefix + MAX_RETURNED_PARAM_NAME);
		if (paramMax != null) {
			this.maxReturned = Integer.parseInt((String) paramMax);
		} else {
			this.maxReturned = defaults.maxReturned;
		}
	}

	public Pager(HttpServletRequest request, String prefix) {
		this(request, prefix, new Pager());
	}

	public void apply(SolrQuery query) {
		query.setStart(offset);
		query.setRows(maxReturned);
		for (SortField sf : sortFields) {
			query.addSortField(sf.field.field, sf.order.order);
		}
	}

	public List<SortField> getSortFields() {
		return sortFields;
	}

	public SortField getFirstSortField() {
		return sortFields.get(0);
	}

	public int getOffset() {
		return offset;
	}

	public int getMaxReturned() {
		return maxReturned;
	}

	/**
	 * Clears sortFields and adds @{code sf} as the only sortfield.
	 * 
	 * @param sf
	 */
	public Pager setSortField(SortField sf) {
		this.sortFields.clear();
		this.sortFields.add(sf);
		return this;
	}

	public Pager setOffset(int offset) {
		this.offset = offset;
		return this;
	}

	public Pager setMaxReturned(int maxReturned) {
		this.maxReturned = maxReturned;
		return this;
	}

	public String getAsParams() {
		return getSortFieldsAsParam() + "&" + getOffsetAsParam() + "&"
				+ getMaxReturnedAsParam();
	}

	public String getSortFieldsAsParam() {
		StringBuilder sb = new StringBuilder();
		sb.append(paramPrefix);
		sb.append(SORT_FIELDS_PARAM_NAME);
		sb.append("=");
		for (SortField sf : sortFields) {
			sb.append(sf.toParamString());
			sb.append(",");
		}
		if (sb.length() > 0) {
			sb.setLength(sb.length() - 1);
		}
		return sb.toString();
	}

	public String getOffsetAsParam() {
		return paramPrefix + OFFSET_PARAM_NAME + "=" + offset;
	}

	public String getMaxReturnedAsParam() {
		return paramPrefix + MAX_RETURNED_PARAM_NAME + "=" + maxReturned;
	}

	public String generateOtherQueryString(HttpServletRequest request) {
		@SuppressWarnings("unchecked")
		Set<Map.Entry<String, String[]>> es = request.getParameterMap()
				.entrySet();
		StringBuilder sb = new StringBuilder();
		for (Entry<String, String[]> p : es) {
			if (p.getKey().equals(paramPrefix + SORT_FIELDS_PARAM_NAME)
					|| p.getKey().equals(paramPrefix + OFFSET_PARAM_NAME)
					|| p.getKey().equals(paramPrefix + MAX_RETURNED_PARAM_NAME)) {
				continue;
			}
			sb.append(p.getKey());
			sb.append("=");
			sb.append(p.getValue()[0]);
			sb.append("&");
		}
		if (sb.length() > 0)
			sb.setLength(sb.length() - 1);
		return sb.toString();
	}

	@Override
	public String toString() {
		return "Pager [paramPrefix=" + paramPrefix + ", sortFields="
				+ sortFields + ", offset=" + offset + ", maxReturned="
				+ maxReturned + "]";
	}

}
