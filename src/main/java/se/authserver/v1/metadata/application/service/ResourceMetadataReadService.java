package se.authserver.v1.metadata.application.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import se.authserver.v1.metadata.application.dto.ResourceMetadataReadDto;
import se.authserver.v1.metadata.domain.model.Resource;
import se.authserver.v1.metadata.domain.repository.MetadataRepositoryProtocol;

@Service
public class ResourceMetadataReadService {
  private final MetadataRepositoryProtocol metadataRepositoryProtocol;

  public ResourceMetadataReadService(
      MetadataRepositoryProtocol metadataRepositoryProtocol) {
    this.metadataRepositoryProtocol = metadataRepositoryProtocol;
  }

  public List<ResourceMetadataReadDto> readAllByResource(Resource resource) {
      return metadataRepositoryProtocol.readAllByResource(resource)
          .stream().map(r -> ResourceMetadataReadDto.builder()
              .metadata_id(r.getMetadataId())
              .name(r.getName())
              .resource(r.getResource())
              .build())
          .collect(Collectors.toList());
    }
}
