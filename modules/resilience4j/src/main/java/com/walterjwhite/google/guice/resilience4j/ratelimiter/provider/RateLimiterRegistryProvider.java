package com.walterjwhite.google.guice.resilience4j.ratelimiter.provider;

import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import javax.inject.Inject;
import javax.inject.Provider;

public class RateLimiterRegistryProvider implements Provider<RateLimiterRegistry> {
  protected final RateLimiterRegistry rateLimiterRegistry;

  @Inject
  public RateLimiterRegistryProvider(RateLimiterConfig rateLimiterConfig) {
    super();
    rateLimiterRegistry = RateLimiterRegistry.of(rateLimiterConfig);
  }

  @Override
  public RateLimiterRegistry get() {
    return rateLimiterRegistry;
  }
}
