package com.walterjwhite.google.guice.resilience4j.circuitbreaker.provider;

import com.walterjwhite.google.guice.property.property.Property;
import com.walterjwhite.google.guice.resilience4j.circuitbreaker.property.DurationInOpenState;
import com.walterjwhite.google.guice.resilience4j.circuitbreaker.property.FailureRateThreshold;
import com.walterjwhite.google.guice.resilience4j.circuitbreaker.property.RingBufferSizeInClosedState;
import com.walterjwhite.google.guice.resilience4j.circuitbreaker.property.RingBufferSizeInHalfOpenState;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import java.time.Duration;
import javax.inject.Inject;
import javax.inject.Provider;

public class CircuitBreakerConfigProvider implements Provider<CircuitBreakerConfig> {
  protected final CircuitBreakerConfig circuitBreakerConfig;

  @Inject
  public CircuitBreakerConfigProvider(
      @Property(DurationInOpenState.class) int durationInOpenState,
      @Property(FailureRateThreshold.class) int failureRateThreshold,
      @Property(RingBufferSizeInClosedState.class) int ringBufferSizeInClosedState,
      @Property(RingBufferSizeInHalfOpenState.class) int ringBufferSizeInHalfOpenState) {

    circuitBreakerConfig =
        CircuitBreakerConfig.custom()
            .failureRateThreshold(failureRateThreshold)
            .waitDurationInOpenState(Duration.ofMillis(durationInOpenState))
            .ringBufferSizeInHalfOpenState(ringBufferSizeInHalfOpenState)
            .ringBufferSizeInClosedState(ringBufferSizeInClosedState)
            .build();
  }

  @Override
  public CircuitBreakerConfig get() {
    return circuitBreakerConfig;
  }
}
