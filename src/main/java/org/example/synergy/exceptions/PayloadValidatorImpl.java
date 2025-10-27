/*
 * VIETTEL SOFTWARE (VTIT)
 *
 * COPYRIGHT NOTICE:
 * All content including source code, documentation, and other information is the property of RFIAS.
 * Unauthorized use, disclosure, reproduction, or distribution is strictly prohibited and may be unlawful.
 * Permission for any use must be obtained in writing from RFIAS.
 */
package org.example.synergy.exceptions;

import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

/**
 * Payload Validator Implementation
 */
@RequiredArgsConstructor
public class PayloadValidatorImpl implements PayloadValidator {
    
    private final Validator validator;
    
    @Override
    public void validate(Object object, Class<?>... groups) {
        Set<ConstraintViolation<Object>> violations = validator.validate(object, groups);
        if (!violations.isEmpty()) {
            String message = violations.stream()
                .sorted(Comparator.comparing(v -> v.getPropertyPath().toString()))
                .map(v -> v.getPropertyPath() + ":" + v.getMessage())
                .collect(Collectors.joining(","));
            throw new InvalidPayloadException(message);
        }
    }
}
