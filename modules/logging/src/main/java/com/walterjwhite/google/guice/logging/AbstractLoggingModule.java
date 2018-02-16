package com.walterjwhite.google.guice.logging;

import com.walterjwhite.google.guice.logging.util.LogUtil;
import com.walterjwhite.google.guice.property.AbstractPropertyModule;
import org.reflections.Reflections;

public abstract class AbstractLoggingModule extends AbstractPropertyModule {
  public AbstractLoggingModule() {
    super();
  }

  public AbstractLoggingModule(Reflections reflections) {
    super(reflections);
  }

  @Override
  protected void configure() {
    super.configure();

    LogUtil.configure(propertyManager);
  }
}
