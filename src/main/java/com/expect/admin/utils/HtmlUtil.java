package com.expect.admin.utils;

import java.util.Arrays;
import java.util.List;

public class HtmlUtil {

	private static List<String> characterDecodes = Arrays.asList("<", ">", "\"", "&", " ");
	private static List<String> characterEncodes = Arrays.asList("&lt;", "&gt;", "&quot;", "&amp;", " nbsp;");

	/**
	 * 转义html字符
	 * 
	 * @param character
	 *            转义前字符
	 * @return 转义后字符
	 */
	public static String escapeCharacter(String character) {
		StringBuilder sb = new StringBuilder();
		char[] chars = character.toCharArray();
		for (char c : chars) {
			boolean isEscape = false;
			for (int i = 0; i < characterDecodes.size(); i++) {
				if (characterDecodes.get(i).equals(c + "")) {
					sb.append(characterEncodes.get(i));
					isEscape = true;
					break;
				}
			}
			if (!isEscape) {
				sb.append(c + "");
			}
		}
		return sb.toString();
	}

}
