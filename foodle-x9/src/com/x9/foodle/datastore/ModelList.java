package com.x9.foodle.datastore;

import java.util.ArrayList;
import java.util.List;

public class ModelList<E> {
	private Pager pager;
	private SortableFieldsConstraints sfields;
	private List<E> list;
	private long offset;
	private long resultsReturned;
	private long resultsFound;

	public ModelList() {
		pager = new Pager();
		sfields = null;
		list = new ArrayList<E>();
		offset = 0;
		resultsReturned = 0;
		resultsFound = 0;
	}

	public ModelList(Pager pager, SortableFieldsConstraints sfields,
			List<E> list, long offset, long reviewsReturned, long reviewsFound) {
		super();
		if (pager == null) {
			throw new NullPointerException("cannot have null pager");
		}
		if (sfields == null) {
			throw new NullPointerException("sfields cannot be null");
		}
		if (list == null) {
			throw new NullPointerException("cannot have null list");
		}
		this.pager = pager;
		this.sfields = sfields;
		this.list = list;
		this.offset = offset;
		this.resultsReturned = reviewsReturned;
		this.resultsFound = reviewsFound;
	}

	public Pager getPager() {
		return pager;
	}

	public List<E> getList() {
		return list;
	}

	public long getOffset() {
		return offset;
	}

	public long getResultsReturned() {
		return resultsReturned;
	}

	public long getResultsFound() {
		return resultsFound;
	}

	public boolean isEmpty() {
		return list.isEmpty();
	}

	public long getNextOffset() {
		return offset + resultsReturned;
	}

	public boolean canGetMore() {
		return resultsFound > (offset + resultsReturned); // TODO: test me
	}

	public long getNumberOfPages() {
		return (long) Math.ceil(resultsFound / (double) pager.getMaxReturned());
	}

	/**
	 * Starts at 1.
	 * 
	 * @return
	 */
	public long getCurrentPage() {
		return 1 + offset / pager.getMaxReturned();
	}

	public List<SortableFields> getApplicableSortableFields() {
		return sfields.getApplicableSortableFields();
	}

}
