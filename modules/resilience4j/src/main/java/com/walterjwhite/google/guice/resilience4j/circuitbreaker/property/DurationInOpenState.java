package com.walterjwhite.google.guice.resilience4j.circuitbreaker.property;

import com.walterjwhite.google.guice.property.annotation.DefaultValue;
import com.walterjwhite.google.guice.property.property.GuiceProperty;

public interface DurationInOpenState extends GuiceProperty {
  @DefaultValue int Default = 1000;
}
