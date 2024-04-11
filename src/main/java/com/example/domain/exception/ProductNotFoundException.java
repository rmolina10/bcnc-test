package com.example.domain.exception;

public class ProductNotFoundException extends ProductException {

  public ProductNotFoundException(ErrorsEnum error, Object... value) {
    super(error, value);
  }
}
