package com.se.authserver.v1.resource_metadata.application.service;

import com.se.authserver.v1.common.domain.exception.NotFoundException;
import com.se.authserver.v1.resource_metadata.domain.model.ResourceMetadata;
import com.se.authserver.v1.resource_metadata.domain.repository.MetadataRepositoryProtocol;
import org.springframework.stereotype.Service;

@Service
public class ResourceMetadataDeleteService {

  private final MetadataRepositoryProtocol metadataRepositoryProtocol;

  public ResourceMetadataDeleteService(
      MetadataRepositoryProtocol metadataRepositoryProtocol) {
    this.metadataRepositoryProtocol = metadataRepositoryProtocol;
  }

  public void delete(Long id) {
    ResourceMetadata resourceMetadata
        = metadataRepositoryProtocol.readById(id)
        .orElseThrow(() -> new NotFoundException("존재하지 않는 메타 데이터입니다."));
    metadataRepositoryProtocol.delete(resourceMetadata);
  }
}
