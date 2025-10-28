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

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

/**
 * BooleanDeserializer
 */
public class BooleanDeserializer extends JsonDeserializer<Boolean> {
    
    @Override
    public Boolean deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException, JacksonException {
        String value = jsonParser.getText();
        
        if (value == null || value.trim().isEmpty()) {
            return true;
        }
        
        value = value.trim().toLowerCase();  // Normalize the input to lowercase
        return switch (value) {
            case "true" -> true;
            case "false" -> false;
            default ->
                throw new InvalidFormatException(jsonParser, "Value must be either 'true' or 'false'", value,
                        Boolean.class);
        };
    }
}
