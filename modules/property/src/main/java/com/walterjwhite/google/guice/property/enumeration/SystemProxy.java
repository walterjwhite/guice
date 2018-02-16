package com.walterjwhite.google.guice.property.enumeration;

import com.walterjwhite.google.guice.property.property.*;
import java.util.Properties;

public enum SystemProxy implements EnvironmentProperty {
  HttpProxy("http_proxy", ProxyHost.class, ProxyPort.class) {
    protected String getValue(
        String environmentValue, Class<? extends GuiceProperty> propertyClass) {
      if (ProxyHost.class.equals(propertyClass))
        return (environmentValue.substring(
            environmentValue.indexOf("://") + 3, environmentValue.lastIndexOf(":")));

      return (environmentValue.substring(environmentValue.lastIndexOf(":") + 1));
    }
  },
  HttpsProxy("https_proxy", ProxyHost.class, ProxyPort.class) {
    protected String getValue(
        String environmentValue, Class<? extends GuiceProperty> propertyClass) {
      if (ProxyHost.class.equals(propertyClass))
        return (environmentValue.substring(
            environmentValue.indexOf("://") + 3, environmentValue.lastIndexOf(":")));

      return (environmentValue.substring(environmentValue.lastIndexOf(":") + 1));
    }
  },
  NoProxy("no_proxy", NoProxy.class) {
    protected String getValue(
        String environmentValue, Class<? extends GuiceProperty> propertyClass) {
      return (environmentValue);
    }
  };

  protected final String key;
  protected final Class<? extends GuiceProperty>[] supportedProperties;

  SystemProxy(String key, Class<? extends GuiceProperty>... supportedProperties) {
    this.key = key;
    this.supportedProperties = supportedProperties;
  }

  protected abstract String getValue(
      final String environmentValue, Class<? extends GuiceProperty> propertyClass);

  @Override
  public String getKey() {
    return key;
  }

  @Override
  public Class<? extends GuiceProperty>[] getSupportedProperties() {
    return supportedProperties;
  }

  @Override
  public String getValue(Properties properties, Class<? extends GuiceProperty> propertyClass) {
    return getValue(System.getenv().get(getKey()), propertyClass);
  }
}
