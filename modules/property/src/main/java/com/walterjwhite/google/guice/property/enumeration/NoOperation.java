package com.walterjwhite.google.guice.property.enumeration;

import com.walterjwhite.google.guice.property.annotation.DefaultValue;
import com.walterjwhite.google.guice.property.property.GuiceProperty;

/** Should a "real" operation be performed or skipped? */
public interface NoOperation extends GuiceProperty {
  @DefaultValue boolean DEFAULT = false;
}
