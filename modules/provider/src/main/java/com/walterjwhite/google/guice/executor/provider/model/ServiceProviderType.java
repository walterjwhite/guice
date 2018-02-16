package com.walterjwhite.google.guice.executor.provider.model;

import com.walterjwhite.datastore.api.model.entity.AbstractNamedEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
public class ServiceProviderType extends AbstractNamedEntity {
  @Column protected String serviceInterfaceClassname;
  @ManyToOne @JoinColumn protected ServiceProvider defaultServiceProvider;

  @OneToMany protected List<ServiceProvider> serviceProviders = new ArrayList<>();

  public ServiceProviderType(
      String name,
      String description,
      String serviceInterfaceClassname,
      ServiceProvider defaultServiceProvider) {
    super(name, description);
    this.serviceInterfaceClassname = serviceInterfaceClassname;
    this.defaultServiceProvider = defaultServiceProvider;
  }

  public ServiceProviderType() {
    super();
  }

  public String getServiceInterfaceClassname() {
    return serviceInterfaceClassname;
  }

  public void setServiceInterfaceClassname(String serviceInterfaceClassname) {
    this.serviceInterfaceClassname = serviceInterfaceClassname;
  }

  public ServiceProvider getDefaultServiceProvider() {
    return defaultServiceProvider;
  }

  public void setDefaultServiceProvider(ServiceProvider defaultServiceProvider) {
    this.defaultServiceProvider = defaultServiceProvider;
  }

  public List<ServiceProvider> getServiceProviders() {
    return serviceProviders;
  }

  public void setServiceProviders(List<ServiceProvider> serviceProviders) {
    this.serviceProviders = serviceProviders;
  }
}
