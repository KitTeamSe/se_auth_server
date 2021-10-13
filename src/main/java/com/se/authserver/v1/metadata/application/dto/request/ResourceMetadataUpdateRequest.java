package com.se.authserver.v1.metadata.application.dto.request;

import com.se.authserver.v1.metadata.domain.model.Resource;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@ApiModel("리소스 별 데이터 수정 요청")
public class ResourceMetadataUpdateRequest {

  public ResourceMetadataUpdateRequest(Long metadataId, String name,
      Resource resource) {
    this.metadataId = metadataId;
    this.name = name;
    this.resource = resource;
  }

  @NotNull
  private Long metadataId;

  @NotBlank
  private String name;

  @ApiModelProperty(notes = "ACCOUNT")
  private Resource resource;
}
