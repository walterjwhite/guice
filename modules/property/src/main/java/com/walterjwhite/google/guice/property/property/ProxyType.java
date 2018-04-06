package com.walterjwhite.google.guice.property.property;

import com.walterjwhite.google.guice.property.annotation.DefaultValue;

/** Proxy Type (HTTP/SOCKS), not currently used / supported. */
public interface ProxyType extends GuiceProperty {
  @DefaultValue
  com.walterjwhite.google.guice.property.enumeration.ProxyType Default =
      com.walterjwhite.google.guice.property.enumeration.ProxyType.HTTP;
}
