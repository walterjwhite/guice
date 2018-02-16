package com.walterjwhite.google.guice.property.property;

public interface JavaEnvironmentProperty {
  String getKey();

  Class<? extends GuiceProperty> getPropertyKey();
}
