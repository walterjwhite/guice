package com.walterjwhite.google.guice.resilience4j.bulkhead;

import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.bulkhead.BulkheadRegistry;
import io.vavr.CheckedFunction0;
import io.vavr.control.Try;
import java.lang.reflect.Method;
import javax.inject.Inject;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class BulkheadMethodInterceptor implements MethodInterceptor {
  @Inject protected BulkheadRegistry bulkheadRegistry;

  @Override
  public Object invoke(MethodInvocation methodInvocation) {
    Bulkhead bulkhead = getBulkhead(methodInvocation);

    // When I decorate my function
    CheckedFunction0<Object> decoratedSupplier =
        Bulkhead.decorateCheckedSupplier(bulkhead, () -> methodInvocation.proceed());
    return Try.of(decoratedSupplier);
  }

  protected Bulkhead getBulkhead(MethodInvocation methodInvocation) {
    // TODO: configure the bulkhead
    //        final CircuitBreakerEnabled circuitBreakerEnabled =
    //                methodInvocation.getMethod().getAnnotation(CircuitBreakerEnabled.class);
    return bulkheadRegistry.bulkhead(getBulkheadName(methodInvocation.getMethod()));
  }

  protected String getBulkheadName(final Method method) {
    return method.getDeclaringClass().getName() + "." + method.getName();
  }
}
