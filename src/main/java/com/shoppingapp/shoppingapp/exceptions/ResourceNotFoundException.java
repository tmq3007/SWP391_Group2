package com.shoppingapp.shoppingapp.exceptions;

public class ResourceNotFoundException extends RuntimeException {
  public ResourceNotFoundException(ErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
  }

  private ErrorCode errorCode;

  public ErrorCode getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(ErrorCode errorCode) {
    this.errorCode = errorCode;
  }

}
