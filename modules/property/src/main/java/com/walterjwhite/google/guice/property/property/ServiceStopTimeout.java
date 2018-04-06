package com.walterjwhite.google.guice.property.property;

import com.walterjwhite.google.guice.property.annotation.DefaultValue;

public interface ServiceStopTimeout extends GuiceProperty {
  @DefaultValue int Default = 30;
}
