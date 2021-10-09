package se.authserver.v1.metadata.domain.repository;

import se.authserver.v1.metadata.domain.model.Resource;
import se.authserver.v1.metadata.domain.model.ResourceMetadata;

public interface MetadataRepositoryProtocol {
  ResourceMetadata readOne(Resource resource, String name);
  ResourceMetadata create(ResourceMetadata resourceMetadata);
}
