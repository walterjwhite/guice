package com.walterjwhite.google.guice.property.enumeration;

import com.walterjwhite.google.guice.property.annotation.DefaultValue;
import com.walterjwhite.google.guice.property.property.GuiceProperty;

/** Should the application run in debug mode? */
public interface Debug extends GuiceProperty {
  @DefaultValue boolean DEFAULT = false;
}
