package com.walterjwhite.google.guice.property.property;

import com.google.inject.Stage;

public interface ApplicationEnvironment extends GuiceProperty {
  @DefaultValue Stage Default = Stage.DEVELOPMENT;
}
