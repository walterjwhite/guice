package com.walterjwhite.google.guice.resilience4j.circuitbreaker;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.vavr.CheckedFunction0;
import java.lang.reflect.Method;
import javax.inject.Inject;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class CircuitBreakerMethodInterceptor implements MethodInterceptor {
  @Inject protected CircuitBreakerRegistry circuitBreakerRegistry;

  @Override
  public Object invoke(MethodInvocation methodInvocation) throws Throwable {
    CircuitBreaker circuitBreaker = getCircuitBreaker(methodInvocation);

    CheckedFunction0<Object> decoratedSupplier =
        CircuitBreaker.decorateCheckedSupplier(circuitBreaker, () -> methodInvocation.proceed());
    return decoratedSupplier.apply();
  }

  protected CircuitBreaker getCircuitBreaker(MethodInvocation methodInvocation) {
    // TODO: configure the circuit breaker
    //        final CircuitBreakerEnabled circuitBreakerEnabled =
    //                methodInvocation.getMethod().getAnnotation(CircuitBreakerEnabled.class);
    return circuitBreakerRegistry.circuitBreaker(
        getCircuitBreakerName(methodInvocation.getMethod()));
  }

  protected String getCircuitBreakerName(final Method method) {
    return method.getDeclaringClass().getName() + "." + method.getName();
  }
}
