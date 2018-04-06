package com.walterjwhite.google.guice.property.annotation;

import com.google.inject.BindingAnnotation;
import com.walterjwhite.google.guice.property.property.GuiceProperty;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@BindingAnnotation
public @interface Property {
  Class<? extends GuiceProperty> value();
}
