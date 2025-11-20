/*
 * VIETTEL SOFTWARE (VTIT)
 *
 * COPYRIGHT NOTICE:
 * All content including source code, documentation, and other information is the property of RFIAS.
 * Unauthorized use, disclosure, reproduction, or distribution is strictly prohibited and may be unlawful.
 * Permission for any use must be obtained in writing from RFIAS.
 */
package org.example.synergy.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAdjusters;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;

@UtilityClass
@Slf4j
public class DateFormatUtil {
    
    public static final DateTimeFormatter DAY_MONTH_YEAR_SLASH_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    public static final DateTimeFormatter DAY_MONTH_YEAR_DASH_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    public static final DateTimeFormatter MONTH_YEAR_SLASH_FORMATTER = DateTimeFormatter.ofPattern("MM/yyyy");
    
    public static final DateTimeFormatter MONTH_YEAR_FORMATTER = DateTimeFormatter.ofPattern("MMyyyy");
    
    public static final DateTimeFormatter YEAR_MONTH_SLASH_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM");
    
    public static final DateTimeFormatter YEAR_MONTH_DAY_HOUR_MINUTE_SECOND_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    public static final String YEAR_MONTH_DAY_HOUR_MINUTE_SECOND_PATTERN = "yyyy-MM-dd HH:mm:ss";
    
    public static final String DAY_MONTH_YEAR_HOUR_MINUTE_SECOND_PATTERN = "dd/MM/yyyy HH:mm:ss";
    
    public static final String DAY_MONTH_YEAR_SLASH_PATTERN = "dd/MM/yyyy";
    
    public static final String YEAR_MONTH_DAY_SLASH_PATTERN = "yyyy/MM/dd";
    
    public static final String YEAR_MONTH_DAY_DASH_PATTERN = "yyyy-MM-dd";
    
    public static final String HOUR_MINUTE_SECOND_PATTERN = "HH:mm:ss";
    
    public String formatInstant(Instant instant, boolean emptyIfNull) {
        if (instant == null) {
            if (emptyIfNull) {
                return "";
            } else {
                throw new IllegalArgumentException("instant cannot be null");
            }
        }
        
        DateTimeFormatter formatter = DAY_MONTH_YEAR_SLASH_FORMATTER.withZone(TimeZoneUtil.buildLocalZoneId());
        return formatter.format(instant);
    }
    
    public String formatInstant(Instant instant) {
        if (instant == null) {
            return "";
        }
        
        DateTimeFormatter formatter =
                YEAR_MONTH_DAY_HOUR_MINUTE_SECOND_FORMATTER.withZone(TimeZoneUtil.buildLocalZoneId());
        return formatter.format(instant);
    }
    
    public String formatMonthYearInstant(Instant instant) {
        if (instant == null) {
            throw new IllegalArgumentException("instant cannot be null");
        }
        
        DateTimeFormatter formatter = MONTH_YEAR_FORMATTER.withZone(TimeZoneUtil.buildLocalZoneId());
        return formatter.format(instant);
    }
    
    public String formatDateTimeInstant(Instant instant, boolean emptyIfNull) {
        if (instant == null) {
            if (emptyIfNull) {
                return "";
            } else {
                throw new IllegalArgumentException("instant cannot be null");
            }
        }
        
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern(DateFormatUtil.DAY_MONTH_YEAR_HOUR_MINUTE_SECOND_PATTERN).withZone(
                        TimeZoneUtil.buildLocalZoneId());
        
        return formatter.format(instant);
    }
    
    public String formatDateTimeInstant(Instant instant) {
        if (instant == null) {
            return "";
        }
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DateFormatUtil.DAY_MONTH_YEAR_SLASH_PATTERN).withZone(
                TimeZoneUtil.buildLocalZoneId());
        
