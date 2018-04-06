package com.walterjwhite.google.guice.property.test;

import com.walterjwhite.google.guice.property.property.GuiceProperty;

public class PropertyValuePair {
  protected final Class<? extends GuiceProperty> propertyClass;
  protected final Object value;

  public PropertyValuePair(Class<? extends GuiceProperty> propertyClass, Object value) {
    super();
    this.propertyClass = propertyClass;
    this.value = value;
  }

  public PropertyValuePair(Class<? extends GuiceProperty> propertyClass) {
    this(propertyClass, null);
  }

  public Class<? extends GuiceProperty> getPropertyClass() {
    return propertyClass;
  }

  public Object getValue() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    PropertyValuePair that = (PropertyValuePair) o;

    return propertyClass != null
        ? propertyClass.equals(that.propertyClass)
        : that.propertyClass == null;
  }

  @Override
  public int hashCode() {
    return propertyClass != null ? propertyClass.hashCode() : 0;
  }
}
