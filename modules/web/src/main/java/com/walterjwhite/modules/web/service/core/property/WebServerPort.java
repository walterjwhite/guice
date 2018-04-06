package com.walterjwhite.modules.web.service.core.property;

import com.walterjwhite.google.guice.property.annotation.DefaultValue;
import com.walterjwhite.google.guice.property.property.GuiceProperty;

public interface WebServerPort extends GuiceProperty {
  // TODO: support random ports, if we do that, then we also need to ensure proper routing is in
  // place
  @DefaultValue int Default = 8080;
}
