package com.walterjwhite.google.guice.property.property;

import com.walterjwhite.google.guice.property.annotation.DefaultValue;

/** Hosts to bypass the proxy. This is not currently used. */
public interface NoProxy extends GuiceProperty {
  @DefaultValue String Default = "";
}
