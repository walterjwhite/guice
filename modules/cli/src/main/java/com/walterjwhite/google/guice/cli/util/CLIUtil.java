package com.walterjwhite.google.guice.cli.util;

import com.walterjwhite.google.guice.property.property.GuiceProperty;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.cli.*;

public class CLIUtil {
  private CLIUtil() {}

  public static Map<Class<? extends GuiceProperty>, String> getCommandLineProperties(
      final Iterable<Class<? extends GuiceProperty>> guiceProperties, final String[] arguments) {
    final Map<Class<? extends GuiceProperty>, String> commandLinePropertyMap = new HashMap<>();

    try {
      final Map<Integer, Option> optionMap = new HashMap<>();

      final Options options = buildOptions(guiceProperties, optionMap);
      final CommandLine commandLine = new DefaultParser().parse(options, arguments);

      parseCommandLineOptions(guiceProperties, commandLinePropertyMap, optionMap, commandLine);
    } catch (ParseException e) {
      throw (new RuntimeException(e));
    }

    return commandLinePropertyMap;
  }

  private static void parseCommandLineOptions(
      final Iterable<Class<? extends GuiceProperty>> guiceProperties,
      final Map<Class<? extends GuiceProperty>, String> commandLinePropertyMap,
      final Map<Integer, Option> optionMap,
      final CommandLine commandLine) {
    int i = 0;
    for (final Class<? extends GuiceProperty> guiceProperty : guiceProperties) {
      Option option = optionMap.get(i++);

      if (commandLine.hasOption(option.getOpt())) {
        commandLinePropertyMap.put(guiceProperty, commandLine.getOptionValue(option.getOpt()));
      }
    }
  }

  private static Options buildOptions(
      Iterable<Class<? extends GuiceProperty>> guiceProperties,
      final Map<Integer, Option> optionMap) {
    final Options options = new Options();

    int i = 0;
    for (Class<? extends GuiceProperty> guicePropertyClass : guiceProperties) {
      final Option option =
          new Option(guicePropertyClass.getSimpleName(), true, guicePropertyClass.getName());
      options.addOption(option);
      optionMap.put(i++, option);
    }

    return (options);
  }
}
