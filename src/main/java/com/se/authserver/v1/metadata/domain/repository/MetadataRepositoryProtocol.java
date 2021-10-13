package com.se.authserver.v1.metadata.domain.repository;

import com.se.authserver.v1.metadata.domain.model.Resource;
import com.se.authserver.v1.metadata.domain.model.ResourceMetadata;
import java.util.List;
import java.util.Optional;

public interface MetadataRepositoryProtocol {

  ResourceMetadata save(ResourceMetadata resourceMetadata);

  ResourceMetadata readOne(String name, Resource resource);

  Optional<ResourceMetadata> readById(Long id);

  void delete(ResourceMetadata resourceMetadata);

  List<ResourceMetadata> readAllByResource(Resource resource);
}
