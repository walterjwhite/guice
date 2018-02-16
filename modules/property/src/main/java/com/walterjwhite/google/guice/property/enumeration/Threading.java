package com.walterjwhite.google.guice.property.enumeration;

import com.walterjwhite.google.guice.property.property.DefaultValue;
import com.walterjwhite.google.guice.property.property.GuiceProperty;

/** Number of threads system should use * */
public interface Threading extends GuiceProperty {
  @DefaultValue int DEFAULT = Runtime.getRuntime().availableProcessors() * 2;
}
