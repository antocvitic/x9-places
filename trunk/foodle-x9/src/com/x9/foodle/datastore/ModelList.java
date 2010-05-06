package com.x9.foodle.datastore;

import java.util.ArrayList;
import java.util.List;

public class ModelList<E extends SortableFieldsConstraints> {
	private Pager pager;
	private List<E> list;
	private long offset;
	private long resultsReturned;
	private long resultsFound;

	public ModelList() {
		pager = null;
		list = new ArrayList<E>();
		offset = 0;
		resultsReturned = 0;
		resultsFound = 0;
	}

	public ModelList(Pager pager, List<E> list, long offset,
			long reviewsReturned, long reviewsFound) {
		super();
		if (pager == null) {
			throw new NullPointerException("cannot have null pager");
		}
		if (list == null) {
			throw new NullPointerException("cannot have null list");
		}
		this.pager = pager;
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
		try {
			// please kill me
			@SuppressWarnings("unchecked")
			List<SortableFields> ret = (List<SortableFields>) list.get(0)
					.getClass().getMethod("getApplicableSortableFields")
					.invoke(null);
			return ret;
		} catch (Exception e) {
			// e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
