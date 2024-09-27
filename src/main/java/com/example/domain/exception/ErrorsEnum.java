package com.example.domain.exception;

import lombok.Getter;

@Getter
public enum ErrorsEnum {
  TYPE_MISMATCH("GEN001", "Request type mismatch", ""),
  BINDING_VALIDATION_ERROR(
      "GEN002", "Validation error", "Error validating the data of the request object."),
  UNSUPPORTED_MEDIA_TYPE("GEN003", "Unsupported media type", ""),
  MESSAGE_NOT_READABLE("GEN004", "Message not readable", ""),
  METHOD_NOT_ALLOWED("GEN005", "Method not allowed", ""),
  INTERNAL_SERVER_ERROR("GEN006", "Internal server error", ""),
  BAD_REQUEST("GEN007", "Bad request", ""),

  PRICE_NOT_FOUND("PRI001", "Price not Found", "There is no price for this request."),

  VALIDATION_EXCEPTION_INVALID_DATE_APPLICATION(
      "VALIDATION001",
      "Validation exception",
      "Specified date application is invalid, it must be the format: 'yyyy-MM-dd'T'HH:mm:ss.SSSxxx'"),

  REPOSITORY_EXCEPTION(
      "REPOSITORY001",
      "Repository exception",
      "Error performing requested operation in repository.");

  private final String code;

  private final String description;

  private final String message;

  ErrorsEnum(String code, String description, String message) {
    this.code = code;
    this.description = description;
    this.message = message;
  }

  @Override
  public String toString() {
    return String.valueOf(description);
  }
}
