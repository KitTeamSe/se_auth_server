package se.authserver.v1.metadata.application.service;

import org.springframework.stereotype.Service;
import se.authserver.v1.common.domain.exception.NotFoundException;
import se.authserver.v1.metadata.domain.model.ResourceMetadata;
import se.authserver.v1.metadata.domain.repository.MetadataRepositoryProtocol;

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
