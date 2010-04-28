package com.x9.foodle.util;

import java.util.Iterator;

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
}
