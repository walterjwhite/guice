package com.walterjwhite.google.guice.resilience4j.circuitbreaker.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CircuitBreakerEnabled {
  int durationInOpenState() default -1;

  int failureRateThreshold() default -1;

  int ringBufferSizeInClosedState() default -1;

  int ringBufferSizeInHalfOpenState() default -1;
}
