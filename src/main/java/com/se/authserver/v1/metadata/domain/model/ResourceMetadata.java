package com.se.authserver.v1.metadata.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.se.authserver.v1.common.domain.model.BaseEntity;

@Entity
@Getter
@NoArgsConstructor
@Table(indexes = @Index(columnList = "resource, name"))
public class ResourceMetadata extends BaseEntity {

  public ResourceMetadata(String name, Resource resource) {
    this.name = name;
    this.resource = resource;
  }
  public void update(String name, Resource resource) {
    this.name = name;
    this.resource = resource;
  }

  @Id
  @GeneratedValue
  private Long metadataId;

  @Column(length = 45, nullable = false)
  @Enumerated(EnumType.STRING)
  private Resource resource;

  @Column(length = 45, nullable = false)
  private String name;
}
