package se.authserver.v1.metadata.infra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.authserver.v1.metadata.domain.model.Resource;
import se.authserver.v1.metadata.domain.model.ResourceMetadata;

public interface MetadataJpaRepository extends JpaRepository<ResourceMetadata,Long> {
  ResourceMetadata findByResourceAndName(Resource resource, String name);
}
