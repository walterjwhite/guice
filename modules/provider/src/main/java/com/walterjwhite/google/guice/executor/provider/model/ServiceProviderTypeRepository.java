package com.walterjwhite.google.guice.executor.provider.model;

import com.walterjwhite.datastore.criteria.AbstractRepository;
import com.walterjwhite.datastore.criteria.CriteriaQueryConfiguration;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;

public class ServiceProviderTypeRepository extends AbstractRepository<ServiceProviderType> {
  public ServiceProviderTypeRepository(
      EntityManager entityManager,
      CriteriaBuilder criteriaBuilder,
      Class<ServiceProviderType> serviceProviderTypeClass) {
    super(entityManager, criteriaBuilder, serviceProviderTypeClass);
  }

  public ServiceProviderType findByServiceProviderTypeClassName(
      final String serviceProviderTypeClassName) {
    final CriteriaQueryConfiguration<ServiceProviderType> jobCriteriaQueryConfiguration =
        getCriteriaQuery();

    Predicate condition =
        criteriaBuilder.equal(
            jobCriteriaQueryConfiguration
                .getRoot()
                .get(ServiceProviderType_.serviceInterfaceClassname),
            serviceProviderTypeClassName);
    jobCriteriaQueryConfiguration.getCriteriaQuery().where(condition);

    return entityManager
        .createQuery(jobCriteriaQueryConfiguration.getCriteriaQuery())
        .getSingleResult();
  }
}
