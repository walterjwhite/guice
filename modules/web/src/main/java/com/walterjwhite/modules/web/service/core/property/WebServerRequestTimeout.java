package com.walterjwhite.modules.web.service.core.property;

import com.walterjwhite.google.guice.property.annotation.DefaultValue;
import com.walterjwhite.google.guice.property.property.GuiceProperty;

public interface WebServerRequestTimeout extends GuiceProperty {
  @DefaultValue int Default = 30000; // ms
}
