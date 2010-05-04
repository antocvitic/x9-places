package com.x9.foodle.datastore;

import java.util.ArrayList;
import java.util.List;

public class ModelList<E> {
	private List<E> list;
	private long offset;
	private long resultsReturned;
	private long resultsFound;

	public ModelList() {
		list = new ArrayList<E>();
		offset = 0;
		resultsReturned = 0;
		resultsFound = 0;
	}

	public ModelList(List<E> list, long offset, long reviewsReturned,
			long reviewsFound) {
		super();
		if (list == null) {
			throw new NullPointerException("cannot have null reviews");
		}
		this.list = list;
		this.offset = offset;
		this.resultsReturned = reviewsReturned;
		this.resultsFound = reviewsFound;
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
}
