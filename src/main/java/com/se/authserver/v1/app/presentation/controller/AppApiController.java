package com.se.authserver.v1.app.presentation.controller;

import com.se.authserver.v1.app.application.dto.AppReadDto;
import com.se.authserver.v1.app.application.dto.request.AppCreateRequest;
import com.se.authserver.v1.app.application.service.AppCreateService;
import com.se.authserver.v1.app.application.service.AppReadService;
import com.se.authserver.v1.common.presentation.response.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@Api(tags = "애플리케이션 관리")
public class AppApiController {
  private final AppCreateService appCreateService;
  private final AppReadService appReadService;

  public AppApiController(
      AppCreateService appCreateService,
      AppReadService appReadService) {
    this.appCreateService = appCreateService;
    this.appReadService = appReadService;
  }

  @PostMapping("/app")
  @ResponseStatus(HttpStatus.CREATED)
  @ApiOperation(value = "애플리케이션 등록")
  public Response<Long> create(@RequestBody AppCreateRequest.Request request) {

    return new Response<>(HttpStatus.CREATED, "성공적으로 등록하였습니다.", appCreateService.create(request));
  }

  @GetMapping("/app/{id}")
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation(value = "애플리케이션 조회")
  public Response<AppReadDto.Response> read(@PathVariable(value = "id") Long id) {
    return new Response<>(HttpStatus.OK, "성공적으로 조회하였습니다.", appReadService.read(id));
  }
}