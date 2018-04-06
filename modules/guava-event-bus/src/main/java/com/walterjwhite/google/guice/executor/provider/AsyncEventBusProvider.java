package com.walterjwhite.google.guice.executor.provider;

import com.google.common.eventbus.AsyncEventBus;
import com.walterjwhite.google.guice.annotation.EventBusOnly;
import com.walterjwhite.google.guice.executor.property.AsyncEventBusName;
import com.walterjwhite.google.guice.property.annotation.Property;
import java.util.concurrent.ExecutorService;
import javax.inject.Inject;
import javax.inject.Provider;

public class AsyncEventBusProvider implements Provider<AsyncEventBus> {
  protected final AsyncEventBus asyncEventBus;

  @Inject
  public AsyncEventBusProvider(
      @EventBusOnly ExecutorService executorService,
      @Property(AsyncEventBusName.class) String asyncEventBusName) {
    super();
    asyncEventBus = new AsyncEventBus(asyncEventBusName, executorService);
  }

  @Override
  public AsyncEventBus get() {
    return asyncEventBus;
  }
}
