package se.authserver.v1.metadata.application.service;

import org.springframework.stereotype.Service;
import se.authserver.v1.common.domain.exception.UniqueValueAlreadyExistsException;
import se.authserver.v1.metadata.application.dto.request.ResourceMetadataCreateRequest;
import se.authserver.v1.metadata.domain.model.Resource;
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
    Resource resource = request.getResource();
    String name = request.getName();

    checkDuplicatedResourceMetadata(resource, name);
    ResourceMetadata resourceMetadata = new ResourceMetadata(request.getResource(), request.getName());
    return metadataRepositoryProtocol.create(resourceMetadata).getMetadataId();
  }

  private void checkDuplicatedResourceMetadata(Resource resource, String name) {
    if (metadataRepositoryProtocol.readOne(resource, name) != null) {
      throw new UniqueValueAlreadyExistsException("이미 등록된 데이터입니다.");
    }
  }
}
