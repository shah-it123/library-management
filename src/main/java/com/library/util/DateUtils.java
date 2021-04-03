/*
 * 
 */
package com.library.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * The Class DateUtils.
 */
public class DateUtils {
	
	/**
	 * As date.
	 *
	 * @param localDate the local date
	 * @return the date
	 */
	public static Date asDate(LocalDate localDate) {
	    return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	  }

	  /**
  	 * As date.
  	 *
  	 * @param localDateTime the local date time
  	 * @return the date
  	 */
  	public static Date asDate(LocalDateTime localDateTime) {
	    return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	  }

	  /**
  	 * As local date.
  	 *
  	 * @param date the date
  	 * @return the local date
  	 */
  	public static LocalDate asLocalDate(Date date) {
	    return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
	  }

	  /**
  	 * As local date time.
  	 *
  	 * @param date the date
  	 * @return the local date time
  	 */
  	public static LocalDateTime asLocalDateTime(Date date) {
	    return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
	  }
}
