package com.se.authserver.v1.resource_metadata_app_mapping.domain.model;

import com.se.authserver.v1.resource_metadata.domain.model.ResourceMetadata;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class ResourceMetadataAppMapping {

  public ResourceMetadataAppMapping(
      ResourceMetadata resourceMetadata, Long appId,
      IsEssentialInfo isEssentialInfo) {
    this.resourceMetadata = resourceMetadata;
    this.appId = appId;
    this.isEssentialInfo = isEssentialInfo;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "resource_metadata_app_mapping_id")
  private Long resourceMetadataAppMappingId;

  @ManyToOne
  @JoinColumn(name = "resource_metadata_id")
  private ResourceMetadata resourceMetadata;

  @Column(name = "app_id", nullable = false)
  private Long appId;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private IsEssentialInfo isEssentialInfo;
}
