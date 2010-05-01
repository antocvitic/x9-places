package com.x9.foodle.util;

import java.text.NumberFormat;
import java.util.Iterator;
import java.util.Locale;

public class StringUtils {
	public static String join(Iterable<?> list, String delimiter) {
		StringBuilder sb = new StringBuilder();
		Iterator<?> iter = list.iterator();
		while (iter.hasNext()) {
			sb.append(iter.next());
			if (iter.hasNext())
				sb.append(delimiter);
		}
		return sb.toString();
	}
	
	public static String formatRating(double rating) {
		NumberFormat nf = NumberFormat.getInstance(Locale.ENGLISH);
		nf.setMaximumFractionDigits(1);
		nf.setMinimumFractionDigits(1);
		return nf.format(rating);
	}
}
