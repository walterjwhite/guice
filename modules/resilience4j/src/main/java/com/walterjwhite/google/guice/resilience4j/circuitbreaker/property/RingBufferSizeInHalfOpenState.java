package com.walterjwhite.google.guice.resilience4j.circuitbreaker.property;

import com.walterjwhite.google.guice.property.property.DefaultValue;
import com.walterjwhite.google.guice.property.property.GuiceProperty;

public interface RingBufferSizeInHalfOpenState extends GuiceProperty {
  @DefaultValue int Default = 2;
}
