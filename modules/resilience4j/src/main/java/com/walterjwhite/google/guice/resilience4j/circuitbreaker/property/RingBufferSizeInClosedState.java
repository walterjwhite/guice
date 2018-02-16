package com.walterjwhite.google.guice.resilience4j.circuitbreaker.property;

import com.walterjwhite.google.guice.property.property.DefaultValue;
import com.walterjwhite.google.guice.property.property.GuiceProperty;

public interface RingBufferSizeInClosedState extends GuiceProperty {
  @DefaultValue int Default = 2;
}
