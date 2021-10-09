package se.authserver.v1.metadata.application.service;

import org.springframework.stereotype.Service;
import se.authserver.v1.metadata.application.dto.request.ResourceMetadataCreateRequest;
import se.authserver.v1.metadata.domain.model.ResourceMetadata;
import se.authserver.v1.metadata.domain.repository.MetadataRepositoryProtocol;

@Service
public class ResourceMetadataCreateService {
  private final MetadataRepositoryProtocol metadataRepositoryProtocol;

  public ResourceMetadataCreateService(
      MetadataRepositoryProtocol metadataRepositoryProtocol) {
    this.metadataRepositoryProtocol = metadataRepositoryProtocol;
  }

  public Long create(ResourceMetadataCreateRequest request) {
    ResourceMetadata resourceMetadata = new ResourceMetadata(request.getResource(), request.getName());
    return metadataRepositoryProtocol.create(resourceMetadata).getMetadataId();
  }
}
