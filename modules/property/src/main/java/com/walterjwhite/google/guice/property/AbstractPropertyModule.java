package com.walterjwhite.google.guice.property;

import com.google.inject.AbstractModule;
import com.google.inject.Stage;
import com.walterjwhite.google.guice.property.annotation.Sensitive;
import com.walterjwhite.google.guice.property.property.ApplicationEnvironment;
import com.walterjwhite.google.guice.property.property.GuiceProperty;
import com.walterjwhite.google.guice.property.property.PropertyImpl;
import com.walterjwhite.google.guice.property.util.PropertyManager;
import com.walterjwhite.google.guice.property.util.ServiceManager;
import com.walterjwhite.logging.annotation.NonLoggable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import org.apache.commons.io.IOUtils;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractPropertyModule extends AbstractModule {
  // TODO: make this configurable
  public static final String DECRYPT_EXECUTABLE = "/usr/bin/decrypt-password";

  private static final Logger LOGGER = LoggerFactory.getLogger(AbstractPropertyModule.class);

  protected final PropertyManager propertyManager;

  protected final ServiceManager serviceManager;

  protected AbstractPropertyModule() {
    this(Reflections.collect());
  }

  protected AbstractPropertyModule(Reflections reflections) {
    super();

    LOGGER.info("Implementation Version:" + getImplementationVersion());

    propertyManager = new PropertyManager(reflections);
    serviceManager = new ServiceManager(propertyManager);

    loadDefaults();
    loadAll();
  }

  protected void loadDefaults() {
    propertyManager.loadDefaults();
  }

  protected void loadAll() {
    propertyManager.loadAll();
  }

  //  public Properties getPropertyKeys() {
  //    return propertyManager.getPropertyKeys();
  //  }

  @Override
  protected void configure() {
    bind(Properties.class).toInstance(propertyManager.getProperties());
    bindToProperties();

    propertyManager.setJavaProperties();
  }

  protected void bindToProperties() {
    propertyManager.getPropertyKeys().forEach(p -> bindToProperty(p));
  }

  protected void bindToProperty(final Class<? extends GuiceProperty> guicePropertyClass) {
    final String value = getValue(guicePropertyClass);

    if (value != null) bindConstant().annotatedWith(new PropertyImpl(guicePropertyClass)).to(value);
    else {
      throw (new IllegalStateException(
          guicePropertyClass
              + " was not set and not default value was provided either, this is likely a configuration problem."));
    }
  }

  @NonLoggable
  protected String getValue(final Class<? extends GuiceProperty> guicePropertyClass) {
    final String value = propertyManager.getValue(guicePropertyClass);

    if (guicePropertyClass.isAnnotationPresent(Sensitive.class)) {
      try {
        return getDecryptedValue(guicePropertyClass, value);
      } catch (IOException e) {
        throw new RuntimeException("Error decrypting value:", e);
      }
    }

    return value;
  }

  public void startServices() {
    serviceManager.startServices();
  }

  public void stopServices() {
    try {
      serviceManager.stopServices();
    } catch (Exception e) {
      LOGGER.warn("Error stopping services", e);
    }
  }

  public static String getImplementationVersion() {
    return AbstractPropertyModule.class.getPackage().getImplementationVersion();
  }

  public Stage getApplicationEnvironment() {
    return Stage.valueOf(propertyManager.getValue(ApplicationEnvironment.class));
  }

  @NonLoggable
  protected String getDecryptedValue(
      final Class<? extends GuiceProperty> guicePropertyClass, final String sensitiveLookupValue)
      throws IOException {
    try (final InputStream inputStream =
        Runtime.getRuntime()
            .exec(
                new String[] {
                  DECRYPT_EXECUTABLE,
                  getFullyQualifiedPropertyName(guicePropertyClass, sensitiveLookupValue)
                })
            .getInputStream()) {
      return IOUtils.toString(inputStream, StandardCharsets.UTF_8);
    }
  }

  protected String getFullyQualifiedPropertyName(
      final Class<? extends GuiceProperty> guicePropertyClass, final String sensitiveLookupValue) {
    if (sensitiveLookupValue == null) return guicePropertyClass.getName();

    return guicePropertyClass.getName() + "." + sensitiveLookupValue;
  }
}
