package com.walterjwhite.google.guice.property.property;

import com.walterjwhite.google.guice.property.annotation.Property;
import java.lang.annotation.Annotation;

public class PropertyImpl implements Property {

  private final Class<? extends GuiceProperty> value;

  public PropertyImpl(Class<? extends GuiceProperty> value) {
    this.value = value;
  }

  @Override
  public Class<? extends GuiceProperty> value() {
    return value;
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Property)) {
      return false;
    }

    Property other = (Property) obj;
    return value.equals(other.value());
  }

  @Override
  public int hashCode() {
    // This is specified in java.lang.Annotation.
    return (127 * "value".hashCode()) ^ value.hashCode();
  }

  @Override
  public String toString() {
    return "@" + Property.class.getName() + "(value=" + value + ")";
  }

  @Override
  public Class<? extends Annotation> annotationType() {
    return Property.class;
  }
}
