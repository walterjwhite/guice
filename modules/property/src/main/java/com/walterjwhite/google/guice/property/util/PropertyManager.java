package com.walterjwhite.google.guice.property.util;

import com.walterjwhite.google.guice.property.property.*;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Properties;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO: support key-value stores such as redis, database-backed properties, LDAP
public class PropertyManager {
  protected final Logger LOGGER = LoggerFactory.getLogger(PropertyManager.class);

  public static final String APPLICATION_PROPERTIES_FILE_PATH = "/application.properties";

  protected final Properties properties = new Properties();

  protected final Reflections reflections;

  public PropertyManager(Reflections reflections) {
    this.reflections = reflections;
  }

  public void loadDefaults() {
    getPropertyKeys().forEach(p -> loadDefault(p));
  }

  protected void loadDefault(final Class<? extends GuiceProperty> guiceProperty) {
    for (final Field field : guiceProperty.getDeclaredFields()) {
      if (isDefault(field)) {
        setDefault(properties, guiceProperty, field);
      }
    }
  }

  protected boolean isDefault(final Field field) {
    return field.isAnnotationPresent(DefaultValue.class);
  }

  protected void setDefault(
      final Properties properties,
      final Class<? extends GuiceProperty> guiceProperty,
      final Field field) {
    try {
      properties.setProperty(getName(guiceProperty), getValue(field).toString());

      if (field.isEnumConstant()) loadDefaultOnInterface(properties, guiceProperty, field);

    } catch (IllegalAccessException e) {
      LOGGER.warn("Unable to set default value on field:" + field, e);
    }
  }

  protected void loadDefaultOnInterface(
      final Properties properties,
      final Class<? extends GuiceProperty> guiceProperty,
      final Field field)
      throws IllegalAccessException {
    for (final Class<? extends GuiceProperty> guicePropertyClass :
        ((Class<? extends GuiceProperty>[]) guiceProperty.getInterfaces())) {
      if (!GuiceProperty.class.equals(guicePropertyClass))
        properties.setProperty(getName(guicePropertyClass), getValue(field).toString());
    }
  }

  public void loadAll() {
    loadFromPropertiesFile();
    loadFromEnvironment();
    loadFromSystemProperty();
    loadMappedEnvironmentalProperties();
  }

  protected Object getValue(final Field field) throws IllegalAccessException {
    final boolean wasAccessible = field.isAccessible();
    try {
      field.setAccessible(true);
      return (field.get(null));
    } finally {
      field.setAccessible(wasAccessible);
    }
  }

  protected void loadFromPropertiesFile() {
    try {
      final Properties tempProperties = new Properties();
      URL url = PropertyManager.class.getResource(APPLICATION_PROPERTIES_FILE_PATH);

      if (url != null) {
        tempProperties.load(url.openStream());
        getPropertyKeys().forEach(p -> loadPropertyFromProperties(tempProperties, p));
      } else LOGGER.trace(APPLICATION_PROPERTIES_FILE_PATH + " does not exist.");
    } catch (Exception e) {
      LOGGER.warn("Error loading properties", e);
    }
  }

  protected void loadPropertyFromProperties(
      final Properties tempProperties, final Class<? extends GuiceProperty> guiceProperty) {
    final Object value = tempProperties.get(getName(guiceProperty));
    if (value != null) properties.put(getName(guiceProperty), value);
  }

  public void loadFromEnvironment() {
    getPropertyKeys().forEach(p -> loadPropertyFromEnvironment(p));
  }

  protected void loadPropertyFromEnvironment(final Class<? extends GuiceProperty> guiceProperty) {
    final String value = System.getenv().get(getEnvironmentVariableName(guiceProperty));
    if (value != null) properties.setProperty(getName(guiceProperty), value);
  }

  public void loadFromSystemProperty() {
    getPropertyKeys().forEach(p -> loadPropertyFromSystemProperty(p));
  }

  protected void loadPropertyFromSystemProperty(
      final Class<? extends GuiceProperty> guiceProperty) {
    final String value = System.getProperty(getName(guiceProperty));
    if (value != null) properties.setProperty(getName(guiceProperty), value);
  }

  public Properties getProperties() {
    return properties;
  }

  public Iterable<Class<? extends GuiceProperty>> getPropertyKeys() {
    return (reflections.getSubTypesOf(GuiceProperty.class));
  }

  public Iterable<Class<? extends EnvironmentProperty>> getEnvironmentProperties() {
    return (reflections.getSubTypesOf(EnvironmentProperty.class));
  }

  public Iterable<Class<? extends JavaEnvironmentProperty>> getJavaEnvironmentProperties() {
    return (reflections.getSubTypesOf(JavaEnvironmentProperty.class));
  }

  public Reflections getReflections() {
    return reflections;
  }

  public String getValue(final Class<? extends GuiceProperty> guiceProperty) {
    return (properties.getProperty(getName(guiceProperty)));
  }

  public void setValue(Class<? extends GuiceProperty> guiceProperty, final String value) {
    properties.setProperty(guiceProperty.getName(), value);
  }

  public String getName(final Class<? extends GuiceProperty> guiceProperty) {
    return (guiceProperty.getName());
  }

  public String getEnvironmentVariableName(final Class<? extends GuiceProperty> guiceProperty) {
    return (getName(guiceProperty).replaceAll("\\.", "_"));
  }

  public void loadMappedEnvironmentalProperties() {
    getEnvironmentProperties().forEach(p -> loadEnvironmentProperty(p));
  }

  // ie. sets the ProxyHost or ProxyPort from http_proxy or https_proxy ...
  public void loadEnvironmentProperty(
      final Class<? extends EnvironmentProperty> environmentProperty) {
    try {
      for (final EnvironmentProperty environmentProperty1 :
          environmentProperty.getEnumConstants()) {
        for (final Class<? extends GuiceProperty> supportedProperty :
            environmentProperty1.getSupportedProperties()) {
          final String value = environmentProperty1.getValue(properties, supportedProperty);
          properties.setProperty(getName(supportedProperty), value);
        }
      }
    } catch (Exception e) {
      LOGGER.warn("Error loading properties for:" + environmentProperty, e);
    }
  }

  public void setJavaProperties() {
    getJavaEnvironmentProperties().forEach(p -> setJavaProperty(p));
  }

  protected void setJavaProperty(
      final Class<? extends JavaEnvironmentProperty> environmentProperty) {
    for (final JavaEnvironmentProperty environmentProperty1 :
        environmentProperty.getEnumConstants()) {
      System.setProperty(
          environmentProperty1.getKey(), getValue(environmentProperty1.getPropertyKey()));
    }
  }
}
