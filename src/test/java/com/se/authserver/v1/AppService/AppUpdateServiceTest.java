package com.se.authserver.v1.AppService;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

import com.se.authserver.v1.app.application.dto.request.AppUpdateRequest;
import com.se.authserver.v1.app.application.dto.request.AppUpdateRequest.Request;
import com.se.authserver.v1.app.application.dto.request.AppUpdateRequest.ResourceMetadataDto;
import com.se.authserver.v1.app.application.service.AppUpdateService;
import com.se.authserver.v1.app.domain.model.App;
import com.se.authserver.v1.app.domain.model.DevelopProgress;
import com.se.authserver.v1.app.domain.repository.AppRepositoryProtocol;
import com.se.authserver.v1.common.domain.exception.NotFoundException;
import com.se.authserver.v1.common.domain.exception.PreconditionFailedException;
import com.se.authserver.v1.resource_metadata.domain.model.Resource;
import com.se.authserver.v1.resource_metadata.domain.model.ResourceMetadata;
import com.se.authserver.v1.resource_metadata.domain.repository.MetadataRepositoryProtocol;
import com.se.authserver.v1.resource_metadata_app_mapping.domain.model.IsEssentialInfo;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AppUpdateServiceTest {

  @Mock
  AppRepositoryProtocol appRepositoryProtocol;

  @Mock
  MetadataRepositoryProtocol metadataRepositoryProtocol;

  @InjectMocks
  AppUpdateService appUpdateService;

  @Test
  void 애플리케이션_수정_성공() {
    // given
    List<String> callbackUrls = Arrays.asList("callback_url");
    List<ResourceMetadataDto> resourceMetadataDtos
        = Arrays.asList(new ResourceMetadataDto(1L, IsEssentialInfo.ESSENTIAL));
    AppUpdateRequest.Request request = new Request(1L
        , "name"
        , DevelopProgress.PROCEEDING
        , callbackUrls
        , resourceMetadataDtos);
    App app = new App("name"
        , "client_id"
        , "client_secret"
        , DevelopProgress.PROCEEDING);
    ResourceMetadata resourceMetadata = new ResourceMetadata("name", Resource.ACCOUNT);

    given(appRepositoryProtocol.findById(request.getId())).willReturn(java.util.Optional.of(app));
    given(appRepositoryProtocol.save(app)).willReturn(new App("name"
        , "client_id"
        , "client_secret"
        , DevelopProgress.PROCEEDING));
    given(metadataRepositoryProtocol
        .readById(request.getResourceMetadataDtos().get(0).getResourceMetadataId()))
        .willReturn(java.util.Optional.of(resourceMetadata));

    // when, then
    assertDoesNotThrow(() -> appUpdateService.update(request));
  }

  @Test
  void 존재하지_않는_애플리케이션() {
    // given
    List<String> callbackUrls = Arrays.asList("callback_url");
    List<ResourceMetadataDto> resourceMetadataDtos
        = Arrays.asList(new ResourceMetadataDto(1L, IsEssentialInfo.ESSENTIAL));
    AppUpdateRequest.Request request = new Request(1L
        , "name"
        , DevelopProgress.PROCEEDING
        , callbackUrls
        , resourceMetadataDtos);

    // when
    NotFoundException notFoundException
        = assertThrows(NotFoundException.class, () -> appUpdateService.update(request));

    // then
    assertThat(notFoundException.getMessage(), is("존재하지 않는 애플리케이션입니다."));
  }

  @Test
  void 존재하지_않는_메타_데이터() {
    // given
    List<String> callbackUrls = Arrays.asList("callback_url");
    List<ResourceMetadataDto> resourceMetadataDtos
        = Arrays.asList(new ResourceMetadataDto(1L, IsEssentialInfo.ESSENTIAL));
    AppUpdateRequest.Request request = new Request(1L
        , "name"
        , DevelopProgress.PROCEEDING
        , callbackUrls
        , resourceMetadataDtos);
    App app = new App("name"
        , "client_id"
        , "client_secret"
        , DevelopProgress.PROCEEDING);
    ResourceMetadata resourceMetadata = new ResourceMetadata("name", Resource.ACCOUNT);

    given(appRepositoryProtocol.findById(request.getId())).willReturn(java.util.Optional.of(app));
    given(appRepositoryProtocol.save(app)).willReturn(new App("name"
        , "client_id"
        , "client_secret"
        , DevelopProgress.PROCEEDING));
    given(metadataRepositoryProtocol
        .readById(request.getResourceMetadataDtos().get(0).getResourceMetadataId()))
        .willThrow(new NotFoundException("존재하지 않는 메타 데이터입니다."));

    // when
    NotFoundException notFoundException
        = assertThrows(NotFoundException.class, () -> appUpdateService.update(request));

    // then
    assertThat(notFoundException.getMessage(), is("존재하지 않는 메타 데이터입니다."));
  }

  @Test
  void CALLBACK_URL_미설정으로_인한_데이터_등록_실패() {
    // given
    // given
    List<String> callbackUrls = null;
    List<ResourceMetadataDto> resourceMetadataDtos
        = Arrays.asList(new ResourceMetadataDto(1L, IsEssentialInfo.ESSENTIAL));
    AppUpdateRequest.Request request = new Request(1L
        , "name"
        , DevelopProgress.PROCEEDING
        , callbackUrls
        , resourceMetadataDtos);
    App app = new App("name"
        , "client_id"
        , "client_secret"
        , DevelopProgress.PROCEEDING);
    ResourceMetadata resourceMetadata = new ResourceMetadata("name", Resource.ACCOUNT);

    given(appRepositoryProtocol.findById(request.getId())).willReturn(java.util.Optional.of(app));
    given(appRepositoryProtocol.save(app)).willReturn(new App("name"
        , "client_id"
        , "client_secret"
        , DevelopProgress.PROCEEDING));

    // when
    PreconditionFailedException preconditionFailedException
        = assertThrows(PreconditionFailedException.class, () -> appUpdateService.update(request));

    // then
    assertThat(preconditionFailedException.getMessage(), is("최소 한 개 이상의 callback URL이 필요합니다."));
  }
}
