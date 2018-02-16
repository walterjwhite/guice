package com.walterjwhite.google.guice.cli.service;

import java.util.concurrent.TimeUnit;

public interface CommandLineDaemon /*extends Callable<Void>*/ {
  /** @return Maximum runtime for this daemon */
  long getTimeout();

  /** @return Units for the timeout */
  TimeUnit getTimeoutUnit();
}
