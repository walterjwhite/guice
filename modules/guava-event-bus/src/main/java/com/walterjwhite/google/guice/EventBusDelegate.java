package com.walterjwhite.google.guice;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.google.common.util.concurrent.AbstractIdleService;
import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventBusDelegate extends AbstractIdleService {
  private static final Logger LOGGER = LoggerFactory.getLogger(EventBusDelegate.class);

  protected Set<WeakReference> syncInstances = new HashSet<>();
  protected Set<WeakReference> asyncInstances = new HashSet<>();

  public void register(Object instance, final boolean isAsync) {
    try {
      GuiceHelper.getGuiceInjector();
      doRegister(instance, isAsync);
    } catch (Exception e) {
      LOGGER.warn("not yet initialized");

      if (isAsync) asyncInstances.add(new WeakReference(instance));
      else syncInstances.add(new WeakReference(instance));
    }
  }

  protected void doRegister(Object e, final boolean isAsync) {
    if (isAsync) GuiceHelper.getGuiceInjector().getInstance(AsyncEventBus.class).register(e);
    else GuiceHelper.getGuiceInjector().getInstance(EventBus.class).register(e);

    LOGGER.info("registered:" + e.toString() + " as " + isAsync);
  }

  @Override
  protected void startUp() throws Exception {
    doRegister(asyncInstances, true);
    doRegister(syncInstances, false);
  }

  protected void doRegister(final Set<WeakReference> instances, final boolean isAsync) {
    final Set<WeakReference> registeredInstances = new HashSet<>();

    for (WeakReference weakReference : instances) {
      doRegister(weakReference.get(), isAsync);

      registeredInstances.add(weakReference);
    }

    instances.removeAll(registeredInstances);
  }

  @Override
  protected void shutDown() throws Exception {}
}
