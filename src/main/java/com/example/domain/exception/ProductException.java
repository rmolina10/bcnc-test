package com.example.domain.exception;

import lombok.Getter;

@Getter
public class ProductException extends RuntimeException {

  private final ErrorsEnum error;

  private final transient Object[] value;

  public ProductException(ErrorsEnum error) {
    super(error.getMessage());
    this.error = error;
    value = new Object[0];
  }

  public ProductException(ErrorsEnum error, Object... value) {
    super(error.getMessage());
    this.error = error;
    this.value = value;
  }

  public ProductException(ErrorsEnum error, Throwable cause) {
    super(error.getMessage(), cause);
    this.error = error;
    this.value = new Object[0];
  }

  public ProductException(ErrorsEnum error, Throwable t, Object... value) {
    super(error.getMessage(), t);
    this.error = error;
    this.value = value;
  }
}
