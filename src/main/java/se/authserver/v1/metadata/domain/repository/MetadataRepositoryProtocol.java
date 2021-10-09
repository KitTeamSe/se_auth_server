package se.authserver.v1.metadata.domain.repository;

import se.authserver.v1.metadata.domain.model.ResourceMetadata;

public interface MetadataRepositoryProtocol {
  public ResourceMetadata create(ResourceMetadata resourceMetadata);
}
