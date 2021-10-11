package se.authserver.v1.metadata.application.service;

import org.springframework.stereotype.Service;
import se.authserver.v1.common.domain.exception.NotFoundException;
import se.authserver.v1.metadata.application.dto.request.ResourceMetadataUpdateRequest;
import se.authserver.v1.metadata.domain.model.ResourceMetadata;
import se.authserver.v1.metadata.domain.repository.MetadataRepositoryProtocol;

@Service
public class ResourceMetadataUpdateService {

  private final MetadataRepositoryProtocol metadataRepositoryProtocol;

  public ResourceMetadataUpdateService(
      MetadataRepositoryProtocol metadataRepositoryProtocol) {
    this.metadataRepositoryProtocol = metadataRepositoryProtocol;
  }

  public Long update(ResourceMetadataUpdateRequest request) {
    ResourceMetadata resourceMetadata = metadataRepositoryProtocol.readById(request.getMetadataId())
        .orElseThrow(() -> new NotFoundException("존재하지 않는 메타 데이터입니다."));
    resourceMetadata.update(request.getName(), request.getResource());
    resourceMetadata = metadataRepositoryProtocol.save(resourceMetadata);
    return resourceMetadata.getMetadataId();
  }
}
