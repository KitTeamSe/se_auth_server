package se.authserver.v1.metadata.domain.repository;

import java.util.List;
import java.util.Optional;
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
