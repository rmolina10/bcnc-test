package com.example.domain.exception;

public class ProductValidationException extends ProductException {

  public ProductValidationException(ErrorsEnum error, Throwable t, String message) {
    super(error, t, message);
  }

  public ProductValidationException(ErrorsEnum error) {
    super(error);
  }

  public ProductValidationException(ErrorsEnum error, Object... value) {
    super(error, value);
  }
}
