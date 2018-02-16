package com.walterjwhite.google.guice.cli.service;

import com.google.common.util.concurrent.AbstractIdleService;
import java.util.concurrent.TimeoutException;

/**
 * TODO: this does *NOT* need to extend AbstractIdleService, however, I need a better way to let
 * this run until terminated inject the execution service and schedule a job for the timeout if the
 * timeout is greater than 0 otherwise, simply let this die when interrupted.
 */
public class CommandLineDaemonService extends AbstractIdleService {
  protected final CommandLineDaemon commandLineDaemon;

  public CommandLineDaemonService(CommandLineDaemon commandLineDaemon) {
    super();
    this.commandLineDaemon = commandLineDaemon;
  }

  @Override
  protected void startUp() throws Exception {}

  @Override
  protected void shutDown() throws Exception {}

  public void run() throws Exception {
    if (!isRunning()) {
      startUp();
    }

    handleTermination();
  }

  protected void handleTermination() throws TimeoutException {
    if (commandLineDaemon.getTimeout() > 0) {
      this.awaitTerminated(commandLineDaemon.getTimeout(), commandLineDaemon.getTimeoutUnit());
    } else this.awaitTerminated();
  }
}
