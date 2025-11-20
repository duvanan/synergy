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
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.example.synergy.utils.DateFormatUtil;
import org.example.synergy.utils.TimeZoneUtil;

/**
 * LocalTimeDeserializer (format "HH:mm:ss")
 */
public class LocalTimeDeserializer extends JsonDeserializer<LocalTime> {
    
    @Override
    public LocalTime deserialize(JsonParser p, DeserializationContext deserializationContext) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DateFormatUtil.HOUR_MINUTE_SECOND_PATTERN)
            .withZone(TimeZoneUtil.buildLocalZoneId());
        return LocalTime.from(formatter.parse(p.getText()));
    }
}
