package com.se.authserver.v1.resource_metadata.application.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.se.authserver.v1.resource_metadata.domain.model.Resource;

@Getter
@NoArgsConstructor
@Builder
@ApiModel(value = "서비스 조회 응답")
public class ResourceMetadataReadDto {

  ResourceMetadataReadDto(Long metadata_id, String name, Resource resource) {
    this.metadata_id = metadata_id;
    this.name = name;
    this.resource = resource;
  }

  Long metadata_id;
  @ApiModelProperty("서비스 이름")
  String name;
  @ApiModelProperty("서비스 구분 (ACCOUNT, ...)")
  Resource resource;
}
