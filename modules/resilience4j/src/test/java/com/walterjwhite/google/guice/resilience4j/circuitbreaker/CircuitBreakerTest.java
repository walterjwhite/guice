package com.walterjwhite.google.guice.resilience4j.circuitbreaker;

import com.google.inject.Injector;
import com.walterjwhite.google.guice.GuiceHelper;
import com.walterjwhite.google.guice.property.test.GuiceTestModule;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CircuitBreakerTest {
  private static final Logger LOGGER = LoggerFactory.getLogger(CircuitBreakerTest.class);

  protected Injector injector;
  protected ImplementationExampleTest implementationExampleTest;

  @Before
  public void onBefore() throws Exception {
    GuiceHelper.addModules(new TestModule(), new GuiceTestModule());
    GuiceHelper.setup();

    injector = GuiceHelper.getGuiceInjector();
    implementationExampleTest = injector.getInstance(ImplementationExampleTest.class);
  }

  @After
  public void onAfter() {
    GuiceHelper.stop();
  }

  @Test
  public void doTest() {
    doTestExecution();
    doTestExecution();
    doTestExecution();
    doTestExecution();

    // Try<String> result = Try.of(CircuitBreaker.decorateCheckedSupplier(circuitBreaker, () ->
    // "Hello"))
  }

  protected void doTestExecution() {
    try {
      final String result = implementationExampleTest.alwaysFail();

      LOGGER.debug("result:" + result);

    } catch (Exception e) {
      LOGGER.warn("expected exception", e);
    }
  }
  //
  //  CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
  //          .ringBufferSizeInClosedState(2)
  //          .waitDurationInOpenState(Duration.ofMillis(1000))
  //          .build();
  //  CircuitBreaker circuitBreaker = CircuitBreaker.of("testName", circuitBreakerConfig);
  //
  //// Simulate a failure attempt
  //    circuitBreaker.onError(0, new RuntimeException());
  //  // CircuitBreaker is still CLOSED, because 1 failure is allowed
  //  assertThat(circuitBreaker.getState()).isEqualTo(CircuitBreaker.State.CLOSED);
  //// Simulate a failure attempt
  //    circuitBreaker.onError(0, new RuntimeException());
  //  // CircuitBreaker is OPEN, because the failure rate is above 50%
  //  assertThat(circuitBreaker.getState()).isEqualTo(CircuitBreaker.State.OPEN);
  //
  //  // When I decorate my function and invoke the decorated function
  //  Try<String> result = Try.of(CircuitBreaker.decorateCheckedSupplier(circuitBreaker, () ->
  // "Hello"))
  //          .map(value -> value + " world");
  //
  //  // Then the call fails, because CircuitBreaker is OPEN
  //  assertThat(result.isFailure()).isTrue();
  //  // Exception is CircuitBreakerOpenException
  //  assertThat(result.failed().get()).isInstanceOf(CircuitBreakerOpenException.class);
}
