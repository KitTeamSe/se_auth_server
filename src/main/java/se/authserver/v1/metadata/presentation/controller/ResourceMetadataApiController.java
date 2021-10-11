package se.authserver.v1.metadata.presentation.controller;

import com.fasterxml.jackson.databind.JsonSerializer.None;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import se.authserver.v1.common.presentation.response.Response;
import se.authserver.v1.metadata.application.dto.ResourceMetadataReadDto;
import se.authserver.v1.metadata.application.dto.request.ResourceMetadataCreateRequest;
import se.authserver.v1.metadata.application.service.ResourceMetadataCreateService;
import se.authserver.v1.metadata.application.service.ResourceMetadataDeleteService;
import se.authserver.v1.metadata.application.service.ResourceMetadataReadService;
import se.authserver.v1.metadata.domain.model.Resource;

@RestController
@Api(tags = "리소스 별 데이터 관리")
@RequestMapping("/api/v1")
public class ResourceMetadataApiController {

  private final ResourceMetadataCreateService resourceMetadataCreateService;
  private final ResourceMetadataReadService resourceMetadataReadService;
  private final ResourceMetadataDeleteService resourceMetadataDeleteService;

  public ResourceMetadataApiController(
      ResourceMetadataCreateService resourceMetadataCreateService,
      ResourceMetadataReadService resourceMetadataReadService,
      ResourceMetadataDeleteService resourceMetadataDeleteService) {
    this.resourceMetadataCreateService = resourceMetadataCreateService;
    this.resourceMetadataReadService = resourceMetadataReadService;
    this.resourceMetadataDeleteService = resourceMetadataDeleteService;
  }

  @PostMapping("/metadata")
  @ResponseStatus(HttpStatus.CREATED)
  @ApiOperation(value = "데이터 생성")
  public Response<Long> create(@RequestBody ResourceMetadataCreateRequest request) {
    return new Response<>(HttpStatus.OK, "성공적으로 등록하였습니다.",
        resourceMetadataCreateService.create(request));
  }

  @DeleteMapping("/metadata/{id}")
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation(value = "데이터 삭제")
  public Response delete(@PathVariable(value = "id") Long id) {
    resourceMetadataDeleteService.delete(id);
    return new Response(HttpStatus.OK, "성공적으로 삭제하였습니다.");
  }

  @GetMapping("/metadata")
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation(value = "데이터 목록 조회")
  public Response<List<ResourceMetadataReadDto>> readAllByResource(@RequestParam Resource resource) {
    return new Response<>(HttpStatus.OK, "성공적으로 조회하였습니다.", resourceMetadataReadService.readAllByResource(resource));
  }
}
