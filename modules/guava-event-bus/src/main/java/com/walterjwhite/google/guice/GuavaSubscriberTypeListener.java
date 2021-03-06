package com.walterjwhite.google.guice;

import com.google.inject.TypeLiteral;
import com.google.inject.spi.InjectionListener;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;
import com.walterjwhite.google.guice.annotation.EventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This automatically registers listeners upon instantiating them with an event bus. I think this is
 * processing all instantiations for listeners, so it isn't efficient, but maintainable.
 */
public class GuavaSubscriberTypeListener implements TypeListener {
  private static final Logger LOGGER = LoggerFactory.getLogger(GuavaSubscriberTypeListener.class);

  protected final EventBusDelegate eventBusDelegate;

  public GuavaSubscriberTypeListener(EventBusDelegate eventBusDelegate) {
    super();
    this.eventBusDelegate = eventBusDelegate;
  }

  @Override
  public <I> void hear(TypeLiteral<I> typeLiteral, TypeEncounter<I> typeEncounter) {
    EventListener eventListener = typeLiteral.getRawType().getAnnotation(EventListener.class);
    if (eventListener != null) {
      typeEncounter.register(
          (InjectionListener<I>)
              i -> {
                LOGGER.trace("registered:" + i);
                eventBusDelegate.register(i, eventListener.value());
              });
    } else {
      LOGGER.trace("type is not an event listener, ignoring:" + typeLiteral);
    }
  }
}
