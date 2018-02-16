package com.walterjwhite.google.guice.resilience4j.circuitbreaker;

import com.walterjwhite.google.guice.resilience4j.circuitbreaker.annotation.CircuitBreakerEnabled;

public class ImplementationExampleTest {

  @CircuitBreakerEnabled
  public String alwaysFail() {
    if (true) throw (new RuntimeException("testing"));

    return ("success");
  }
}
