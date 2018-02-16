package com.walterjwhite.google.guice.executor.property;

import com.walterjwhite.google.guice.property.property.DefaultValue;
import com.walterjwhite.google.guice.property.property.GuiceProperty;

public interface EventBusName extends GuiceProperty {
  @DefaultValue String Default = "EventBus";
}
