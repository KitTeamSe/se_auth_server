package com.se.authserver.v1.app.application.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.se.authserver.v1.app.domain.model.DevelopProgress;
import com.se.authserver.v1.resource_metadata_app_mapping.domain.model.IsEssentialInfo;
import io.swagger.annotations.ApiModel;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AppCreateRequest {

  @Getter
  @NoArgsConstructor
  @Builder
  @ApiModel(value = "애플리케이션 등록 요청")
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

    @NotNull
    private String name;

    @NotNull
    private DevelopProgress developProgress;

    @JsonInclude(Include.NON_NULL)
    private List<String> callbackUrls;

    @JsonInclude(Include.NON_NULL)
    private List<ResourceMetadataDto> resourceMetadataDtos;
  }

  @Getter
  @NoArgsConstructor
  @Builder
  @ApiModel(value = "메타데이터 등록")
  static public class ResourceMetadataDto {

    private Long resourceMetadataId;
    private IsEssentialInfo isEssentialInfo;

    public ResourceMetadataDto(Long resourceMetadataId, IsEssentialInfo isEssentialInfo) {
      this.resourceMetadataId = resourceMetadataId;
      this.isEssentialInfo = isEssentialInfo;
    }
  }
}
