package com.walterjwhite.google.guice.property;

import com.google.inject.AbstractModule;
import com.google.inject.Stage;
import com.walterjwhite.google.guice.property.property.ApplicationEnvironment;
import com.walterjwhite.google.guice.property.property.GuiceProperty;
import com.walterjwhite.google.guice.property.property.PropertyImpl;
import com.walterjwhite.google.guice.property.util.PropertyManager;
import com.walterjwhite.google.guice.property.util.ServiceManager;
import java.util.Properties;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractPropertyModule extends AbstractModule {
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
    final String value = propertyManager.getValue(guicePropertyClass);

    if (value != null) bindConstant().annotatedWith(new PropertyImpl(guicePropertyClass)).to(value);
    else {
      throw (new IllegalStateException(
          guicePropertyClass
              + " was not set and not default value was provided either, this is likely a configuration problem."));
    }
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
}
