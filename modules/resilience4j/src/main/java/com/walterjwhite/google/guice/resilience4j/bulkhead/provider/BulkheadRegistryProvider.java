package com.walterjwhite.google.guice.resilience4j.bulkhead.provider;

import io.github.resilience4j.bulkhead.BulkheadConfig;
import io.github.resilience4j.bulkhead.BulkheadRegistry;
import javax.inject.Inject;
import javax.inject.Provider;

public class BulkheadRegistryProvider implements Provider<BulkheadRegistry> {
  protected final BulkheadRegistry bulkheadRegistry;

  @Inject
  public BulkheadRegistryProvider(BulkheadConfig bulkheadConfig) {
    super();
    bulkheadRegistry = BulkheadRegistry.of(bulkheadConfig);
  }

  @Override
  public BulkheadRegistry get() {
    return bulkheadRegistry;
  }
}
