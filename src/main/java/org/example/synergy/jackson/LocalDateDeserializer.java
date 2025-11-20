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

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.example.synergy.utils.DateFormatUtil;

/**
 * LocalTimeDeserializer (format "HH:mm:ss")
 */
public class LocalDateDeserializer extends JsonDeserializer<LocalDate> {
    
    @Override
    public LocalDate deserialize(JsonParser p, DeserializationContext deserializationContext) throws IOException {
        return LocalDate.parse(p.getText(), DateFormatUtil.DAY_MONTH_YEAR_DASH_FORMATTER);
    }
}
