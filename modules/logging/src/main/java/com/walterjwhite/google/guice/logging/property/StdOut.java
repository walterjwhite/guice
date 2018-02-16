package com.walterjwhite.google.guice.logging.property;

import com.walterjwhite.google.guice.property.property.DefaultValue;
import com.walterjwhite.google.guice.property.property.GuiceProperty;

public interface StdOut extends GuiceProperty {
  // corresponds with slf4j.org SimpleLogger org.slf4j.simplelogger.logFile
  @DefaultValue String Default = "System.out";
}
