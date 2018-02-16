package com.walterjwhite.google.guice.resilience4j.circuitbreaker;

import com.google.inject.AbstractModule;
import com.walterjwhite.google.guice.property.test.GuiceTestModule;

public class TestModule extends AbstractModule {
  @Override
  protected void configure() {
    bind(ImplementationExampleTest.class);

    install(new GuiceResilience4jCircuitBreakerModule());
    install(new GuiceTestModule());
  }
}
