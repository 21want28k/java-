package utils;

import java.time.*;
import java.util.Date;

public class DateUtils {

    public static ZoneId zoneId = ZoneId.systemDefault();

    private static Date getDate(ZonedDateTime zonedDateTime) {
        Instant instant = zonedDateTime.toInstant();
        return Date.from(instant);
    }

    private static ZonedDateTime getZonedDateTime(Date date) {
        Instant instant = date.toInstant();
        return ZonedDateTime.ofInstant(instant, zoneId);
    }

    public static Date asDate(LocalDate localDate) {
        ZonedDateTime zonedDateTime = localDate.atStartOfDay(zoneId);
        return getDate(zonedDateTime);
    }


    public static Date asDate(LocalDateTime localDateTime) {
        ZonedDateTime zonedDateTime = localDateTime.atZone(zoneId);
        return getDate(zonedDateTime);
    }

    /**
     * date to localDate
     */
    public static LocalDate asLocalDate(Date date) {
        return getZonedDateTime(date).toLocalDate();
    }

    /**
     * date->LocalDateTime
     */
    public static LocalDateTime asLocalDateTime(Date date) {
        return getZonedDateTime(date).toLocalDateTime();
    }

}