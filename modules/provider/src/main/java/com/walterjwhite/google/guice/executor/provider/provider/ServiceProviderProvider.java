package com.walterjwhite.google.guice.executor.provider.provider;

import com.walterjwhite.datastore.criteria.Repository;
import javax.inject.Inject;

public class ServiceProviderProvider {
  protected final Repository repository;

  @Inject
  public ServiceProviderProvider(Repository repository) {
    super();
    this.repository = repository;
  }

  // find the default provider for a given service interface classname
  //    public ServiceProviderType get(){
  //        repository.findAll()
  //    }
}
