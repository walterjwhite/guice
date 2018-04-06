package com.walterjwhite.google.guice.cli.service;

import com.walterjwhite.google.guice.cli.property.CommandLineHandlerShutdownTimeout;
import com.walterjwhite.google.guice.property.annotation.Property;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractCommandLineHandler {
  private static final Logger LOGGER = LoggerFactory.getLogger(AbstractCommandLineHandler.class);
  protected final long shutdownTimeoutInSeconds;

  protected final Thread shutdownHook = new ShutdownHook();
  protected final Thread mainThread = Thread.currentThread();

  public AbstractCommandLineHandler(
      @Property(CommandLineHandlerShutdownTimeout.class) int shutdownTimeoutInSeconds) {
    super();
    this.shutdownTimeoutInSeconds = shutdownTimeoutInSeconds;
    Runtime.getRuntime().addShutdownHook(shutdownHook);
  }

  public abstract void run(final String... arguments) throws Exception;

  protected void onShutdown() throws Exception {}

  public class ShutdownHook extends Thread {
    @Override
    public void run() {
      try {
        onShutdown();
      } catch (Exception e) {
        LOGGER.error("error exiting:", e);
      } finally {
        try {
          mainThread.join(shutdownTimeoutInSeconds);
        } catch (InterruptedException e) {
          LOGGER.warn("Error waiting for shutdown", e);
        }
      }
    }
  }
}
