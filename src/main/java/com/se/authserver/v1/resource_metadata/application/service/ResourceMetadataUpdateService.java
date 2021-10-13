package com.se.authserver.v1.resource_metadata.application.service;

import com.se.authserver.v1.common.domain.exception.NotFoundException;
import com.se.authserver.v1.resource_metadata.domain.model.ResourceMetadata;
import com.se.authserver.v1.resource_metadata.domain.repository.MetadataRepositoryProtocol;
import org.springframework.stereotype.Service;
import com.se.authserver.v1.resource_metadata.application.dto.request.ResourceMetadataUpdateRequest;

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
