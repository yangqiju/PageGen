package com.joyveb.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

public class DateRangeUtil {
	public static Date getStartOfWeek(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.clear(Calendar.HOUR);
		cal.clear(Calendar.MINUTE);
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
		return DateUtils.setHours(cal.getTime(), 0);

	}

	public static Date[] getWeekRange(Date date) {
		Date weekstart = getStartOfWeek(date);
		Date weekend = DateUtils.addMilliseconds(
				DateUtils.addDays(weekstart, 7), -1);
		return new Date[] { weekstart, weekend };

	}

	public static Date getStartOfDay(Date date) {
		return DateUtils.truncate(date, Calendar.DAY_OF_MONTH);
	}

	public static Date[] getDayRange(Date date) {
		Date start = getStartOfDay(date);
		Date end = DateUtils.addMilliseconds(DateUtils.addDays(start, 1), -1);
		return new Date[] { start, end };
	}

	public static Date getStartOfMonth(Date date) {
		return DateUtils.truncate(date, Calendar.MONDAY);
	}

	public static Date[] getMonthRange(Date date) {
		Date start = getStartOfMonth(date);
		Date end = DateUtils.addMilliseconds(DateUtils.addMonths(start, 1), -1);
		return new Date[] { start, end };
	}

	public static Date getStartOfYear(Date date) {
		return DateUtils.truncate(date, Calendar.YEAR);
	}

	public static Date[] getYearRange(Date date) {
		Date start = getStartOfYear(date);
		Date end = DateUtils.addMilliseconds(DateUtils.addYears(start, 1), -1);
		return new Date[] { start, end };
	}

	public static Date[] getYearRange(int year){
		Calendar c = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			c.setTime(df.parse("2012-01-01 00:00:00"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.set(Calendar.YEAR, year);
		Date start = c.getTime();
		c.set(Calendar.YEAR, year + 1);
		Date end = c.getTime();
		return new Date[] { start, end };
	}
	public static void main(String[] args) {
		Date calcdate = new Date();

		Date start = DateUtils.truncate(calcdate, Calendar.DAY_OF_MONTH);
		Date end = DateUtils.addDays(start, 1);
		System.out.println("calcdate=" + calcdate + ":start=" + start + ":end="
				+ end);
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		try {
			calcdate = df.parse("2012/10/18");

			System.out.println(getStartOfWeek(calcdate));

			Date[] drange = getDayRange(calcdate);
			System.out.println("day::calcdate=" + calcdate + ":start="
					+ drange[0] + ":end=" + drange[1]);

			Date[] range = getWeekRange(calcdate);
			System.out.println("weekly::calcdate=" + calcdate + ":start="
					+ range[0] + ":end=" + range[1]);

			Date[] mrange = getMonthRange(calcdate);
			System.out.println("month::calcdate=" + calcdate + ":start="
					+ mrange[0] + ":end=" + mrange[1]);
			Date[] yrange = getYearRange(calcdate);
			System.out.println("year::calcdate=" + calcdate + ":start="
					+ yrange[0] + ":end=" + yrange[1]);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
