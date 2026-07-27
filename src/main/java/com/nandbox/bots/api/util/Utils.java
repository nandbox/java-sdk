package com.nandbox.bots.api.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.nandbox.bots.api.Nandbox.Api;
import com.nandbox.bots.api.data.MenuCallback;
import com.nandbox.bots.api.outmessages.SetNavigationButtonOutMessage;
import net.minidev.json.JSONArray;

/**
 * Media Utility CLass
 * 
 * @author Hossam
 *
 */
public class Utils {

	public enum MediaType {
		text, image, video, audio, file, voice, textFile, contact, location, gif_video, gif_image, sticker, article
	};

	private static final AtomicInteger seq = new AtomicInteger();

	/**
	 * @param duration
	 *            in milliseconds
	 * @return formatted in minutes and seconds and
	 */
	public static String formatDurationInMinsAndSeconds(Integer duration) {
		String durationInMinsAndSeconds = null;
		if (duration != null) {
			durationInMinsAndSeconds = String.format("%d min, %d sec", TimeUnit.MILLISECONDS.toMinutes(duration),
					TimeUnit.MILLISECONDS.toSeconds(duration)
							- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)));

		}

		return durationInMinsAndSeconds;
	}

	public static void setNavigationButton(String chatId, String nextMenu, Api api, String buttons) {


		SetNavigationButtonOutMessage navMsg = new SetNavigationButtonOutMessage();

		navMsg.setChatId(chatId);
		navMsg.setNavigation_button(buttons);

		api.send(navMsg);

	}

	public static void setAdminNavigationButton(String chatId, String nextMenu, Api api, String buttons) {


		SetNavigationButtonOutMessage navMsg = new SetNavigationButtonOutMessage();

		navMsg.setChatId(chatId);
		navMsg.setNavigation_button(buttons);

		api.send(navMsg);

	}

	public static int getNext() {
		// Single atomic update: the previous read-then-set could let concurrent
		// callers both pass the bound before either reset the counter.
		return seq.updateAndGet(current -> current >= 1000 ? 1 : current + 1);
	}

	public static String getUniqueId() {
		return String.valueOf(Calendar.getInstance().getTimeInMillis()) + getNext();
	}

	public static String formatDate(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd,HH:mm:ss");
		return format.format(date);
	}
	public static Map<String,String> getFieldsAndValues(List<MenuCallback.Cell> cells){
		Map<String, String> result = new HashMap<>();
		if (cells == null) return result;
		for (MenuCallback.Cell cell : cells) {
			String key = cell.getCallback();
			String valueStr = "";
			List<MenuCallback.CellValue> values = cell.getValue();
			if (values != null && !values.isEmpty()) {
				Object val = values.get(0).getValue();
				valueStr = val != null ? val.toString() : "";
			}
			result.put(key, valueStr);
		}
		return result;

	}
	public static boolean getBoolean(Object o) {
		if (o == null)
			return false;
		if (o instanceof Boolean)
			return (Boolean) o;
		if (o instanceof Integer)
			return ((int) o != 0);
		if (o instanceof String)
			return !o.toString().equals("0");
		return false;
	}

	/**
	 * Converts a JSON value to a {@code long}, returning {@code 0} when the value
	 * is absent or is not a parseable number.
	 */
	public static long getLong(Object o) {
		if (o == null)
			return 0l;
		// Covers Integer, Long, Double and BigDecimal, all of which json-smart can
		// produce for the same field depending on the literal it parsed.
		if (o instanceof Number)
			return ((Number) o).longValue();
		try {
			return Long.parseLong(o.toString().trim());
		} catch (NumberFormatException e) {
			return 0l;
		}
	}

	/**
	 * Converts a JSON value to an {@code int}, returning {@code 0} when the value
	 * is absent or is not a parseable number.
	 */
	public static int getInteger(Object o) {
		if (o == null)
			return 0;
		if (o instanceof Number)
			return ((Number) o).intValue();
		try {
			return Integer.parseInt(o.toString().trim());
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	public static boolean isNotEmpty(String string) {
		return string != null && !string.isEmpty();
	}

	// public static ArrayList<String> getTagsNames(Tag[] tagsDef, List<String>
	// memberTags) {
	//
	// ArrayList<String> result = new ArrayList<String>();
	// for (Iterator iterator = memberTags.iterator(); iterator.hasNext();) {
	// String tagId = (String) iterator.next();
	//
	// result.add(e)
	//
	// }

	// }

}
