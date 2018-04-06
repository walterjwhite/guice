package com.walterjwhite.google.guice.resilience4j.bulkhead.property;

import com.walterjwhite.google.guice.property.annotation.DefaultValue;
import com.walterjwhite.google.guice.property.property.GuiceProperty;

public interface MaxWaitTime extends GuiceProperty {
  @DefaultValue int Default = 100;
}
