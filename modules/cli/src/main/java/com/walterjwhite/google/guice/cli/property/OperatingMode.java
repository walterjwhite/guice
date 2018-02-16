package com.walterjwhite.google.guice.cli.property;

import com.walterjwhite.google.guice.cli.service.AbstractCommandLineHandler;
import com.walterjwhite.google.guice.property.property.GuiceProperty;

public interface OperatingMode extends GuiceProperty {
  Class<? extends AbstractCommandLineHandler> getInitiatorClass();
}
