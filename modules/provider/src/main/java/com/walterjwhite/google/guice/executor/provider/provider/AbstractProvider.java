package com.walterjwhite.google.guice.executor.provider.provider;

import com.walterjwhite.google.guice.GuiceHelper;
import com.walterjwhite.google.guice.executor.provider.ServiceProviderTypeRepository;
import com.walterjwhite.google.guice.executor.provider.model.ServiceProviderType;
import javax.inject.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractProvider<ProviderType> implements Provider<ProviderType> {
  private static final Logger LOGGER = LoggerFactory.getLogger(AbstractProvider.class);
  protected final Class<? extends ProviderType> serviceInterfaceClass;
  protected final ServiceProviderTypeRepository serviceProviderTypeRepository;
  //    protected final String serviceProviderTypeClassName;
  protected final ServiceProviderType serviceProviderType;

  public AbstractProvider(
      Class<? extends ProviderType> serviceInterfaceClass,
      ServiceProviderTypeRepository serviceProviderTypeRepository) {
    super();
    this.serviceInterfaceClass = serviceInterfaceClass;
    this.serviceProviderTypeRepository = serviceProviderTypeRepository;

    this.serviceProviderType =
        this.serviceProviderTypeRepository.findByServiceProviderTypeClassName(
            serviceInterfaceClass.getName());
  }

  @Override
  public ProviderType get() {
    try {
      return (ProviderType)
          GuiceHelper.getGuiceInjector()
              .getInstance(Class.forName(serviceProviderType.getServiceInterfaceClassname()));
    } catch (ClassNotFoundException e) {
      LOGGER.error("Error getting instance", e);
      throw (new RuntimeException("Error getting instance", e));
    }
  }
}
