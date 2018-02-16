package com.walterjwhite.google.guice.executor.property;

import com.walterjwhite.google.guice.property.property.DefaultValue;
import com.walterjwhite.google.guice.property.property.GuiceProperty;

public interface AsyncEventBusName extends GuiceProperty {
  @DefaultValue String Default = "AsyncEventBus";
}
