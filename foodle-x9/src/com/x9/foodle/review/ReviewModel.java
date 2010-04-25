package com.x9.foodle.review;

import java.util.Date;

/*
 * TOOD: constructor and solr stuff 
 */

public class ReviewModel {
	
	private String reviewID;
	private String text;
	private Date timeAdded;
	private String venueID;
	private String creator;
	private String rankValue;
	
	
	public String getReviewID() {
		return reviewID;
	}
	public void setReviewID(String reviewID) {
		this.reviewID = reviewID;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Date getTimeAdded() {
		return timeAdded;
	}
	public void setTimeAdded(Date timeAdded) {
		this.timeAdded = timeAdded;
	}
	public String getVenueID() {
		return venueID;
	}
	public void setVenueID(String venueID) {
		this.venueID = venueID;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getRankValue() {
		return rankValue;
	}
	public void setRankValue(String rankValue) {
		this.rankValue = rankValue;
	}
}


