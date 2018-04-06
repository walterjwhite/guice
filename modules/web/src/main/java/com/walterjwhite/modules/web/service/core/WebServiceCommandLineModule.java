package com.walterjwhite.modules.web.service.core;

import com.walterjwhite.google.guice.cli.AbstractCommandLineModule;
import com.walterjwhite.modules.web.service.core.enumeration.WebServiceOperatingMode;
import org.reflections.Reflections;

public class WebServiceCommandLineModule extends AbstractCommandLineModule {
  // protected final Set<Class<? extends ServletModule>> servletModuleClases;

  public WebServiceCommandLineModule(Reflections reflections) {
    super(reflections, WebServiceOperatingMode.class);

    // servletModuleClases = reflections.getSubTypesOf(ServletModule.class);
  }

  @Override
  protected void doConfigure() {
    /*
    for(final Class<? extends ServletModule> servletModuleClass:servletModuleClases) {
      try {
        install(servletModuleClass.newInstance());
      } catch (InstantiationException|IllegalAccessException e) {
      throw(new RuntimeException("Configuration error", e));
      }
    }
    */
  }
}
