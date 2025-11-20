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

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.example.synergy.utils.DateFormatUtil;
import org.example.synergy.utils.TimeZoneUtil;

/**
 * InstantDeserializer (format "yyyy-MM-dd HH:mm:ss")
 */
public class InstantDeserializer extends JsonDeserializer<Instant> {
    
    @Override
    public Instant deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern(DateFormatUtil.YEAR_MONTH_DAY_HOUR_MINUTE_SECOND_PATTERN)
                    .withZone(TimeZoneUtil.buildLocalZoneId());
        return Instant.from(formatter.parse(p.getText()));
    }
}
