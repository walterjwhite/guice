package com.walterjwhite.google.guice.property.property;

/** Hosts to bypass the proxy. This is not currently used. */
public interface NoProxy extends GuiceProperty {
  @DefaultValue String Default = "";
}
