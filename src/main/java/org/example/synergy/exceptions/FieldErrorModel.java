/*
 * VIETTEL SOFTWARE (VTIT)
 *
 * COPYRIGHT NOTICE:
 * All content including source code, documentation, and other information is the property of RFIAS.
 * Unauthorized use, disclosure, reproduction, or distribution is strictly prohibited and may be unlawful.
 * Permission for any use must be obtained in writing from RFIAS.
 */
package org.example.synergy.exceptions;

import java.io.Serial;
import java.io.Serializable;


public record FieldErrorModel(String field, String message) implements Serializable {

    @Serial
    private static final long serialVersionUID = 1386942829173832298L;
}
