package se.authserver.v1.metadata.domain.repository;

import java.util.List;
import se.authserver.v1.metadata.domain.model.Resource;
import se.authserver.v1.metadata.domain.model.ResourceMetadata;

public interface MetadataRepositoryProtocol {
  ResourceMetadata create(ResourceMetadata resourceMetadata);
  ResourceMetadata readOne(String name, Resource resource);
  List<ResourceMetadata> readAllByResource(Resource resource);
}
