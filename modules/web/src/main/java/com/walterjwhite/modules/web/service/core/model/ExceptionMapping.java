package com.walterjwhite.modules.web.service.core.model;

public class ExceptionMapping {
  protected Class<? extends Exception> exceptionClass;
  protected int httpCode;

  public ExceptionMapping(Class<? extends Exception> exceptionClass, int httpCode) {
    this.exceptionClass = exceptionClass;
    this.httpCode = httpCode;
  }

  public ExceptionMapping() {}

  public Class<? extends Exception> getExceptionClass() {
    return exceptionClass;
  }

  public void setExceptionClass(Class<? extends Exception> exceptionClass) {
    this.exceptionClass = exceptionClass;
  }

  public int getHttpCode() {
    return httpCode;
  }

  public void setHttpCode(int httpCode) {
    this.httpCode = httpCode;
  }
}
