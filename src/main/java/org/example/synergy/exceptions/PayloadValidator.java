/*
 * VIETTEL SOFTWARE (VTIT)
 *
 * COPYRIGHT NOTICE:
 * All content including source code, documentation, and other information is the property of RFIAS.
 * Unauthorized use, disclosure, reproduction, or distribution is strictly prohibited and may be unlawful.
 * Permission for any use must be obtained in writing from RFIAS.
 */
package org.example.synergy.exceptions;

/**
 * Payload Validator Interface
 */
public interface PayloadValidator {
    
    /**
     * Validate object.
     *
     * @param object validation target object
     * @param groups validate with validation groups.
     * @throws InvalidPayloadException if validation failed
     */
    void validate(Object object, Class<?>... groups);
}
