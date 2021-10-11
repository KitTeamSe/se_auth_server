package se.authserver.v1.metadata.domain.repository;

import java.util.List;
import java.util.Optional;
import se.authserver.v1.metadata.domain.model.Resource;
import se.authserver.v1.metadata.domain.model.ResourceMetadata;

public interface MetadataRepositoryProtocol {

  ResourceMetadata save(ResourceMetadata resourceMetadata);

  ResourceMetadata readOne(String name, Resource resource);

  Optional<ResourceMetadata> readById(Long id);

  List<ResourceMetadata> readAllByResource(Resource resource);
}
