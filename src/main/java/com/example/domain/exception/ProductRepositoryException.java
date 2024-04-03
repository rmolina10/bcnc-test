package com.example.domain.exception;

public class ProductRepositoryException extends ProductException {

  public ProductRepositoryException(ErrorsEnum error) {
    super(error);
  }

  public ProductRepositoryException(ErrorsEnum error, Throwable t, Object... value) {
    super(error, t, value);
  }
}
