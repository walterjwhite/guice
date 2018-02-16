package com.walterjwhite.google.guice.property.enumeration;

import com.walterjwhite.google.guice.property.property.GuiceProperty;
import com.walterjwhite.google.guice.property.property.JavaEnvironmentProperty;
import com.walterjwhite.google.guice.property.property.NoProxy;
import com.walterjwhite.google.guice.property.property.ProxyHost;
import com.walterjwhite.google.guice.property.property.ProxyPort;

public enum JavaProxy implements JavaEnvironmentProperty {
  HttpProxyHost("http.proxyHost", ProxyHost.class),
  HttpProxyPort("http.proxyPort", ProxyPort.class),
  HttpsProxyHost("https.proxyHost", ProxyHost.class),
  HttpsProxyPort("https.proxyPort", ProxyPort.class),
  NoProxy("noProxy", NoProxy.class);

  protected final String key;
  protected final Class<? extends GuiceProperty> propertyKey;

  JavaProxy(String key, Class<? extends GuiceProperty> propertyKey) {
    this.key = key;
    this.propertyKey = propertyKey;
  }

  @Override
  public String getKey() {
    return key;
  }

  @Override
  public Class<? extends GuiceProperty> getPropertyKey() {
    return propertyKey;
  }
}
