package se.authserver.v1.metadata.presentation.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import se.authserver.v1.common.presentation.response.Response;
import se.authserver.v1.metadata.application.dto.request.ResourceMetadataCreateRequest;
import se.authserver.v1.metadata.application.service.ResourceMetadataCreateService;

@RestController
@Api(tags = "리소스 별 데이터 관리")
@RequestMapping("/api/v1")
public class ResourceMetadataApiController {

  private final ResourceMetadataCreateService resourceMetadataCreateService;

  public ResourceMetadataApiController(
      ResourceMetadataCreateService resourceMetadataCreateService) {
    this.resourceMetadataCreateService = resourceMetadataCreateService;
  }

  @PostMapping("/metadata")
  @ResponseStatus(HttpStatus.CREATED)
  @ApiOperation(value = "서비스 생성")
  public Response<Long> create(@RequestBody ResourceMetadataCreateRequest request) {
    return new Response<>(HttpStatus.OK, "성공적으로 등록하였습니다.",
        resourceMetadataCreateService.create(request));
  }
}
