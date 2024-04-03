package com.example.infrastructure.rest.spring.error;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.example.domain.exception.*;
import com.example.infrastructure.rest.spring.dto.ErrorResponseWebDto;
import com.example.tests.LoggingEventUtility;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

@ExtendWith(MockitoExtension.class)
class ApiErrorHandlerTest {

  private static final String DEFAULT_PATH = "some path";
  private static final String ERROR_MESSAGE = "some error message";
  private static final String DEFAULT_LOG_MESSAGE = "Error performing Rest operation.";
  private final ApiErrorHandler apiErrorHandler = new ApiErrorHandler();

  private ListAppender<ILoggingEvent> loggingEventListAppender;

  @BeforeEach
  public void setup() {
    loggingEventListAppender =
        LoggingEventUtility.getListAppenderForClass(ApiErrorHandler.class, Level.DEBUG);
  }

  @Test
  void handleException() {

    // Given
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.setServletPath(DEFAULT_PATH);
    Exception exception = new RuntimeException(ERROR_MESSAGE);

    Level expectedLogLevel = Level.ERROR;

    // When
    ResponseEntity<ErrorResponseWebDto> response =
        apiErrorHandler.handleException(exception, request);

    // Then
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    ErrorResponseWebDto errorResponseWebDto = response.getBody();
    assertNotNull(errorResponseWebDto);

    assertEquals(ErrorsEnum.INTERNAL_SERVER_ERROR.getCode(), errorResponseWebDto.getCode());
    assertEquals(ERROR_MESSAGE, errorResponseWebDto.getMessage());

    // Logging
    assertEquals(1, loggingEventListAppender.list.size());
    assertEquals(expectedLogLevel, loggingEventListAppender.list.get(0).getLevel());
    assertEquals(DEFAULT_LOG_MESSAGE, loggingEventListAppender.list.get(0).getFormattedMessage());
  }

  @Test
  void handleProductException() {
    // Given
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.setServletPath(DEFAULT_PATH);

    ProductException productException = new ProductException(ErrorsEnum.INTERNAL_SERVER_ERROR);

    Level expectedLogLevel = Level.ERROR;

    // When
    ResponseEntity<ErrorResponseWebDto> response =
        apiErrorHandler.handleProductException(productException, request);

    // Then
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    ErrorResponseWebDto errorResponseWebDto = response.getBody();
    assertNotNull(errorResponseWebDto);

    assertEquals(ErrorsEnum.INTERNAL_SERVER_ERROR.getCode(), errorResponseWebDto.getCode());
    assertEquals(ErrorsEnum.INTERNAL_SERVER_ERROR.getMessage(), errorResponseWebDto.getMessage());
    assertEquals(
        ErrorsEnum.INTERNAL_SERVER_ERROR.getDescription(), errorResponseWebDto.getDescription());
    assertNotNull(errorResponseWebDto.getTimestamp());
    assertEquals(DEFAULT_PATH, errorResponseWebDto.getPath());

    // Logging
    assertEquals(1, loggingEventListAppender.list.size());
    assertEquals(expectedLogLevel, loggingEventListAppender.list.get(0).getLevel());
    assertEquals(DEFAULT_LOG_MESSAGE, loggingEventListAppender.list.get(0).getFormattedMessage());
  }

  @Test
  void handleProductException_withThrowableAndMessage() {
    // Given
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.setServletPath(DEFAULT_PATH);

    Throwable throwable = new RuntimeException(ERROR_MESSAGE);
    ProductException productException =
        new ProductException(ErrorsEnum.INTERNAL_SERVER_ERROR, throwable, ERROR_MESSAGE);

    Level expectedLogLevel = Level.ERROR;

    // When
    ResponseEntity<ErrorResponseWebDto> response =
        apiErrorHandler.handleProductException(productException, request);

    // Then
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

    ErrorResponseWebDto errorResponseWebDto = response.getBody();
    assertNotNull(errorResponseWebDto);

    assertEquals(ErrorsEnum.INTERNAL_SERVER_ERROR.getCode(), errorResponseWebDto.getCode());
    assertEquals(
        ErrorsEnum.INTERNAL_SERVER_ERROR.getDescription(), errorResponseWebDto.getDescription());
    assertEquals(ErrorsEnum.INTERNAL_SERVER_ERROR.getMessage(), errorResponseWebDto.getMessage());
    assertNotNull(errorResponseWebDto.getTimestamp());
    assertEquals(DEFAULT_PATH, errorResponseWebDto.getPath());

    // Logging
    assertEquals(1, loggingEventListAppender.list.size());
    assertEquals(expectedLogLevel, loggingEventListAppender.list.get(0).getLevel());
    assertEquals(DEFAULT_LOG_MESSAGE, loggingEventListAppender.list.get(0).getFormattedMessage());
  }

