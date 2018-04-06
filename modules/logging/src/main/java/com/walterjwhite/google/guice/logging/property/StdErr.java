package com.walterjwhite.google.guice.logging.property;

import com.walterjwhite.google.guice.property.annotation.DefaultValue;
import com.walterjwhite.google.guice.property.property.GuiceProperty;

public interface StdErr extends GuiceProperty {
  // corresponds with slf4j.org SimpleLogger org.slf4j.simplelogger.logFile
  @DefaultValue String Default = "System.err";
}
