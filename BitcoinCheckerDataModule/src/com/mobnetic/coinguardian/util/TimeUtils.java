package com.mobnetic.coinguardian.util;

public class TimeUtils {

	public final static long NANOS_IN_MILLIS = 1000;
	public final static long MILLIS_IN_SECOND = 1000;
	public final static long MILLIS_IN_MINUTE = 60*MILLIS_IN_SECOND;
	public final static long MILLIS_IN_HOUR = 60*MILLIS_IN_MINUTE;
	public final static long MILLIS_IN_DAY = 24*MILLIS_IN_HOUR;
	private final static long MILLIS_IN_YEAR = 365*MILLIS_IN_DAY;
	
	public static long parseTimeToMillis(long time) {
		if(time<MILLIS_IN_YEAR)
			return time*MILLIS_IN_SECOND;
		else if(time>5000*MILLIS_IN_YEAR)
			return time/1000;
		return time;
	}
}
