package com.nandbox.bots.api.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.nandbox.bots.api.Nandbox.Api;
import com.nandbox.bots.api.data.Button;
import com.nandbox.bots.api.outmessages.SetNavigationButtonOutMessage;

/**
 * Media Utility CLass
 * 
 * @author Hossam
 *
 */
public class Utils {

	public enum MediaType {
		text, image, video, audio, file,voice, textFile, contact, location, gif_video, gif_image, sticker, article
	};

	static AtomicInteger seq = new AtomicInteger();

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

	public void setNavigationButton(String chatId, String nextMenu, Api api) {

		Button fb = new Button();
		fb.setNextMenu(nextMenu);
		SetNavigationButtonOutMessage navMsg = new SetNavigationButtonOutMessage();
		navMsg.setChatId(chatId);
		navMsg.setNavigationButton(fb);

		api.send(navMsg);

	}

	public static int getNext() {
		int nextVal = seq.incrementAndGet();
		if (nextVal > 1000)
			seq.set(0);
		return nextVal;
	}

	public static long getUniqueId() {
		return Long.parseLong(String.valueOf(Calendar.getInstance().getTimeInMillis()) + getNext());
	}

	public static String formatDate(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd,HH:mm:ss");
		return format.format(date);
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

	public static long getLong(Object o) {
		if (o == null)
			return 0l;
		if (o instanceof Long)
			return (long) o;
		if (o instanceof Integer)
			return (int) o;
		return Long.parseLong(o.toString());
	}

	public static int getInteger(Object o) {
		if (o == null)
			return 0;
		if (o instanceof Integer)
			return (int) o;
		return Integer.parseInt(o.toString());
	}

	public static boolean isNotEmpty(String string) {
		return !"".equals(string);
	}

}
