package com.walterjwhite.google.guice.resilience4j.ratelimiter.property;

import com.walterjwhite.google.guice.property.annotation.DefaultValue;
import com.walterjwhite.google.guice.property.property.GuiceProperty;

public interface LimitRefreshPeriod extends GuiceProperty {
  @DefaultValue int Default = 1; // 1 ms
}
