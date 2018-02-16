package com.walterjwhite.google.guice.property.enumeration;

import com.walterjwhite.google.guice.property.property.DefaultValue;
import com.walterjwhite.google.guice.property.property.GuiceProperty;

/** Debug port to listen on. */
public interface DebugPort extends GuiceProperty {
  @DefaultValue int DEFAULT = 1234;
}
