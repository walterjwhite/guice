package com.walterjwhite.google.guice.resilience4j.ratelimiter.provider;

import com.walterjwhite.google.guice.property.annotation.Property;
import com.walterjwhite.google.guice.resilience4j.ratelimiter.property.LimitForPeriod;
import com.walterjwhite.google.guice.resilience4j.ratelimiter.property.LimitRefreshPeriod;
import com.walterjwhite.google.guice.resilience4j.ratelimiter.property.TimeoutDuration;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import java.time.Duration;
import javax.inject.Inject;
import javax.inject.Provider;

public class RateLimiterConfigProvider implements Provider<RateLimiterConfig> {
  protected final RateLimiterConfig rateLimiterConfig;

  @Inject
  public RateLimiterConfigProvider(
      @Property(LimitForPeriod.class) int limitForPeriod,
      @Property(LimitRefreshPeriod.class) int limitRefreshPeriod,
      @Property(TimeoutDuration.class) int timeoutDuration) {
    rateLimiterConfig =
        RateLimiterConfig.custom()
            .limitForPeriod(limitForPeriod)
            .limitRefreshPeriod(Duration.ofMillis(limitRefreshPeriod))
            .timeoutDuration(Duration.ofMillis(timeoutDuration))
            .build();
  }

  @Override
  public RateLimiterConfig get() {
    return rateLimiterConfig;
  }
}
