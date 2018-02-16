package com.walterjwhite.google.guice.cli.property;

import com.walterjwhite.google.guice.property.property.DefaultValue;
import com.walterjwhite.google.guice.property.property.GuiceProperty;

public interface CommandLineHandlerShutdownTimeout extends GuiceProperty {
  @DefaultValue int Default = 30; // wait up to 30 seconds before dying.
}
