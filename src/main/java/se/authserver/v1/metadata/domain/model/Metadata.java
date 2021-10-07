package se.authserver.v1.metadata.domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import se.authserver.v1.common.domain.model.BaseEntity;

@Entity
public class Metadata extends BaseEntity {

  @Id @GeneratedValue
  private Long metadataId;

  // TODO 필요한 속성 추가
}
