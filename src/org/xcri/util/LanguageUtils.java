package org.xcri.util;

import com.ibm.icu.util.ULocale;

public class LanguageUtils {

	/**
	 * Validates a given language tag. Empty and null values are considered false.
	 * @param tag
	 * @return true if a valid language tag, otherwise false
	 */
	public static boolean isValidLanguageTag(String tag){
		try {
			ULocale locale = ULocale.forLanguageTag(tag);
			if (locale.toLanguageTag() == null) return false;
			// We don't accept "x" extensions (private use tags)
			if(locale.getExtension("x".charAt(0))!=null) return false;
			if (!locale.toLanguageTag().equalsIgnoreCase(tag)) return false;
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
