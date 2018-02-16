package com.walterjwhite.google.guice.resilience4j.circuitbreaker;

import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;
import com.walterjwhite.google.guice.resilience4j.circuitbreaker.annotation.CircuitBreakerEnabled;
import com.walterjwhite.google.guice.resilience4j.circuitbreaker.provider.CircuitBreakerConfigProvider;
import com.walterjwhite.google.guice.resilience4j.circuitbreaker.provider.CircuitBreakerRegistryProvider;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Use this inside unit tests to ensure guice components are properly configured. */
public class GuiceResilience4jCircuitBreakerModule extends AbstractModule {
  private static final Logger LOGGER =
      LoggerFactory.getLogger(GuiceResilience4jCircuitBreakerModule.class);

  protected final CircuitBreakerMethodInterceptor circuitBreakerMethodInterceptor =
      new CircuitBreakerMethodInterceptor();
  //    protected final RateLimiterMethodInterceptor rateLimiterMethodInterceptor = new
  // RateLimiterMethodInterceptor();

  @Override
  protected void configure() {
    bind(CircuitBreakerConfig.class).toProvider(CircuitBreakerConfigProvider.class);
    bind(CircuitBreakerRegistry.class).toProvider(CircuitBreakerRegistryProvider.class);

    requestInjection(circuitBreakerMethodInterceptor);
    bindInterceptor(
        Matchers.any(),
        Matchers.annotatedWith(CircuitBreakerEnabled.class),
        circuitBreakerMethodInterceptor);

    // TODO: migrate this to a separate module
    //        requestInjection(rateLimiterMethodInterceptor);
    //        bindInterceptor(Matchers.any(), Matchers.annotatedWith(RateLimiterEnabled.class),
    //                rateLimiterMethodInterceptor);
  }
}