        return formatter.format(instant);
    }
    
    public String formatLocalDate(LocalDate localDate, boolean emptyIfNull) {
        if (localDate == null) {
            if (emptyIfNull) {
                return "";
            } else {
                throw new IllegalArgumentException("instant cannot be null");
            }
        }
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DateFormatUtil.DAY_MONTH_YEAR_SLASH_PATTERN).withZone(
                TimeZoneUtil.buildLocalZoneId());
        
        return formatter.format(localDate);
    }
    
    public String formatLocalDateWithPattern(LocalDate localDate, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern).withZone(TimeZoneUtil.buildLocalZoneId());
        
        return formatter.format(localDate);
    }
    
    public String fromLocalDate(LocalDate localDate) {
        if (localDate == null) {
            throw new IllegalArgumentException("localDate cannot be null");
        }
        
        DateTimeFormatter formatter = DAY_MONTH_YEAR_SLASH_FORMATTER.withZone(TimeZoneUtil.buildLocalZoneId());
        return formatter.format(localDate);
    }
    
    public Instant convertStringDateToInstant(String dateTimeString) {
        if (StringUtils.isBlank(dateTimeString)) {
            return null;
        }
        
        try {
            LocalDate localDate = LocalDate.parse(dateTimeString, DAY_MONTH_YEAR_SLASH_FORMATTER);
            LocalDateTime localDateTime = localDate.atStartOfDay();
            return localDateTime.atZone(TimeZoneUtil.buildLocalZoneId()).toInstant();
        } catch (DateTimeParseException e) {
            return null;
        }
    }
    
    public String formatMonthYear(LocalDate localDate) {
        if (localDate == null) {
            throw new IllegalArgumentException("localDate cannot be null");
        }
        
        DateTimeFormatter formatter = MONTH_YEAR_SLASH_FORMATTER.withZone(TimeZoneUtil.buildLocalZoneId());
        return formatter.format(localDate);
    }
    
    public String formatYearMonth(LocalDate localDate) {
        if (localDate == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        
        DateTimeFormatter formatter = YEAR_MONTH_SLASH_FORMATTER.withZone(TimeZoneUtil.buildLocalZoneId());
        return localDate.format(formatter);
    }
    
    public String formatCurrentYearMonth() {
        DateTimeFormatter formatter = YEAR_MONTH_SLASH_FORMATTER.withZone(TimeZoneUtil.buildLocalZoneId());
        return LocalDate.now().format(formatter);
    }
    
    public Instant formatStringToInstant(String dateString) {
        LocalDate localDate = formatStringToLocalDate(dateString);
        
        if (localDate == null) {
            throw new IllegalArgumentException("localDate cannot be null");
        }
        
        ZonedDateTime zonedDateTime = localDate.atStartOfDay(TimeZoneUtil.buildLocalZoneId());
        return zonedDateTime.toInstant();
    }
    
    public LocalDate formatStringToLocalDate(String dateString) {
        if (StringUtils.isEmpty(dateString)) {
            throw new IllegalArgumentException("dateString cannot be null");
        }
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DAY_MONTH_YEAR_SLASH_PATTERN);
        try {
            return LocalDate.parse(dateString, formatter);
        } catch (DateTimeParseException e) {
            return null;
        }
    }
    
    public LocalDate formatStringToLocalDate(String dateString, boolean emptyIfNull) {
        if (StringUtils.isEmpty(dateString)) {
            if (emptyIfNull) {
                return null;
            } else {
                throw new IllegalArgumentException("dateString cannot be null");
            }
        }
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DAY_MONTH_YEAR_SLASH_PATTERN);
        try {
            return LocalDate.parse(dateString, formatter);
        } catch (DateTimeParseException e) {
            return null;
        }
    }
    
    public boolean isValidDateFormat(String dateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DAY_MONTH_YEAR_SLASH_PATTERN);
        
        try {
            LocalDate date = LocalDate.parse(dateStr, formatter);
            return dateStr.equals(date.format(formatter));
        } catch (DateTimeParseException e) {
            return false;
        }
    }
    
    public LocalDate getCurrentDate() {
        ZonedDateTime zonedDateTime = ZonedDateTime.now(TimeZoneUtil.buildLocalZoneId());
        return zonedDateTime.toLocalDate();
    }
    
    public String convertToYearMonthDay(String date) {
        DateTimeFormatter originalFormat = DateTimeFormatter.ofPattern(DAY_MONTH_YEAR_SLASH_PATTERN);
        DateTimeFormatter desiredFormat = DateTimeFormatter.ofPattern(YEAR_MONTH_DAY_DASH_PATTERN);
        LocalDate localDate = LocalDate.parse(date, originalFormat);
        return localDate.format(desiredFormat);
    }
    
    public String convertToYearMonth(String input) {
        if (StringUtils.isBlank(input)) {
            return input;
        }
        
        YearMonth yearMonth = YearMonth.parse(input, MONTH_YEAR_SLASH_FORMATTER);
        return yearMonth.format(YEAR_MONTH_SLASH_FORMATTER);
    }
    
    public String getFirstDayOfCurrentMonth() {
        ZonedDateTime zonedDateTime = ZonedDateTime.now(TimeZoneUtil.buildLocalZoneId());
        LocalDate firstDay = zonedDateTime.with(TemporalAdjusters.firstDayOfMonth()).toLocalDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(YEAR_MONTH_DAY_DASH_PATTERN);
        return firstDay.format(formatter);
    }
    
    public String getLastDayOfCurrentMonth() {
        ZonedDateTime zonedDateTime = ZonedDateTime.now(TimeZoneUtil.buildLocalZoneId());
        LocalDate lastDay = zonedDateTime.with(TemporalAdjusters.lastDayOfMonth()).toLocalDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(YEAR_MONTH_DAY_DASH_PATTERN);
        return lastDay.format(formatter);
    }
    
    public boolean isFutureMonth(String dateStr) {
        try {
            YearMonth inputDate = YearMonth.parse(dateStr, MONTH_YEAR_SLASH_FORMATTER);
            YearMonth currentDate = YearMonth.now();
            return inputDate.isAfter(currentDate);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format: " + e.getMessage());
        }
    }
    
    public boolean matchesDatePattern(String date) {
        String regex = "^\\d{2}/\\d{2}/\\d{4}$";
        return date.matches(regex);
    }
    
    public String getCurrentYear() {
        YearMonth yearMonth = YearMonth.from(ZonedDateTime.now(TimeZoneUtil.buildLocalZoneId()));
        return String.valueOf(yearMonth.getYear());
    }
    
    public String getCurrentMonth() {
        YearMonth yearMonth = YearMonth.from(ZonedDateTime.now(TimeZoneUtil.buildLocalZoneId()));
        return String.valueOf(yearMonth.getMonthValue());
    }
    
    public int getYearFromInstant(Instant instant) {
        ZonedDateTime zonedDateTime = instant.atZone(TimeZoneUtil.buildLocalZoneId());
        return zonedDateTime.getYear();
    }
    
    public int getMonthFromInstant(Instant instant) {
        ZonedDateTime zonedDateTime = instant.atZone(TimeZoneUtil.buildLocalZoneId());
        return zonedDateTime.getMonthValue();
    }
    
    public LocalDate convertInstantToLocalDate(Instant instant) {
        if (instant == null) {
            return null;
        }
        
        return instant.atZone(TimeZoneUtil.buildLocalZoneId()).toLocalDate();
    }
    
    public Instant convertLocalDateToInstant(LocalDate localDate) {
        if (localDate == null) {
            return null;
        }
        
        return localDate.atStartOfDay(TimeZoneUtil.buildLocalZoneId()).toInstant();
    }
    
    public Instant getCurrentDateTime() {
        ZonedDateTime zonedDateTime = ZonedDateTime.now(TimeZoneUtil.buildLocalZoneId());
        return zonedDateTime.toInstant();
    }
    
    public long toTimestamp(Instant measureTime) {
        if (measureTime == null) {
            return 0;
        }
        
        ZonedDateTime zonedDateTime = measureTime.atZone(TimeZoneUtil.buildLocalZoneId());
        return zonedDateTime.toInstant().toEpochMilli();
    }
    
    public int getHourFromInstant(Instant instant) {
        ZonedDateTime zonedDateTime = instant.atZone(TimeZoneUtil.buildLocalZoneId());
        return zonedDateTime.getHour();
    }
    
    public static boolean isValidLocalDateFormat(String dateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DateFormatUtil.DAY_MONTH_YEAR_SLASH_PATTERN);
        try {
            LocalDate.parse(dateStr, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
    
    public static boolean isEffectiveAfterExpiryDate(String startDate, String endDate) {
        if (startDate == null || endDate == null) {
            return false;
        }
        try {
            LocalDate start = LocalDate.parse(startDate.trim(),
                    DateTimeFormatter.ofPattern(DateFormatUtil.DAY_MONTH_YEAR_SLASH_PATTERN));
            LocalDate end = LocalDate.parse(endDate.trim(),
                    DateTimeFormatter.ofPattern(DateFormatUtil.DAY_MONTH_YEAR_SLASH_PATTERN));
            if (start.equals(end)) {
                return true;
            }
            return start.isBefore(end);
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
