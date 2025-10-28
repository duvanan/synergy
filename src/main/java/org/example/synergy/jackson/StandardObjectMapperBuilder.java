/*
 * VIETTEL SOFTWARE (VTIT)
 *
 * COPYRIGHT NOTICE:
 * All content including source code, documentation, and other information is the property of RFIAS.
 * Unauthorized use, disclosure, reproduction, or distribution is strictly prohibited and may be unlawful.
 * Permission for any use must be obtained in writing from RFIAS.
 */
package org.example.synergy.jackson;

import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import org.zalando.problem.jackson.ProblemModule;
import org.zalando.problem.violations.ConstraintViolationProblemModule;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * StandardObjectMapperBuilder
 */
public class StandardObjectMapperBuilder extends Jackson2ObjectMapperBuilder {
    
    /**
     * Create instance.
     */
    public StandardObjectMapperBuilder() {
        modules(new Jdk8Module(),
                new JavaTimeModule(),
                new ProblemModule(),
                new ConstraintViolationProblemModule());
        propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        featuresToDisable(MapperFeature.AUTO_DETECT_SETTERS);
        featuresToEnable(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS);
        featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }
}
