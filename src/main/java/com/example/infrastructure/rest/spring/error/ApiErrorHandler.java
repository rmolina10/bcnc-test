package com.example.infrastructure.rest.spring.error;

import com.example.domain.exception.*;
import com.example.infrastructure.rest.spring.dto.ErrorResponseWebDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import java.time.OffsetDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class ApiErrorHandler extends ResponseEntityExceptionHandler {
  private static final String DEFAULT_LOG_MESSAGE = "Error performing Rest operation.";

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponseWebDto> handleException(
      Exception ex, HttpServletRequest request) {
    return buildErrorResponseException(ex, request);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ErrorResponseWebDto> handleConstraintViolationException(
      ConstraintViolationException ex, HttpServletRequest request) {
    return buildErrorResponseProductException(
        new ProductValidationException(ErrorsEnum.BAD_REQUEST, ex, ex.getMessage()),
        HttpStatus.BAD_REQUEST,
        request);
  }

  @ExceptionHandler(ProductException.class)
  public ResponseEntity<ErrorResponseWebDto> handleProductException(
      ProductException ex, HttpServletRequest request) {
    return buildErrorResponseProductException(ex, HttpStatus.INTERNAL_SERVER_ERROR, request);
  }

  @ExceptionHandler(ProductValidationException.class)
  public ResponseEntity<ErrorResponseWebDto> handleProductValidationException(
      ProductValidationException ex, HttpServletRequest request) {
    return buildErrorResponseProductException(ex, HttpStatus.BAD_REQUEST, request);
  }

  @ExceptionHandler(ProductRepositoryException.class)
  public ResponseEntity<ErrorResponseWebDto> handleProductRepositoryException(
      ProductRepositoryException ex, HttpServletRequest request) {
    return buildErrorResponseProductException(ex, HttpStatus.SERVICE_UNAVAILABLE, request);
  }

  private void logException(Throwable t) {
    log.error(DEFAULT_LOG_MESSAGE, t);
  }

  private ResponseEntity<ErrorResponseWebDto> buildErrorResponseException(
      Exception ex, HttpServletRequest request) {

    logException(ex);

    return new ResponseEntity<>(
        ErrorResponseWebDto.builder()
            .code(ErrorsEnum.INTERNAL_SERVER_ERROR.getCode())
            .description(ErrorsEnum.INTERNAL_SERVER_ERROR.getDescription())
            .message(ex.getLocalizedMessage())
            .timestamp(OffsetDateTime.now())
            .path(request.getServletPath())
            .build(),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

  private ResponseEntity<ErrorResponseWebDto> buildErrorResponseProductException(
      ProductException ex, HttpStatus httpStatus, HttpServletRequest request) {
    logException(ex);
    return new ResponseEntity<>(
        ErrorResponseWebDto.builder()
            .code(ex.getError().getCode())
            .description(ex.getError().getDescription())
            .message(getFormattedMessage(ex.getError(), ex.getValue()))
            .timestamp(OffsetDateTime.now())
            .path(request.getServletPath())
            .build(),
        httpStatus);
  }

  private String getFormattedMessage(ErrorsEnum errorsEnum, Object[] params) {
    return String.format(errorsEnum.getMessage(), params);
  }
}
