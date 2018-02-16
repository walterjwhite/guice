package com.walterjwhite.google.guice.resilience4j.ratelimiter;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import io.vavr.CheckedFunction0;
import io.vavr.control.Try;
import java.lang.reflect.Method;
import javax.inject.Inject;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class RateLimiterMethodInterceptor implements MethodInterceptor {
  @Inject protected RateLimiterRegistry rateLimiterRegistry;

  @Override
  public Object invoke(MethodInvocation methodInvocation) {
    RateLimiter rateLimiter = getRateLimiter(methodInvocation);

    // When I decorate my function
    CheckedFunction0<Object> decoratedSupplier =
        RateLimiter.decorateCheckedSupplier(rateLimiter, () -> methodInvocation.proceed());
    return Try.of(decoratedSupplier);
  }

  /** Check guice's implementation of the rate limiter ... */
  protected RateLimiter getRateLimiter(MethodInvocation methodInvocation) {
    // TODO: configure the circuit breaker
    //        final CircuitBreakerEnabled circuitBreakerEnabled =
    //                methodInvocation.getMethod().getAnnotation(CircuitBreakerEnabled.class);
    return rateLimiterRegistry.rateLimiter(getRateLimiterName(methodInvocation.getMethod()));
  }

  protected String getRateLimiterName(final Method method) {
    return method.getDeclaringClass().getName() + "." + method.getName();
  }
}
