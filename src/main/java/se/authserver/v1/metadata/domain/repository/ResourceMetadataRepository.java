package se.authserver.v1.metadata.domain.repository;

import org.springframework.stereotype.Repository;
import se.authserver.v1.metadata.domain.model.Resource;
import se.authserver.v1.metadata.domain.model.ResourceMetadata;
import se.authserver.v1.metadata.infra.repository.MetadataJpaRepository;

@Repository
public class ResourceMetadataRepository implements MetadataRepositoryProtocol {

  private final MetadataJpaRepository jpa;

  public ResourceMetadataRepository(
      MetadataJpaRepository jpa) {
    this.jpa = jpa;
  }

  @Override
  public ResourceMetadata create(ResourceMetadata resourceMetadata) {
    return jpa.save(resourceMetadata);
  }

  @Override
  public ResourceMetadata readOne(Resource resource, String name) {
    return jpa.findByResourceAndName(resource, name);
  }
}
