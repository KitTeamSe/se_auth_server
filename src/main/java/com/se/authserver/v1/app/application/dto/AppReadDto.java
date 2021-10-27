package com.se.authserver.v1.app.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.se.authserver.v1.app.domain.model.DevelopProgress;
import com.se.authserver.v1.resource_metadata.domain.model.Resource;
import com.se.authserver.v1.resource_metadata_app_mapping.domain.model.IsEssentialInfo;
import io.swagger.annotations.ApiModel;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


public class AppReadDto {

  @NoArgsConstructor
  @Builder
  @Getter
  @ApiModel(value = "애플리케이션 조회 응답")
  static public class Response {

    private Long appId;

    private String appName;

    private String clientId;

    private String clientSecret; // 복호화된 비밀번호

    @JsonInclude(Include.NON_NULL)
    private DevelopProgress developProgress;

    @JsonInclude(Include.NON_NULL)
    private List<CallbackUrlDto> callbackUrls;

    @JsonInclude(Include.NON_NULL)
    private List<ResourceMetadataDto> resourceMetadataDtos;

    public Response(Long appId, String appName, String clientId, String clientSecret,
        DevelopProgress developProgress, List<CallbackUrlDto> callbackUrls,
        List<ResourceMetadataDto> resourceMetadataDtos) {
      this.appId = appId;
      this.appName = appName;
      this.clientId = clientId;
      this.clientSecret = clientSecret;
      this.developProgress = developProgress;
      this.callbackUrls = callbackUrls;
      this.resourceMetadataDtos = resourceMetadataDtos;
    }
  }

  @NoArgsConstructor
  @Builder
  @Getter
  @ApiModel(value = "Callback URL 조회")
  static public class CallbackUrlDto {

    private Long callbackUrlId;
    private String url;

    public CallbackUrlDto(Long callbackUrlId, String url) {
      this.callbackUrlId = callbackUrlId;
      this.url = url;
    }
  }

  @NoArgsConstructor
  @Builder
  @Getter
  @ApiModel(value = "메타 데이터 조회")
  static public class ResourceMetadataDto {

    private Long resourceMetadataId;
    private Resource resource;
    private String resourceMetadataName;
    private IsEssentialInfo isEssentialInfo;

    public ResourceMetadataDto(Long resourceMetadataId,
        Resource resource, String resourceMetadataName,
        IsEssentialInfo isEssentialInfo) {
      this.resourceMetadataId = resourceMetadataId;
      this.resource = resource;
      this.resourceMetadataName = resourceMetadataName;
      this.isEssentialInfo = isEssentialInfo;
    }
  }
}
