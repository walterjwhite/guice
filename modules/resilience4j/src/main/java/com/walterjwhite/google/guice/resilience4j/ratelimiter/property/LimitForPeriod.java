package com.walterjwhite.google.guice.resilience4j.ratelimiter.property;

import com.walterjwhite.google.guice.property.property.DefaultValue;
import com.walterjwhite.google.guice.property.property.GuiceProperty;

public interface LimitForPeriod extends GuiceProperty {
  @DefaultValue int Default = 10; // 10 ms
}
