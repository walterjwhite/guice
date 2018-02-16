package com.walterjwhite.google.guice.resilience4j.bulkhead.provider;

import com.walterjwhite.google.guice.property.property.Property;
import com.walterjwhite.google.guice.resilience4j.bulkhead.property.MaxConcurrentCalls;
import com.walterjwhite.google.guice.resilience4j.bulkhead.property.MaxWaitTime;
import io.github.resilience4j.bulkhead.BulkheadConfig;
import javax.inject.Inject;
import javax.inject.Provider;

public class BulkheadConfigProvider implements Provider<BulkheadConfig> {
  protected final BulkheadConfig bulkheadConfig;

  @Inject
  public BulkheadConfigProvider(
      @Property(MaxConcurrentCalls.class) int maxConcurrentCalls,
      @Property(MaxWaitTime.class) int maxWaitTime) {
    bulkheadConfig =
        BulkheadConfig.custom()
            .maxConcurrentCalls(maxConcurrentCalls)
            .maxWaitTime(/*Duration.ofMillis(maxWaitTime)*/ maxWaitTime)
            .build();
  }

  @Override
  public BulkheadConfig get() {
    return bulkheadConfig;
  }
}
