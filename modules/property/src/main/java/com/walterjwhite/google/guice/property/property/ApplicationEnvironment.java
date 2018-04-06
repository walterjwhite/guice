package com.walterjwhite.google.guice.property.property;

import com.google.inject.Stage;
import com.walterjwhite.google.guice.property.annotation.DefaultValue;

public interface ApplicationEnvironment extends GuiceProperty {
  @DefaultValue Stage Default = Stage.DEVELOPMENT;
}
