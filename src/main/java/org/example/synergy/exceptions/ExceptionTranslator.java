/*
 * VIETTEL SOFTWARE (VTIT)
 *
 * COPYRIGHT NOTICE:
 * All content including source code, documentation, and other information is the property of RFIAS.
 * Unauthorized use, disclosure, reproduction, or distribution is strictly prohibited and may be unlawful.
 * Permission for any use must be obtained in writing from RFIAS.
 */
package org.example.synergy.exceptions;

import java.util.Arrays;
import java.util.Locale;
import java.util.function.BiFunction;
import java.util.function.Function;

import lombok.RequiredArgsConstructor;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import org.apache.commons.lang3.function.TriFunction;

/**
 * exception translator for all throwable classes.
 */
@Component
@RequiredArgsConstructor
public class ExceptionTranslator {
    
    private final MessageSource msg;
    
    /**
     * String.format (must contains 2 variables like %s)
     */
    private static final String DEFAULT_MESSAGE = "%s[%s]";
    
    private static final Locale LOCALE = Locale.ENGLISH;
    
    /**
     * translate exception with cause.
     * <h3>usage:</h3>
     * <pre>
     * ExceptionTranslator exp;
     * Throwable cause;
     * throw exp.translate(NotExistsException::new, cause,"not.exists", id);
     * </pre>
     *
     * @param <T>       translated exception type
     * @param <C>       cause exception type
     * @param throwable {@code (message, cause) => Throwable } function (for example... RuntimeException::new ).
     * @param cause     cause of exception
     * @param code      message code
     * @param args      message arguments
     * @return translated exception
     */
    public <T extends Throwable, C extends Throwable> T translate(BiFunction<String, C, T> throwable, C cause,
            String code, Object... args) {
        String message =
                msg.getMessage(code, args, String.format(LOCALE, DEFAULT_MESSAGE, code, Arrays.toString(args)), LOCALE);
        return throwable.apply(message, cause);
    }
    
    /**
     * translate exception without cause.
     * <h3>usage:</h3>
     * <pre>
     * ExceptionTranslator exp;
     * throw exp.translate(NotExistsException::new, "not.exists", id);
     * </pre>
     *
     * @param <T>       translated exception type
     * @param throwable {@code message => Throwable } function (for example... RuntimeException::new )
     * @param code      message code
     * @param args      message arguments
     * @return translated exception
     */
    public <T extends Throwable> T translate(Function<String, T> throwable, String code, Object... args) {
        return translate((m, c) -> throwable.apply(m), null, code, args);
    }
    
    /**
     * Translate exception with an error key.
     *
     * @param <T>       the type of the exception to be thrown
     * @param throwable a function to create a new exception
     * @param code      message code that corresponds to the message in MessageSource
     * @param errorKey  the error key to be associated with the exception
     * @param args      arguments for the message template
     * @return an exception of type T with localized message and error key
     */
    public <T extends Throwable> T translateWithErrorKey(BiFunction<String, String, T> throwable, String code,
            String errorKey, Object... args) {
        String message =
                msg.getMessage(code, args, String.format(DEFAULT_MESSAGE, code, Arrays.toString(args)), LOCALE);
        return throwable.apply(message, errorKey);
    }
    
    /**
     * Translate exception with an error key.
     *
     * @param <T>       the type of the exception to be thrown
     * @param throwable a function to create a new exception
     * @param code      message code that corresponds to the message in MessageSource
     * @param errorKey  the error key to be associated with the exception
     * @return an exception of type T with localized message and error key
     */
    public <T extends Throwable> T translateWithErrorKey(BiFunction<String, String, T> throwable, String code,
            String errorKey) {
        return throwable.apply(msg.getMessage(code, null, code, LOCALE), errorKey);
    }
    
    /**
     * Translates an exception by adding a localized message and an error key using a TriFunction.
     *
     * @param <T>       Type of the exception to be thrown.
     * @param <C>       Type of the cause of the exception.
     * @param throwable TriFunction to create a new exception with message, cause, and error key.
     * @param cause     The cause of the exception.
     * @param code      Message code for localization.
     * @param errorKey  Error key associated with the exception.
     * @param args      Arguments for the message template.
     * @return          A new exception of type T.
     */
    public <T extends Throwable, C extends Throwable> T translateWithErrorKey(
            TriFunction<String, C, String, T> throwable, C cause, String code, String errorKey, Object... args) {
        String message =
                msg.getMessage(code, args, String.format(DEFAULT_MESSAGE, code, Arrays.toString(args)), LOCALE);
        return throwable.apply(message, cause, errorKey);
    }
}
