package com.walterjwhite.google.guice.resilience4j.circuitbreaker.provider;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import javax.inject.Inject;
import javax.inject.Provider;

public class CircuitBreakerRegistryProvider implements Provider<CircuitBreakerRegistry> {
  protected final CircuitBreakerRegistry circuitBreakerRegistry;

  @Inject
  public CircuitBreakerRegistryProvider(CircuitBreakerConfig circuitBreakerConfig) {
    super();
    circuitBreakerRegistry = CircuitBreakerRegistry.of(circuitBreakerConfig);
  }

  @Override
  public CircuitBreakerRegistry get() {
    return circuitBreakerRegistry;
  }
}
