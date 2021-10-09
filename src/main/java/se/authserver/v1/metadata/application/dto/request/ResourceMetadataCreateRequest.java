package se.authserver.v1.metadata.application.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import se.authserver.v1.metadata.domain.model.Resource;

@Getter
@NoArgsConstructor
@ApiModel("서비스 생성 요청")
public class ResourceMetadataCreateRequest {

  @NotBlank
  String name;

  @ApiModelProperty(notes = "ACCOUNT")
  Resource resource;
}
