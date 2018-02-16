package com.walterjwhite.google.guice.cli;

import com.walterjwhite.google.guice.GuiceHelper;
import com.walterjwhite.google.guice.cli.property.OperatingMode;
import com.walterjwhite.google.guice.cli.service.AbstractCommandLineHandler;
import com.walterjwhite.google.guice.cli.service.CommandLineDaemon;
import com.walterjwhite.google.guice.cli.service.CommandLineDaemonService;
import com.walterjwhite.google.guice.cli.util.CLIUtil;
import com.walterjwhite.google.guice.logging.AbstractLoggingModule;
import com.walterjwhite.google.guice.property.property.GuiceProperty;
import java.util.Map;
import org.reflections.Reflections;

public abstract class AbstractCommandLineModule extends AbstractLoggingModule {
  protected final Class<? extends OperatingMode> operatingModeClass;
  // TODO: these are arguments that are passed on the command line but not corresponding to
  // properties, is this needed?  The purpose was not documented.
  @Deprecated protected String[] arguments;

  protected AbstractCommandLineHandler abstractCommandLineHandler;

  public AbstractCommandLineModule(
      Reflections reflections, Class<? extends OperatingMode> operatingModeClass) {
    super(reflections);
    this.operatingModeClass = operatingModeClass;
  }

  @Deprecated
  public String[] getArguments() {
    return arguments;
  }

  @Deprecated
  public void setArguments(String[] arguments) {
    this.arguments = arguments;
  }

  @Override
  protected void configure() {
    setCommandLineProperties(
        CLIUtil.getCommandLineProperties(propertyManager.getPropertyKeys(), arguments));

    super.configure();

    doConfigure();
  }

  protected void setCommandLineProperties(
      final Map<Class<? extends GuiceProperty>, String> guicePropertyMap) {
    for (final Class<? extends GuiceProperty> guiceProperty : guicePropertyMap.keySet())
      propertyManager.setValue(guiceProperty, guicePropertyMap.get(guiceProperty));
  }

  protected abstract void doConfigure();

  protected OperatingMode getOperatingMode(String value) {
    return (OperatingMode) Enum.valueOf((Class<? extends Enum>) operatingModeClass, value);
  }

  // TODO: generalize code from other command line module implementations
  public void doRun(/*final String[] arguments*/ ) throws Exception {
    try {
      startServices();
      doRunInternal();
    } finally {
      stopServices();
    }
  }

  protected void doRunInternal() throws Exception {
    abstractCommandLineHandler =
        GuiceHelper.getGuiceInjector().getInstance(getCommandLineHandlerClass());

    if (CommandLineDaemon.class.isAssignableFrom(abstractCommandLineHandler.getClass())) {
      doRunDaemon(abstractCommandLineHandler);
    } else abstractCommandLineHandler.run(arguments);
  }

  protected Class<? extends AbstractCommandLineHandler> getCommandLineHandlerClass() {
    return (getOperatingMode(propertyManager.getValue(operatingModeClass)).getInitiatorClass());
  }

  protected void doRunDaemon(AbstractCommandLineHandler abstractCommandLineHandler)
      throws Exception {
    //    ExecutorService executor = null;
    //    SimpleTimeLimiter.create(executor).newProxy(abstractCommandLineHandler,
    // CommandLineDaemon.class)
    new CommandLineDaemonService(((CommandLineDaemon) abstractCommandLineHandler)).run();
  }
}
