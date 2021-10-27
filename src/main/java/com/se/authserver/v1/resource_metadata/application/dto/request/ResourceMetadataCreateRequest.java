package com.se.authserver.v1.resource_metadata.application.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.se.authserver.v1.resource_metadata.domain.model.Resource;

@Getter
@NoArgsConstructor
@ApiModel(value = "리소스 별 데이터 생성 요청")
public class ResourceMetadataCreateRequest {

  public ResourceMetadataCreateRequest(String name, Resource resource) {
    this.name = name;
    this.resource = resource;
  }

  @NotBlank
  private String name;

  @ApiModelProperty(notes = "ACCOUNT")
  private Resource resource;
}
