package com.walterjwhite.google.guice.executor.provider.model;

import com.walterjwhite.datastore.api.model.entity.AbstractNamedEntity;
import javax.persistence.*;

@Entity
public class ServiceProvider extends AbstractNamedEntity {
  @Column protected String serviceClassname;
  @ManyToOne @JoinColumn protected ServiceProviderType serviceProviderType;
  //    @Column
  //    protected boolean defaultProvider;

  public ServiceProvider(
      String name,
      String description,
      String serviceClassname,
      ServiceProviderType serviceProviderType) {
    super(name, description);
    this.serviceClassname = serviceClassname;
    this.serviceProviderType = serviceProviderType;
  }

  public ServiceProvider() {
    super();
  }

  public String getServiceClassname() {
    return serviceClassname;
  }

  public void setServiceClassname(String serviceClassname) {
    this.serviceClassname = serviceClassname;
  }

  public ServiceProviderType getServiceProviderType() {
    return serviceProviderType;
  }

  public void setServiceProviderType(ServiceProviderType serviceProviderType) {
    this.serviceProviderType = serviceProviderType;
  }
}
