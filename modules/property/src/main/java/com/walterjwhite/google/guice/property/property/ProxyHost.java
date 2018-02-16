package com.walterjwhite.google.guice.property.property;

/**
 * Default value was removed, instead system relies on environmental variable to be set OR,
 * command-line option, or properties file.
 */
public interface ProxyHost extends GuiceProperty {
  //  @DefaultValue String Default = "localhost";
}
