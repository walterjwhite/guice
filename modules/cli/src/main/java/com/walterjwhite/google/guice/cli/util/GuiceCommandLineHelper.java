package com.walterjwhite.google.guice.cli.util;

import com.walterjwhite.google.guice.GuiceHelper;
import com.walterjwhite.google.guice.cli.AbstractCommandLineModule;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Main class that starts guice and the application. */
public class GuiceCommandLineHelper {
  private static final Logger LOGGER = LoggerFactory.getLogger(GuiceCommandLineHelper.class);

  public static final Reflections REFLECTIONS_INSTANCE = Reflections.collect();
  private static AbstractCommandLineModule COMMAND_LINE_MODULE;

  private GuiceCommandLineHelper() {}

  public static void main(final String[] arguments) {
    try {
      COMMAND_LINE_MODULE = getCommandLineModule(arguments);
      GuiceHelper.addModule(COMMAND_LINE_MODULE);
      GuiceHelper.setup(COMMAND_LINE_MODULE.getApplicationEnvironment());

      COMMAND_LINE_MODULE.doRun(/*arguments*/ );
      doExit("Finished execution, exiting ...", 0);
    } catch (Exception e) {
      LOGGER.error("Error running", e);
      doExit("Error running", 1);
    }
  }

  /** TODO: set a property to this value to eliminate the dependency on reflection. */
  private static AbstractCommandLineModule getCommandLineModule(final String[] arguments)
      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException,
          InstantiationException {
    final Class<? extends AbstractCommandLineModule> commandLineModuleClass =
        getCommandLineModuleClass();
    final Constructor<? extends AbstractCommandLineModule> commandLineModuleConstructor =
        commandLineModuleClass.getDeclaredConstructor(Reflections.class);
    AbstractCommandLineModule module =
        commandLineModuleConstructor.newInstance(REFLECTIONS_INSTANCE);
    module.setArguments(arguments);

    return (module);
  }

  private static Class<? extends AbstractCommandLineModule> getCommandLineModuleClass() {
    return REFLECTIONS_INSTANCE.getSubTypesOf(AbstractCommandLineModule.class).iterator().next();
  }

  /** This *MUST* NOT be called within a shutdown hook, otherwise, the thread will deadlock */
  private static void doExit(final String message, final int exitCode) {
    logExitMessage(message, exitCode);
    System.exit(exitCode);
  }

  private static void logExitMessage(final String message, final int exitCode) {
    if (exitCode == 0) {
      LOGGER.debug(message);
    } else {
      LOGGER.error(message);
    }
  }
}
