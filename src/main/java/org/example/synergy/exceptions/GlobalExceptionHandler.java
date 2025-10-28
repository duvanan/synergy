///*
// * VIETTEL SOFTWARE (VTIT)
// *
// * COPYRIGHT NOTICE:
// * All content including source code, documentation, and other information is the property of RFIAS.
// * Unauthorized use, disclosure, reproduction, or distribution is strictly prohibited and may be unlawful.
// * Permission for any use must be obtained in writing from RFIAS.
// */
//package org.example.synergy.exceptions;
//
//import static org.springframework.core.annotation.AnnotatedElementUtils.findMergedAnnotation;
//
//import java.io.IOException;
//import java.net.URI;
//import java.util.Arrays;
//import java.util.Collection;
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Locale;
//import java.util.Map;
//import java.util.Objects;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//import org.springframework.beans.BeanWrapper;
//import org.springframework.beans.ConversionNotSupportedException;
//import org.springframework.beans.NotReadablePropertyException;
//import org.springframework.beans.NotWritablePropertyException;
//import org.springframework.beans.TypeMismatchException;
//import org.springframework.core.env.Environment;
//import org.springframework.dao.ConcurrencyFailureException;
//import org.springframework.dao.DataAccessException;
//import org.springframework.data.util.DirectFieldAccessFallbackBeanWrapper;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.HttpStatusCode;
//import org.springframework.http.ResponseEntity;
//import org.springframework.http.converter.HttpMessageConversionException;
//import org.springframework.http.converter.HttpMessageNotReadableException;
//import org.springframework.http.converter.HttpMessageNotWritableException;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.validation.method.MethodValidationException;
//import org.springframework.web.ErrorResponse;
//import org.springframework.web.ErrorResponseException;
//import org.springframework.web.HttpMediaTypeNotAcceptableException;
//import org.springframework.web.HttpMediaTypeNotSupportedException;
//import org.springframework.web.HttpRequestMethodNotSupportedException;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.MissingPathVariableException;
//import org.springframework.web.bind.MissingServletRequestParameterException;
//import org.springframework.web.bind.ServletRequestBindingException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.context.request.NativeWebRequest;
//import org.springframework.web.context.request.WebRequest;
//import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
//import org.springframework.web.method.annotation.HandlerMethodValidationException;
//import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
//import org.springframework.web.multipart.MaxUploadSizeExceededException;
//import org.springframework.web.multipart.support.MissingServletRequestPartException;
//import org.springframework.web.servlet.NoHandlerFoundException;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
//import org.springframework.web.servlet.resource.NoResourceFoundException;
//
//import org.apache.commons.lang3.StringUtils;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.validation.ConstraintViolationException;
//import tech.jhipster.config.JHipsterConstants;
//import tech.jhipster.web.rest.errors.ProblemDetailWithCause;
//import tech.jhipster.web.rest.errors.ProblemDetailWithCause.ProblemDetailWithCauseBuilder;
//
///**
// * Controller advice to translate the server side exceptions to client-friendly json structures.
// * The error response follows RFC7807 - Problem Details for HTTP APIs (https://tools.ietf.org/html/rfc7807).
// */
//@ControllerAdvice
//public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
//
//    private final Environment env;
//
//    public GlobalExceptionHandler(Environment env) {
//        this.env = env;
//    }
//
//    @ExceptionHandler
//    public ResponseEntity<Object> handleAnyException(Throwable ex, NativeWebRequest request) {
//        return new ResponseEntity<>(customErrorResponse(ex, request), buildHeaders(ex), toStatus(ex).value());
//    }
//
//    protected ProblemDetailWithCause wrapAndCustomizeProblem(Throwable ex, NativeWebRequest request) {
//        return customizeProblem(getProblemDetailWithCause(ex), ex, request);
//    }
//
//    private ProblemDetailWithCause getProblemDetailWithCause(Throwable ex) {
//        if (ex instanceof ErrorResponseException exp
//                && exp.getBody() instanceof ProblemDetailWithCause problemDetailWithCause)
//            return problemDetailWithCause;
//        return ProblemDetailWithCauseBuilder.instance().withStatus(toStatus(ex).value()).build();
//    }
//
//    protected ProblemDetailWithCause customizeProblem(ProblemDetailWithCause problem, Throwable err,
//            NativeWebRequest request) {
//        if (problem.getStatus() <= 0)
//            problem.setStatus(toStatus(err));
//
//        if (problem.getType() == null || problem.getType().equals(URI.create("about:blank")))
//            problem.setType(getMappedType(err));
//
//        // higher precedence to Custom/ResponseStatus types
//        String title = extractTitle(err, problem.getStatus());
//        String problemTitle = problem.getTitle();
//        if (problemTitle == null || !problemTitle.equals(title)) {
//            problem.setTitle(title);
//        }
//
//        if (problem.getDetail() == null) {
//            // higher precedence to cause
//            problem.setDetail(getCustomizedErrorDetails(err));
//        }
//
//        Map<String, Object> problemProperties = problem.getProperties();
//        if (problemProperties == null || !problemProperties.containsKey(ErrorConstants.MESSAGE_KEY))
//            problem.setProperty(
//                    ErrorConstants.MESSAGE_KEY,
//                    getMappedMessageKey(err) != null ? getMappedMessageKey(err) : "error.http." + problem.getStatus());
//
//        if (problemProperties == null || !problemProperties.containsKey(ErrorConstants.PATH_KEY))
//            problem.setProperty(ErrorConstants.PATH_KEY, getPathValue(request));
//
//        if ((err instanceof MethodArgumentNotValidException fieldException) &&
//                (problemProperties == null || !problemProperties.containsKey(ErrorConstants.FIELD_ERRORS_KEY)))
//            problem.setProperty(ErrorConstants.FIELD_ERRORS_KEY, getFieldErrors(fieldException));
//
//        if (problemProperties == null || !problemProperties.containsKey(ErrorConstants.TIMESTAMP_KEY)) {
//            problem.setProperty(ErrorConstants.TIMESTAMP_KEY, System.currentTimeMillis());
//        }
//
//        problem.setCause(buildCause(err.getCause(), request).orElse(null));
//
//        return problem;
//    }
//
//    private String extractTitle(Throwable err, int statusCode) {
//        return getCustomizedTitle(err) != null ? getCustomizedTitle(err)
//                : extractTitleForResponseStatus(err, statusCode);
//    }
//
//    private List<FieldErrorModel> getFieldErrors(MethodArgumentNotValidException ex) {
//        return ex
//            .getBindingResult()
//            .getFieldErrors()
//            .stream()
//            .map(
//                    f -> new FieldErrorModel(
//                            f.getField(),
//                            StringUtils.isNotBlank(f.getDefaultMessage()) ? f.getDefaultMessage() : f.getCode()))
//            .toList();
//    }
//
//    private List<FieldErrorModel> getFieldErrors(ConstraintViolationException ex) {
//        return ex.getConstraintViolations().stream()
//            .map(violation -> new FieldErrorModel(
//                    simplifyPath(violation.getPropertyPath().toString()),
//                    StringUtils.isNotBlank(violation.getMessage()) ? violation.getMessage()
//                            : violation.getMessageTemplate()))
//            .collect(Collectors.toList());
//    }
//
//    private String simplifyPath(String path) {
//        String[] parts = path.split("\\.", 2);
//        if (parts.length > 1) {
//            return parts[1];
//        }
//
//        return path;
//    }
//
//    private String extractTitleForResponseStatus(Throwable err, int statusCode) {
//        ResponseStatus specialStatus = extractResponseStatus(err);
//        return specialStatus == null ? HttpStatus.valueOf(statusCode).getReasonPhrase() : specialStatus.reason();
//    }
//
//    private String extractURI(NativeWebRequest request) {
//        HttpServletRequest nativeRequest = request.getNativeRequest(HttpServletRequest.class);
//        return nativeRequest != null ? nativeRequest.getRequestURI() : StringUtils.EMPTY;
//    }
//
//    private HttpStatus toStatus(final Throwable throwable) {
//        // Let the ErrorResponse take this responsibility
//        if (throwable instanceof ErrorResponse err)
//            return HttpStatus.valueOf(err.getBody().getStatus());
//
//        return Optional.ofNullable(getMappedStatus(throwable)).orElse(
//                Optional.ofNullable(resolveResponseStatus(throwable)).map(ResponseStatus::value)
//                    .orElse(HttpStatus.INTERNAL_SERVER_ERROR));
//    }
//
//    private ResponseStatus extractResponseStatus(final Throwable throwable) {
//        return Optional.ofNullable(resolveResponseStatus(throwable)).orElse(null);
//    }
//
//    private ResponseStatus resolveResponseStatus(final Throwable type) {
//        final ResponseStatus candidate = findMergedAnnotation(type.getClass(), ResponseStatus.class);
//        return candidate == null && type.getCause() != null ? resolveResponseStatus(type.getCause()) : candidate;
//    }
//
//    private URI getMappedType(Throwable err) {
//        if (err instanceof MethodArgumentNotValidException)
//            return ErrorConstants.CONSTRAINT_VIOLATION_TYPE;
//        return ErrorConstants.DEFAULT_TYPE;
//    }
//
//    private String getMappedMessageKey(Throwable err) {
//        if (err instanceof MethodArgumentNotValidException) {
//            return ErrorConstants.ERR_VALIDATION;
//        } else if (err instanceof ConcurrencyFailureException
//                || err.getCause() instanceof ConcurrencyFailureException) {
//            return ErrorConstants.ERR_CONCURRENCY_FAILURE;
//        }
//        return null;
//    }
//
//    private String getCustomizedTitle(Throwable err) {
//        if (err instanceof MethodArgumentNotValidException)
//            return "Method argument not valid";
//        return null;
//    }
//
//    private String getCustomizedErrorDetails(Throwable err) {
//        Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
//        if (activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_PRODUCTION)) {
//            if (err instanceof HttpMessageConversionException)
//                return "Unable to convert http message";
//            if (err instanceof DataAccessException)
//                return "Failure during data access";
//            if (containsPackageName(err.getMessage()))
//                return "Unexpected runtime exception";
//        }
//        return err.getCause() != null ? err.getCause().getMessage() : err.getMessage();
//    }
//
//    private HttpStatus getMappedStatus(Throwable err) {
//        // Where we disagree with Spring defaults
//        if (err instanceof AccessDeniedException)
//            return HttpStatus.FORBIDDEN;
//        if (err instanceof ConcurrencyFailureException)
//            return HttpStatus.CONFLICT;
//        if (err instanceof BadCredentialsException)
//            return HttpStatus.UNAUTHORIZED;
//        return null;
//    }
//
//    private URI getPathValue(NativeWebRequest request) {
//        if (request == null)
//            return URI.create("about:blank");
//        return URI.create(extractURI(request));
//    }
//
//    private HttpHeaders buildHeaders(Throwable err) {
//        return null;
//    }
//
//    public Optional<ProblemDetailWithCause> buildCause(final Throwable throwable, NativeWebRequest request) {
//        if (throwable != null && isCasualChainEnabled()) {
//            return Optional.of(customizeProblem(getProblemDetailWithCause(throwable), throwable, request));
//        }
//        return Optional.ofNullable(null);
//    }
//
//    private boolean isCasualChainEnabled() {
//        // Customize as per the needs
//        return ErrorConstants.CASUAL_CHAIN_ENABLED;
//    }
//
//    private boolean containsPackageName(String message) {
//        // This list is for sure not complete
//        return StringUtils.containsAny(message, "org.", "java.", "net.", "jakarta.", "javax.", "com.", "io.", "de.",
//                "com.viettel.vtit.rfias");
//    }
//
//    @Override
//    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
//            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//        return new ResponseEntity<>(customErrorResponse(ex, (NativeWebRequest) request), headers, status);
//    }
//
//    @Override
//    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
//            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//        return new ResponseEntity<>(customErrorResponse(ex, (NativeWebRequest) request), headers, status);
//    }
//
//    @Override
//    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
//            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//        return new ResponseEntity<>(customErrorResponse(ex, (NativeWebRequest) request), headers, status);
//    }
//
//    @ExceptionHandler({
//        MethodArgumentTypeMismatchException.class
//    })
//    public ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex,
//            NativeWebRequest request) {
//        return new ResponseEntity<>(customErrorResponse(ex, request), buildHeaders(ex), HttpStatus.BAD_REQUEST.value());
//    }
//
//    @Override
//    protected ResponseEntity<Object> handleNoResourceFoundException(NoResourceFoundException ex, HttpHeaders headers,
//            HttpStatusCode status, WebRequest request) {
//        return new ResponseEntity<>(customErrorResponse(ex, (NativeWebRequest) request), headers, status);
//    }
//
//    @Override
//    protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex,
//            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//        return new ResponseEntity<>(customErrorResponse(ex, (NativeWebRequest) request), headers, status);
//    }
//
//    @ExceptionHandler({
//        ConstraintViolationException.class
//    })
//    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex,
//            NativeWebRequest request) {
//        return new ResponseEntity<>(customErrorResponse(ex, request), buildHeaders(ex), HttpStatus.BAD_REQUEST.value());
//    }
//
//    @Override
//    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
//            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//        return new ResponseEntity<>(customErrorResponse(ex, (NativeWebRequest) request), headers, status);
//    }
//
//    @Override
//    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
//            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//        return new ResponseEntity<>(customErrorResponse(ex, (NativeWebRequest) request), headers, status);
//    }
//
//    @Override
//    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex,
//            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//        return new ResponseEntity<>(customErrorResponse(ex, (NativeWebRequest) request), headers, status);
//    }
//
//    @Override
//    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers,
//            HttpStatusCode status, WebRequest request) {
//        return new ResponseEntity<>(customErrorResponse(ex, (NativeWebRequest) request), headers, status);
//    }
//
//    @Override
//    protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex,
//            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//        return new ResponseEntity<>(customErrorResponse(ex, (NativeWebRequest) request), headers, status);
//    }
//
//    @Override
//    protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex,
//            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//        return new ResponseEntity<>(customErrorResponse(ex, (NativeWebRequest) request), headers, status);
//    }
//
//    @Override
//    protected ResponseEntity<Object> handleHandlerMethodValidationException(HandlerMethodValidationException ex,
//            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//        return new ResponseEntity<>(customErrorResponse(ex, (NativeWebRequest) request), headers, status);
//    }
//
//    @Override
//    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
//            HttpStatusCode status, WebRequest request) {
//        return new ResponseEntity<>(customErrorResponse(ex, (NativeWebRequest) request), headers, status);
//    }
//
//    @Override
//    protected ResponseEntity<Object> handleAsyncRequestTimeoutException(AsyncRequestTimeoutException ex,
//            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//        return new ResponseEntity<>(customErrorResponse(ex, (NativeWebRequest) request), headers, status);
//    }
//
//    @Override
//    protected ResponseEntity<Object> handleErrorResponseException(ErrorResponseException ex, HttpHeaders headers,
//            HttpStatusCode status, WebRequest request) {
//        return new ResponseEntity<>(customErrorResponse(ex, (NativeWebRequest) request), headers, status);
//    }
//
//    @Override
//    protected ResponseEntity<Object> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException ex,
//            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//        return new ResponseEntity<>(customErrorResponse(ex, (NativeWebRequest) request), headers, status);
//    }
//
//    @Override
//    protected ResponseEntity<Object> handleConversionNotSupported(ConversionNotSupportedException ex,
//            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//        return new ResponseEntity<>(customErrorResponse(ex, (NativeWebRequest) request), headers, status);
//    }
//
//    @Override
//    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
//            HttpStatusCode status, WebRequest request) {
//        return new ResponseEntity<>(customErrorResponse(ex, (NativeWebRequest) request), headers, status);
//    }
//
//    @Override
//    protected ResponseEntity<Object> handleMethodValidationException(MethodValidationException ex, HttpHeaders headers,
//            HttpStatus status, WebRequest request) {
//        return new ResponseEntity<>(customErrorResponse(ex, (NativeWebRequest) request), headers, status);
//    }
//
//    @Override
//    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
//            HttpStatusCode statusCode, WebRequest request) {
//        return new ResponseEntity<>(customErrorResponse(ex, (NativeWebRequest) request), headers, statusCode);
//    }
//
//    @ExceptionHandler(IOException.class)
//    public ResponseEntity<Object> handleIOException(IOException ex, NativeWebRequest request) {
//        // Do not log with "Broken pipe" error
//        if ("Broken pipe".equals(ex.getMessage())) {
//            return null;
//        }
//
//        return new ResponseEntity<>(customErrorResponse(ex, request), buildHeaders(ex),
//                HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//
//    private Map<String, Object> customErrorResponse(Throwable ex, NativeWebRequest request) {
//        Map<String, Object> errorResponse = new LinkedHashMap<>();
//        HttpStatus httpStatus = toStatus(ex);
//
//        int httpStatusCode = httpStatus.value();
//        long timestamp = System.currentTimeMillis();
//        URI pathValue = getPathValue(request);
//        String error = httpStatus.name().toLowerCase(Locale.ENGLISH);
//        String errorMessage = getCustomizedErrorDetails(ex);
//
//        errorResponse.put(ErrorConstants.TIMESTAMP_KEY, timestamp);
//        errorResponse.put(ErrorConstants.PATH_KEY, pathValue);
//        errorResponse.put(ErrorConstants.ERROR_KEY, error);
//        errorResponse.put(ErrorConstants.ERROR_MESSAGE_KEY, errorMessage);
//
//        BeanWrapper beanWrapper = new DirectFieldAccessFallbackBeanWrapper(ex);
//        Object errorKeyValue = getPropertyValueIfExists(beanWrapper, "errorKey");
//        errorResponse.put(ErrorConstants.ERROR_KEY_KEY,
//                Objects.requireNonNullElse(errorKeyValue, error.toUpperCase(Locale.ENGLISH)));
//
//        if (ex instanceof MethodArgumentNotValidException fieldException) {
//            errorResponse.put(ErrorConstants.FIELD_ERRORS_KEY, getFieldErrors(fieldException));
//            errorResponse.put(ErrorConstants.ERROR_MESSAGE_KEY, "Validation failed");
//            errorResponse.put(ErrorConstants.ERROR_KEY_KEY, HttpStatus.BAD_REQUEST.name().toUpperCase(Locale.ENGLISH));
//        }
//
//        if (ex instanceof HttpMessageNotReadableException) {
//            errorResponse.put(ErrorConstants.ERROR_KEY,
//                    HttpStatus.BAD_REQUEST.name().toLowerCase(Locale.ENGLISH));
//            errorResponse.put(ErrorConstants.ERROR_KEY_KEY, HttpStatus.BAD_REQUEST.name().toUpperCase(Locale.ENGLISH));
//        }
//
//        if (ex instanceof MethodArgumentTypeMismatchException e) {
//            errorResponse.put(ErrorConstants.ERROR_MESSAGE_KEY,
//                    e.getName() + " should be of type " + Objects.requireNonNull(e.getRequiredType()).getName());
//            errorResponse.put(ErrorConstants.ERROR_KEY,
//                    HttpStatus.BAD_REQUEST.name().toLowerCase(Locale.ENGLISH));
//            errorResponse.put(ErrorConstants.ERROR_KEY_KEY, HttpStatus.BAD_REQUEST.name().toUpperCase(Locale.ENGLISH));
//        }
//
//        if (ex instanceof ConstraintViolationException e) {
//            errorResponse.put(ErrorConstants.ERROR_MESSAGE_KEY, HttpStatus.BAD_REQUEST.getReasonPhrase());
//            errorResponse.put(ErrorConstants.FIELD_ERRORS_KEY, getFieldErrors(e));
//            errorResponse.put(ErrorConstants.ERROR_KEY,
//                    HttpStatus.BAD_REQUEST.name().toLowerCase(Locale.ENGLISH));
//            errorResponse.put(ErrorConstants.ERROR_KEY_KEY, HttpStatus.BAD_REQUEST.name().toUpperCase(Locale.ENGLISH));
//        }
//
//        return errorResponse;
//    }
//
//    private Object getPropertyValueIfExists(BeanWrapper beanWrapper, String property) {
//        try {
//            return beanWrapper.getPropertyValue(property);
//        } catch (NotReadablePropertyException | NotWritablePropertyException e) {
//            // Log the exception if necessary
//            return null;
//        }
//    }
//}
