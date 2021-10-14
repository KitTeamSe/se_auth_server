package com.se.authserver.v1.resource_metadata.domain.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import com.se.authserver.v1.resource_metadata.domain.model.Resource;
import com.se.authserver.v1.resource_metadata.domain.model.ResourceMetadata;
import com.se.authserver.v1.resource_metadata.infra.repository.MetadataJpaRepository;

@Repository
public class ResourceMetadataRepository implements MetadataRepositoryProtocol {

  private final MetadataJpaRepository jpa;

  public ResourceMetadataRepository(
      MetadataJpaRepository jpa) {
    this.jpa = jpa;
  }

  @Override
  public ResourceMetadata save(ResourceMetadata resourceMetadata) {
    return jpa.save(resourceMetadata);
  }

  @Override
  public ResourceMetadata readOne(String name, Resource resource) {
    return jpa.findByResourceAndName(resource, name);
  }

  @Override
  public Optional<ResourceMetadata> readById(Long id) {
    return jpa.findById(id);
  }

  @Override
  public void delete(ResourceMetadata resourceMetadata) {
    jpa.delete(resourceMetadata);
  }

  @Override
  public List<ResourceMetadata> readAllByResource(Resource resource) {
    return jpa.findAllByResource(resource);
  }
}
