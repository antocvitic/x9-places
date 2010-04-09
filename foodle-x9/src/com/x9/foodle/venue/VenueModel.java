package com.x9.foodle.venue;

import java.util.List;

import com.x9.foodle.user.UserModel;

public class VenueModel {
	private int venueID;
	private String title;
	private String address;
	private String description;
	private List<String> photos;

	private int numberOfRatings;
	private double averageRating;

	private boolean closed;

	private long timeAdded;
	private UserModel creator;

	private long lastUpdated;

	public static VenueModel getFromSolr(int venueID) {
		// TODO:
		return null;
	}

	public int getVenueID() {
		return venueID;
	}

	public String getTitle() {
		return title;
	}

	public String getAddress() {
		return address;
	}

	public String getDescription() {
		return description;
	}

	public List<String> getPhotos() {
		return photos;
	}

	public int getNumberOfRatings() {
		return numberOfRatings;
	}

	public double getAverageRating() {
		return averageRating;
	}

	public boolean isClosed() {
		return closed;
	}

	public long getTimeAdded() {
		return timeAdded;
	}

	public UserModel getCreator() {
		return creator;
	}

	public long getLastUpdated() {
		return lastUpdated;
	}

	public static class Builder {
		// TODO:
	}

	public static class Validator {
		// TODO:
	}

	/**
	 * Private constructor, use {@link Builder} create new venues.
	 */
	private VenueModel() {
		this.venueID = -1;
		this.title = null;
		this.address = null;
		this.description = null;
		this.photos = null;

		this.numberOfRatings = 0;
		this.averageRating = 0.0;

		this.closed = false;

		this.timeAdded = -1;
		this.creator = null;

		this.lastUpdated = -1;
	}

	/**
	 * Private copy constructor.
	 * 
	 * @param copyMe
	 *            the user to be copied.
	 */
	private VenueModel(VenueModel copyMe) {
		this();
		copy(this, copyMe);
	}

	/**
	 * Copies all the fields of src into dest.
	 * 
	 * @param dest
	 *            the destination of the copy operation.
	 * @param src
	 *            the source of the copy operation.
	 */
	private static void copy(VenueModel dest, VenueModel src) {
		dest.venueID = src.venueID;
		dest.title = src.title;
		dest.address = src.address;
		dest.description = src.description;
		dest.photos = src.photos;

		dest.numberOfRatings = src.numberOfRatings;
		dest.averageRating = src.averageRating;

		dest.closed = src.closed;

		dest.timeAdded = src.timeAdded;
		dest.creator = src.creator;

		dest.lastUpdated = src.lastUpdated;
	}
}
