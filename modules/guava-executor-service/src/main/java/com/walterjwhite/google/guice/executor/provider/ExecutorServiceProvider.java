package com.walterjwhite.google.guice.executor.provider;

import com.google.common.util.concurrent.ListeningScheduledExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.walterjwhite.google.guice.executor.property.NumberOfExecutorServiceThreads;
import com.walterjwhite.google.guice.property.property.Property;
import java.util.concurrent.Executors;
import javax.inject.Inject;
import javax.inject.Provider;

public class ExecutorServiceProvider implements Provider<ListeningScheduledExecutorService> {
  protected final ListeningScheduledExecutorService executorService;

  @Inject
  public ExecutorServiceProvider(
      @Property(NumberOfExecutorServiceThreads.class) int numberOfThreads) {
    super();
    executorService =
        MoreExecutors.listeningDecorator(Executors.newScheduledThreadPool(numberOfThreads));
    // this is needed unless we handle it differently (ie. wrap executor service as an idle service,
    // then let our handler call shutdown on the service)
    Runtime.getRuntime().addShutdownHook(new ShutdownHook());
  }

  @Override
  public ListeningScheduledExecutorService get() {
    return executorService;
  }

  class ShutdownHook extends Thread {
    @Override
    public void run() {
      executorService.shutdownNow();
    }
  }
}
