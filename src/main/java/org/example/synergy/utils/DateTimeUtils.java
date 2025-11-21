package org.example.synergy.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.xml.datatype.XMLGregorianCalendar;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

@Slf4j
public class DateTimeUtils {

    private DateTimeUtils() {
    }

    public static final String STANDARD_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
    public static final String DEFAULT_DATE_FORMAT = "dd/MM/yyyy";
    public static final String DEFAULT_DATE_FILE_NAME_FORMAT = "ddMMyyyy";
    public static final String DEFAULT_DATE_HOUR_EXPORT_FORMAT = "HH:mm:ss dd/MM/yyyy";
    public static final String DEFAULT_DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm:ss";
    public static final String DEFAULT_DATE_HOUR_FILE_FORMAT = "yyyyMMdd_HHmmss";
    public static final String DATE_FORMAT = "dd_MM_yyyy";
    public static final String SIMPLE_DATE_FORMAT = "yyyyMMdd";

    private static final LocalDate DATE_2007 = LocalDate.of(2007, 1, 1);

    private static final DateTimeFormatter[] DATE_FORMATTERS = {
            DateTimeFormatter.ofPattern("d/M/yyyy"),
            DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT),
            DateTimeFormatter.ofPattern("M/d/yyyy"),
            DateTimeFormatter.ofPattern("MM/dd/yyyy"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd"),
            DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)
    };

    public static String getStringFormat(String pattern, Date date) {
        if (date == null) {
            return null;
        }
        DateFormat formatter = new SimpleDateFormat(pattern);
        return formatter.format(date);
    }

    public static String getStringFormat(String pattern, LocalDate date) {
        if (date == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return formatter.format(date);
    }

    public static boolean isDateOnOrAfter2007(String dateString) {
        for (DateTimeFormatter formatter : DATE_FORMATTERS) {
            try {
                LocalDate dateToCheck = LocalDate.parse(dateString, formatter);
                return !dateToCheck.isBefore(DATE_2007);
            } catch (DateTimeParseException e) {
                log.error(e.getMessage());
            }
        }
        return false;
    }

    public static boolean isEndDateAfterStartDate(String startDateStr, String endDateStr, String pattern) {
        if (startDateStr == null || endDateStr == null) {
            return false;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        try {
            LocalDate startDate = LocalDate.parse(startDateStr, formatter);
            LocalDate endDate = LocalDate.parse(endDateStr, formatter);

            return endDate.isAfter(startDate);
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static LocalDateTime convertXMLGregorianCalendar(XMLGregorianCalendar xmlGregorianCalendar) {
        if (xmlGregorianCalendar == null) {
            return null;
        }
        ZoneId zoneId = ZoneId.of("Asia/Ho_Chi_Minh");
        ZonedDateTime zonedDateTime = xmlGregorianCalendar.toGregorianCalendar().toZonedDateTime().withZoneSameInstant(zoneId);
        return zonedDateTime.toLocalDateTime();
    }

    public static Date convertStringToDateTime(String dateString) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
            sdf.setLenient(false);
            return sdf.parse(dateString);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Instant convertToInstant(String dateStr) {
        if (ObjectUtils.isEmpty(dateStr)) {
            return null;
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT);
            LocalDate localDate = LocalDate.parse(dateStr.trim(), formatter);
            return localDate.atStartOfDay(ZoneOffset.UTC).toInstant();
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Định dạng ngày không hợp lệ: " + dateStr, e);
        }
    }

    public static Date convertXMLGregorianCalendarToDate(XMLGregorianCalendar xmlGregorianCalendar) {
        if (xmlGregorianCalendar == null) {
            return null;
        }
        return xmlGregorianCalendar.toGregorianCalendar().getTime();
    }

    public static LocalDateTime convertTimestampToLocalDateTime(Timestamp timestamp) {
        return Objects.isNull(timestamp) ? null : timestamp.toLocalDateTime();
    }

    public static boolean isValid(String dateStr, String pattern) {
        if (!StringUtils.hasText(dateStr)) {
            return true;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        sdf.setLenient(false);

        try {
            sdf.parse(dateStr);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static Date addDays(final Date date, final int amount) {
        return add(date, amount);
    }

    private static Date add(final Date date, final int amount) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, amount);
        return c.getTime();
    }

    public static Date toDate(LocalDateTime ldt) {
        if (ldt == null) {
            return null; // check null
        }
        return Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDate convertMonthYearToLocalDate(String monthYear) {
        return LocalDate.parse("01/" + monthYear, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public static boolean isBetweenMonthYear(LocalDate target, LocalDate start, LocalDate end) {
        if (target == null || start == null || end == null) {
            return false;
        }

        YearMonth targetYm = YearMonth.from(target);
        YearMonth startYm = YearMonth.from(start);
        YearMonth endYm   = YearMonth.from(end);

        return (targetYm.equals(startYm) || targetYm.isAfter(startYm)) &&
                (targetYm.equals(endYm)   || targetYm.isBefore(endYm));
    }

    public static String convertToMonthYear(String dateStr, String format) {
        if (dateStr == null || dateStr.isBlank()) {
            return null;
        }

        DateTimeFormatter inputFmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter outputFmt = DateTimeFormatter.ofPattern(format);

        try {
            LocalDate date = LocalDate.parse(dateStr, inputFmt);
            return date.format(outputFmt);
        } catch (DateTimeParseException e) {
            return null;
        }
    }
}
