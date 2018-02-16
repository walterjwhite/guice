// package com.walterjwhite.google.guice;
//
// import com.google.common.eventbus.AsyncEventBus;
// import com.google.common.eventbus.EventBus;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
//
// import javax.inject.Inject;
// import javax.inject.Singleton;
// import java.util.concurrent.ExecutorService;
//
//// @Singleton
// public class EventPublisher {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(GuavaEventBusModule.class);
//
//    //  private final EventBus asyncEventBus = new EventBus();
//    private final AsyncEventBus asyncEventBus;
//    private final EventBus eventBus;
//
//    @Inject
//    public EventPublisher(ExecutorService executorService) {
//        super();
//
//        asyncEventBus = new AsyncEventBus("defaultAsync", executorService);
//        eventBus = new EventBus();//"defaultAsync", executorService);
//    }
//
//    public void synchronousFire(Object event){
//        eventBus.post(event);
//    }
//
//    public void asynchronousFire(Object event){
//        asyncEventBus.post(event);
//    }
// }
