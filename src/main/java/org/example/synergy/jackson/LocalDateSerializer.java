/*
 * VIETTEL SOFTWARE (VTIT)
 *
 * COPYRIGHT NOTICE:
 * All content including source code, documentation, and other information is the property of RFIAS.
 * Unauthorized use, disclosure, reproduction, or distribution is strictly prohibited and may be unlawful.
 * Permission for any use must be obtained in writing from RFIAS.
 */
package org.example.synergy.jackson;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.example.synergy.util.DateFormatUtil;
import org.example.synergy.util.TimeZoneUtil;

/**
 * LocalTimeSerializer (format "HH:mm:ss")
 */
public class LocalDateSerializer extends JsonSerializer<LocalDate> {
    
    @Override
    public void serialize(LocalDate value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern(DateFormatUtil.YEAR_MONTH_DAY_DASH_PATTERN)
                    .withZone(TimeZoneUtil.buildLocalZoneId());
        String str = formatter.format(value);
        gen.writeString(str);
    }
}
