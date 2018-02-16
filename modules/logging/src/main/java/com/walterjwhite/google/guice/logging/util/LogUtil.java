package com.walterjwhite.google.guice.logging.util;

import com.walterjwhite.google.guice.logging.property.StdErr;
import com.walterjwhite.google.guice.logging.property.StdOut;
import com.walterjwhite.google.guice.property.property.GuiceProperty;
import com.walterjwhite.google.guice.property.util.PropertyManager;
import java.io.*;

public class LogUtil {
  public static void configure(final PropertyManager propertyManager) {
    try {
      final File outFile = prepareLog(propertyManager, StdOut.class, StdOut.Default);
      if (outFile != null)
        System.setOut(new PrintStream(new BufferedOutputStream(new FileOutputStream(outFile))));

      final File errFile = prepareLog(propertyManager, StdErr.class, StdErr.Default);
      if (errFile != null)
        System.setErr(new PrintStream(new BufferedOutputStream(new FileOutputStream(errFile))));
    } catch (FileNotFoundException e) {
      throw (new RuntimeException("Logging is mis-configured, please correct", e));
    }
  }

  private static File prepareLog(
      final PropertyManager propertyManager,
      final Class<? extends GuiceProperty> propertyKey,
      final String defaultValue) {
    final String configurationValue = propertyManager.getValue(propertyKey);

    if (configurationValue.equals(defaultValue)) return null;

    final File logFile = new File(configurationValue);
    final File logFileDirectory = logFile.getParentFile();
    if (!logFileDirectory.exists()) logFileDirectory.mkdirs();

    return logFile;
  }
}
