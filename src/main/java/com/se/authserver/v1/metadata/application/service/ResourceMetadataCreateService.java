package com.se.authserver.v1.metadata.application.service;

import com.se.authserver.v1.metadata.application.dto.request.ResourceMetadataCreateRequest;
import org.springframework.stereotype.Service;
import com.se.authserver.v1.common.domain.exception.UniqueValueAlreadyExistsException;
import com.se.authserver.v1.metadata.domain.model.Resource;
import com.se.authserver.v1.metadata.domain.model.ResourceMetadata;
import com.se.authserver.v1.metadata.domain.repository.MetadataRepositoryProtocol;

@Service
public class ResourceMetadataCreateService {

  private final MetadataRepositoryProtocol metadataRepositoryProtocol;

  public ResourceMetadataCreateService(
      MetadataRepositoryProtocol metadataRepositoryProtocol) {
    this.metadataRepositoryProtocol = metadataRepositoryProtocol;
  }

  public Long create(ResourceMetadataCreateRequest request) {
    Resource resource = request.getResource();
    String name = request.getName();

    checkDuplicatedResourceMetadata(name, resource);
    return metadataRepositoryProtocol
        .save(new ResourceMetadata(request.getName(), request.getResource()))
        .getResourceMetadataId();
  }

  private void checkDuplicatedResourceMetadata(String name, Resource resource) {
    if (metadataRepositoryProtocol.readOne(name, resource) != null) {
      throw new UniqueValueAlreadyExistsException("이미 등록된 데이터입니다.");
    }
  }
}
