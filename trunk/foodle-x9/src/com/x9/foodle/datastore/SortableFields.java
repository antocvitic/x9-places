/**
 * 
 */
package com.x9.foodle.datastore;

public enum SortableFields {
	SCORE("score", "score", Order.DESC), // 
	// uses solr field title_sortable because you can't sort on title
	TITLE("title", "title_sortable", Order.ASC), // 
	TIME_ADDED("time added", "timeAdded", Order.ASC), // 
	AVERAGE_RATING("rating", "averageRating", Order.DESC), //
	NUMBER_OF_RATINGS("ratings", "numberOfRatings", Order.DESC), //
	CREATOR_ID("creator id", "creator", Order.ASC), //
	VENUE_ID("venue id", "reference", Order.ASC), // 
	REVIEW_ID("review id", "reference", Order.ASC), // 
	RANKING("ranking", "ranking", Order.DESC), // 
	LAST_UPDATED("last updated", "lastModified", Order.DESC); //

	public final String display;
	public final String field;
	public final Order defaultOrder;

	private SortableFields(String display, String field, Order defaultOrder) {
		this.display = display;
		this.field = field;
		this.defaultOrder = defaultOrder;
	}
}