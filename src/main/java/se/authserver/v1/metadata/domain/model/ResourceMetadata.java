package se.authserver.v1.metadata.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import se.authserver.v1.common.domain.model.BaseEntity;

@Entity
@Getter
@NoArgsConstructor
public class ResourceMetadata extends BaseEntity {

  public ResourceMetadata(Resource resource, String name) {
    this.resource = resource;
    this.name = name;
  }

  @Id @GeneratedValue
  private Long metadataId;

  @Column(length = 45, nullable = false)
  @Enumerated(EnumType.STRING)
  private Resource resource;

  @Column(length = 45, nullable = false)
  private String name;

  // TODO 필요한 속성 추가
}
