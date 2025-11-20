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
import java.time.Instant;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.example.synergy.utils.DateFormatUtil;
import org.example.synergy.utils.TimeZoneUtil;

/**
 * InstantSerializer (format "yyyy-MM-dd HH:mm:ss")
 */
public class InstantSerializer extends JsonSerializer<Instant> {
    
    @Override
    public void serialize(Instant value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern(DateFormatUtil.YEAR_MONTH_DAY_HOUR_MINUTE_SECOND_PATTERN)
                    .withZone(TimeZoneUtil.buildLocalZoneId());
        String str = formatter.format(value);
        gen.writeString(str);
    }
}
