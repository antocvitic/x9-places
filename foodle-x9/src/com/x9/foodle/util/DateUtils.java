package com.x9.foodle.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtils {

	private static final SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

	public static Date getNowUTC() {
		return Calendar.getInstance(TimeZone.getTimeZone("GMT:00")).getTime();
	}

	public static String dateToSolrDate(Date date) {
		return sdf.format(date.getTime());
	}

	public static Date solrDateToDate(String date) {
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			throw new RuntimeException("Could not parse date string: " + date,
					e);
		}

	}
}
