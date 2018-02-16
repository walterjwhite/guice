package com.walterjwhite.google.guice.property.util;

import com.google.common.util.concurrent.Service;
import com.google.inject.ConfigurationException;
import com.walterjwhite.google.guice.GuiceHelper;
import com.walterjwhite.google.guice.property.property.ServiceStopTimeout;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceManager {
  protected final Logger LOGGER = LoggerFactory.getLogger(ServiceManager.class);

  protected final Set<Class<? extends Service>> installedServices = new HashSet<>();
  protected final PropertyManager propertyManager;

  public ServiceManager(PropertyManager propertyManager) {
    this.propertyManager = propertyManager;
  }

  public void startServices() {
    installConcreteServices();
    installedServices.forEach(service -> doStartService(service));
  }

  protected void installConcreteServices() {
    installedServices.addAll(
        propertyManager
            .getReflections()
            .getSubTypesOf(Service.class)
            .stream()
            .filter(c -> isConcrete(c))
            .collect(Collectors.toList()));
  }

  protected boolean isConcrete(final Class<? extends Service> serviceClass) {
    return (!Modifier.isAbstract(serviceClass.getModifiers()));
  }

  public void stopServices() {
    installedServices.forEach(service -> doStopService(service));
  }

  protected void doStartService(final Class<? extends Service> serviceClass) {
    try {
      final Service service = GuiceHelper.getGuiceInjector().getInstance(serviceClass);

      if (!service.isRunning()) service.startAsync();
      else LOGGER.debug("service was already started, perhaps because it is an eager singleton?");
    } catch (ConfigurationException e) {
      LOGGER.warn("Service may NOT be a Guice service, check configuration", e);
    }
  }

  protected void doStopService(final Class<? extends Service> serviceClass) {
    try {
      final Service service = GuiceHelper.getGuiceInjector().getInstance(serviceClass);
      if (service.isRunning())
        service
            .stopAsync()
            .awaitTerminated(
                Long.valueOf(propertyManager.getValue(ServiceStopTimeout.class)), TimeUnit.SECONDS);
      else LOGGER.warn("Service:" + service + " is already stopped.");
    } catch (TimeoutException | IllegalStateException e) {
      LOGGER.warn("Service is still stopping, killing.");
    }
  }
}
