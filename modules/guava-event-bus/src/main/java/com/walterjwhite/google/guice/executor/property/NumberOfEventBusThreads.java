package com.walterjwhite.google.guice.executor.property;

import com.walterjwhite.google.guice.property.annotation.DefaultValue;
import com.walterjwhite.google.guice.property.property.GuiceProperty;

public interface NumberOfEventBusThreads extends GuiceProperty {
  @DefaultValue int Default = 2;
}
