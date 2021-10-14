package com.se.authserver.v1.app.application.dto.request;

import com.se.authserver.v1.app.domain.model.DevelopProgress;
import com.se.authserver.v1.resource_metadata_app_mapping.domain.model.IsEssentialInfo;
import io.swagger.annotations.ApiModel;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AppCreateRequest {

  @Getter
  @NoArgsConstructor
  @Builder
  @ApiModel("애플리케이션 등록 요청")
  static public class Request {

    public Request(String name
        , DevelopProgress developProgress
        , List<String> callbackUrls
        , List<ResourceMetadataDto> resourceMetadataDtos) {
      this.name = name;
      this.developProgress = developProgress;
      this.callbackUrls = callbackUrls;
      this.resourceMetadataDtos = resourceMetadataDtos;
    }

    private String name;
    private DevelopProgress developProgress;
    private List<String> callbackUrls;
    private List<ResourceMetadataDto> resourceMetadataDtos;
  }

  @Getter
  @NoArgsConstructor
  @Builder
  static public class ResourceMetadataDto {

    private Long resourceMetadataId;
    private IsEssentialInfo isEssentialInfo;

    public ResourceMetadataDto(Long resourceMetadataId, IsEssentialInfo isEssentialInfo) {
      this.resourceMetadataId = resourceMetadataId;
      this.isEssentialInfo = isEssentialInfo;
    }
  }
}
