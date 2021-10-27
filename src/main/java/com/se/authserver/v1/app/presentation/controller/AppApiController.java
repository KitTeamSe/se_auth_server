package com.se.authserver.v1.app.presentation.controller;

import com.se.authserver.v1.app.application.dto.AppReadDto;
import com.se.authserver.v1.app.application.dto.request.AppCreateRequest;
import com.se.authserver.v1.app.application.dto.request.AppUpdateRequest;
import com.se.authserver.v1.app.application.service.AppCreateService;
import com.se.authserver.v1.app.application.service.AppDeleteService;
import com.se.authserver.v1.app.application.service.AppReadService;
import com.se.authserver.v1.app.application.service.AppUpdateService;
import com.se.authserver.v1.common.presentation.response.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@Api(tags = "애플리케이션 관리")
public class AppApiController {
  private final AppCreateService appCreateService;
  private final AppUpdateService appUpdateService;
  private final AppReadService appReadService;
  private final AppDeleteService appDeleteService;

  public AppApiController(
      AppCreateService appCreateService,
      AppUpdateService appUpdateService,
      AppReadService appReadService,
      AppDeleteService appDeleteService) {
    this.appCreateService = appCreateService;
    this.appUpdateService = appUpdateService;
    this.appReadService = appReadService;
    this.appDeleteService = appDeleteService;
  }

  @PostMapping("/app")
  @ResponseStatus(HttpStatus.CREATED)
  @ApiOperation(value = "애플리케이션 등록")
  public Response<Long> create(@RequestBody AppCreateRequest.Request request) {

    return new Response<>(HttpStatus.CREATED, "성공적으로 등록하였습니다.", appCreateService.create(request));
  }

  @PutMapping("/app")
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation(value = "애플리케이션 수정")
  public Response<Long> update(@RequestBody AppUpdateRequest.Request request) {
    return new Response<>(HttpStatus.OK, "성공적으로 수정하였습니다.", appUpdateService.update(request));
  }

  @DeleteMapping("/app/{id}")
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation(value = "애플리케이션 삭제")
  public Response delete(@PathVariable(value = "id") Long id) {
    appDeleteService.delete(id);
    return new Response(HttpStatus.OK, "성공적으로 삭제하였습니다.");
  }

  @GetMapping("/app/{id}")
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation(value = "애플리케이션 조회")
  public Response<AppReadDto.Response> read(@PathVariable(value = "id") Long id) {
    return new Response<>(HttpStatus.OK, "성공적으로 조회하였습니다.", appReadService.read(id));
  }
}