  @Test
  void handleConstraintViolationException() {
    // Given
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.setServletPath(DEFAULT_PATH);

    ConstraintViolationException constraintViolationException =
        mock(ConstraintViolationException.class);
    when(constraintViolationException.getMessage())
        .thenReturn(ErrorsEnum.BAD_REQUEST.getDescription());

    Level expectedLogLevel = Level.ERROR;

    // When
    ResponseEntity<ErrorResponseWebDto> response =
        apiErrorHandler.handleConstraintViolationException(constraintViolationException, request);

    // Then
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    ErrorResponseWebDto errorResponseWebDto = response.getBody();
    assertNotNull(errorResponseWebDto);

    assertEquals(ErrorsEnum.BAD_REQUEST.getCode(), errorResponseWebDto.getCode());
    assertEquals(ErrorsEnum.BAD_REQUEST.getMessage(), errorResponseWebDto.getMessage());
    assertEquals(ErrorsEnum.BAD_REQUEST.getDescription(), errorResponseWebDto.getDescription());
    assertNotNull(errorResponseWebDto.getTimestamp());
    assertEquals(DEFAULT_PATH, errorResponseWebDto.getPath());

    // Logging
    assertEquals(1, loggingEventListAppender.list.size());
    assertEquals(expectedLogLevel, loggingEventListAppender.list.get(0).getLevel());
    assertEquals(DEFAULT_LOG_MESSAGE, loggingEventListAppender.list.get(0).getFormattedMessage());
  }

  @Test
  void handleProductValidationException() {
    // Given
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.setServletPath(DEFAULT_PATH);

    ProductValidationException productValidationException =
        new ProductValidationException(ErrorsEnum.BINDING_VALIDATION_ERROR);

    Level expectedLogLevel = Level.ERROR;

    // When
    ResponseEntity<ErrorResponseWebDto> response =
        apiErrorHandler.handleProductValidationException(productValidationException, request);

    // Then
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    ErrorResponseWebDto errorResponseWebDto = response.getBody();
    assertNotNull(errorResponseWebDto);

    assertEquals(ErrorsEnum.BINDING_VALIDATION_ERROR.getCode(), errorResponseWebDto.getCode());
    assertEquals(
        ErrorsEnum.BINDING_VALIDATION_ERROR.getDescription(), errorResponseWebDto.getDescription());
    assertEquals(
        ErrorsEnum.BINDING_VALIDATION_ERROR.getMessage(), errorResponseWebDto.getMessage());
    assertNotNull(errorResponseWebDto.getTimestamp());
    assertEquals(DEFAULT_PATH, errorResponseWebDto.getPath());

    // Logging
    assertEquals(1, loggingEventListAppender.list.size());
    assertEquals(expectedLogLevel, loggingEventListAppender.list.get(0).getLevel());
    assertEquals(DEFAULT_LOG_MESSAGE, loggingEventListAppender.list.get(0).getFormattedMessage());
  }

  @Test
  void handleProductRepositoryException_withThrowableAndMessage() {
    // Given
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.setServletPath(DEFAULT_PATH);

    ProductRepositoryException productRepositoryException =
        new ProductRepositoryException(ErrorsEnum.REPOSITORY_EXCEPTION);

    Level expectedLogLevel = Level.ERROR;

    // When
    ResponseEntity<ErrorResponseWebDto> response =
        apiErrorHandler.handleProductRepositoryException(productRepositoryException, request);

    // Then
    assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
    ErrorResponseWebDto errorResponseWebDto = response.getBody();
    assertNotNull(errorResponseWebDto);

    assertEquals(ErrorsEnum.REPOSITORY_EXCEPTION.getCode(), errorResponseWebDto.getCode());
    assertEquals(ErrorsEnum.REPOSITORY_EXCEPTION.getMessage(), errorResponseWebDto.getMessage());
    assertEquals(
        ErrorsEnum.REPOSITORY_EXCEPTION.getDescription(), errorResponseWebDto.getDescription());
    assertNotNull(errorResponseWebDto.getTimestamp());
    assertEquals(DEFAULT_PATH, errorResponseWebDto.getPath());

    // Logging
    assertEquals(1, loggingEventListAppender.list.size());
    assertEquals(expectedLogLevel, loggingEventListAppender.list.get(0).getLevel());
    assertEquals(DEFAULT_LOG_MESSAGE, loggingEventListAppender.list.get(0).getFormattedMessage());
  }

  @Test
  void handleProductRepositoryException() {
    // Given
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.setServletPath(DEFAULT_PATH);

    Throwable throwable = new RuntimeException(ERROR_MESSAGE);
    ProductRepositoryException productRepositoryException =
        new ProductRepositoryException(ErrorsEnum.REPOSITORY_EXCEPTION, throwable, ERROR_MESSAGE);

    Level expectedLogLevel = Level.ERROR;

    // When
    ResponseEntity<ErrorResponseWebDto> response =
        apiErrorHandler.handleProductRepositoryException(productRepositoryException, request);

    // Then
    assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
    ErrorResponseWebDto errorResponseWebDto = response.getBody();
    assertNotNull(errorResponseWebDto);

    assertEquals(ErrorsEnum.REPOSITORY_EXCEPTION.getCode(), errorResponseWebDto.getCode());
    assertEquals(ErrorsEnum.REPOSITORY_EXCEPTION.getMessage(), errorResponseWebDto.getMessage());
    assertEquals(
        ErrorsEnum.REPOSITORY_EXCEPTION.getDescription(), errorResponseWebDto.getDescription());
    assertNotNull(errorResponseWebDto.getTimestamp());
    assertEquals(DEFAULT_PATH, errorResponseWebDto.getPath());

    // Logging
    assertEquals(1, loggingEventListAppender.list.size());
    assertEquals(expectedLogLevel, loggingEventListAppender.list.get(0).getLevel());
    assertEquals(DEFAULT_LOG_MESSAGE, loggingEventListAppender.list.get(0).getFormattedMessage());
  }
}
