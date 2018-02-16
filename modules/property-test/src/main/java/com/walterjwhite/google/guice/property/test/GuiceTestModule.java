package com.walterjwhite.google.guice.property.test;

import com.walterjwhite.google.guice.logging.AbstractLoggingModule;
import com.walterjwhite.google.guice.property.enumeration.Debug;
import org.reflections.Reflections;

/** Use this inside unit tests to ensure guice components are properly configured. */
public class GuiceTestModule extends AbstractLoggingModule {
  protected final PropertyValuePair[] propertyValuePairs;

  public GuiceTestModule(PropertyValuePair... propertyValuePairs) {
    super();

    this.propertyValuePairs = propertyValuePairs;
    loadSpecifiedProperties();
  }

  public GuiceTestModule(Reflections reflections, PropertyValuePair... propertyValuePairs) {
    super(reflections);

    this.propertyValuePairs = propertyValuePairs;
    loadSpecifiedProperties();
  }

  @Override
  protected void loadDefaults() {
    super.loadDefaults();

    propertyManager.setValue(Debug.class, Boolean.toString(true));
  }

  protected void loadSpecifiedProperties() {
    if (propertyValuePairs != null && propertyValuePairs.length > 0) {
      for (PropertyValuePair propertyValuePair : propertyValuePairs) {
        loadSpecifiedProperty(propertyValuePair);
      }
    }
  }

  protected void loadSpecifiedProperty(PropertyValuePair propertyValuePair) {
    propertyManager.setValue(
        propertyValuePair.getPropertyClass(), propertyValuePair.getValue().toString());
  }
}
