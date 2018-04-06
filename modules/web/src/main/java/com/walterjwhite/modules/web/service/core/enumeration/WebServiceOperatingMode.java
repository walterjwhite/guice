package com.walterjwhite.modules.web.service.core.enumeration;

import com.walterjwhite.google.guice.cli.property.OperatingMode;
import com.walterjwhite.google.guice.cli.service.AbstractCommandLineHandler;
import com.walterjwhite.google.guice.property.annotation.DefaultValue;
import com.walterjwhite.modules.web.service.core.ServerCommandLineHandler;

public enum WebServiceOperatingMode implements OperatingMode {
  @DefaultValue
  Default(ServerCommandLineHandler.class);

  private final Class<? extends AbstractCommandLineHandler> initiatorClass;

  WebServiceOperatingMode(Class<? extends AbstractCommandLineHandler> initiatorClass) {
    this.initiatorClass = initiatorClass;
  }

  @Override
  public Class<? extends AbstractCommandLineHandler> getInitiatorClass() {
    return initiatorClass;
  }
}
