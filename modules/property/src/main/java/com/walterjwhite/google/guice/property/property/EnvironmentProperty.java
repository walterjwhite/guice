package com.walterjwhite.google.guice.property.property;

import java.util.Properties;

public interface EnvironmentProperty {
  String getKey();

  Class<? extends GuiceProperty>[] getSupportedProperties();

  String getValue(Properties properties, Class<? extends GuiceProperty> propertyClass);
}
