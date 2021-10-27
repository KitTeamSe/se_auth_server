package com.se.authserver.v1.AppService;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

import com.se.authserver.v1.app.application.dto.request.AppCreateRequest;
import com.se.authserver.v1.app.application.dto.request.AppCreateRequest.Request;
import com.se.authserver.v1.app.application.dto.request.AppCreateRequest.ResourceMetadataDto;
import com.se.authserver.v1.app.application.service.AppCreateService;
import com.se.authserver.v1.app.domain.model.App;
import com.se.authserver.v1.app.domain.model.DevelopProgress;
import com.se.authserver.v1.app.domain.repository.AppRepositoryProtocol;
import com.se.authserver.v1.common.domain.exception.NotFoundException;
import com.se.authserver.v1.common.domain.exception.PreconditionFailedException;
import com.se.authserver.v1.resource_metadata.domain.model.Resource;
import com.se.authserver.v1.resource_metadata.domain.model.ResourceMetadata;
import com.se.authserver.v1.resource_metadata.domain.repository.MetadataRepositoryProtocol;
import com.se.authserver.v1.resource_metadata_app_mapping.domain.model.IsEssentialInfo;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AppCreateServiceTest {

  @Mock
  MetadataRepositoryProtocol metadataRepositoryProtocol;

  @Mock
  AppRepositoryProtocol appRepositoryProtocol;

  @InjectMocks
  AppCreateService appCreateService;

  @BeforeEach
  void setUp() throws NoSuchFieldException, IllegalAccessException {
    Field algorithmField = appCreateService.getClass().getDeclaredField("ALGORITHM");
    algorithmField.setAccessible(true);
    algorithmField.set(appCreateService, "AES/CBC/PKCS5Padding");

    Field keyField = appCreateService.getClass().getDeclaredField("KEY");
    keyField.setAccessible(true);
    keyField.set(appCreateService, "keykeykeykeykeykeykey");
  }

  @Test
  void 애플리케이션_생성_성공() {
    // given
    List<String> callbackUrls = Arrays.asList("callback_url");
    List<ResourceMetadataDto> resourceMetadataDtos
        = Arrays.asList(new ResourceMetadataDto(1L, IsEssentialInfo.ESSENTIAL));
    AppCreateRequest.Request request
        = new Request("name", DevelopProgress.PROCEEDING, callbackUrls, resourceMetadataDtos);

    App app = new App("name", "clientId", "clientSecret", DevelopProgress.PROCEEDING);
    given(appRepositoryProtocol.save(Mockito.any(App.class))).willReturn(app);
    given(appRepositoryProtocol.findByClientId(Mockito.any(String.class))).willReturn(null);
    given(metadataRepositoryProtocol.readById(Mockito.any(Long.class))).willReturn(
        java.util.Optional.of(new ResourceMetadata("name", Resource.ACCOUNT)));

    // when, then
    assertDoesNotThrow(() -> appCreateService.create(request));
  }

  @Test
  void 존재하지_않는_리소스() {
    // given
    List<String> callbackUrls = Arrays.asList("callback_url");
    List<ResourceMetadataDto> resourceMetadataDtos
        = Arrays.asList(new ResourceMetadataDto(1L, IsEssentialInfo.ESSENTIAL));
    AppCreateRequest.Request request
        = new Request("name", DevelopProgress.PROCEEDING, callbackUrls, resourceMetadataDtos);

    App app = new App("name", "clientId", "clientSecret", DevelopProgress.PROCEEDING);
    given(appRepositoryProtocol.save(Mockito.any(App.class))).willReturn(app);
    given(appRepositoryProtocol.findByClientId(Mockito.any(String.class))).willReturn(null);
    given(metadataRepositoryProtocol.readById(Mockito.any(Long.class)))
        .willThrow(new NotFoundException("존재하지 않는 리소스입니다."));

    // when
    NotFoundException notFoundException = assertThrows(NotFoundException.class,
        () -> appCreateService.create(request));

    // then
    assertThat(notFoundException.getMessage(), is("존재하지 않는 리소스입니다."));
  }


  @Test
  void CALLBACK_URL_미설정으로_인한_데이터_등록_실패() {
    // given
    List<ResourceMetadataDto> resourceMetadataDtos
        = Arrays.asList(new ResourceMetadataDto(1L, IsEssentialInfo.ESSENTIAL));
    AppCreateRequest.Request request
        = new Request("name", DevelopProgress.PROCEEDING, new ArrayList<>(), resourceMetadataDtos);

    App app = new App("name", "clientId", "clientSecret", DevelopProgress.PROCEEDING);
    given(appRepositoryProtocol.save(Mockito.any(App.class))).willReturn(app);
    given(appRepositoryProtocol.findByClientId(Mockito.any(String.class))).willReturn(null);

    // when
    PreconditionFailedException preconditionFailedException
        = assertThrows(PreconditionFailedException.class, () -> appCreateService.create(request));

    // then
    assertThat(preconditionFailedException.getMessage(), is("최소 한 개 이상의 callback URL이 필요합니다."));
  }
}
