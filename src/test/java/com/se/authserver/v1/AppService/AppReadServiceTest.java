package com.se.authserver.v1.AppService;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

import com.se.authserver.v1.app.application.dto.AppReadDto;
import com.se.authserver.v1.app.application.service.AppReadService;
import com.se.authserver.v1.app.domain.exception.EncryptionAndDecryptionException;
import com.se.authserver.v1.app.domain.model.App;
import com.se.authserver.v1.app.domain.model.DevelopProgress;
import com.se.authserver.v1.app.domain.repository.AppRepositoryProtocol;
import com.se.authserver.v1.app_callback_url.domain.model.CallbackUrl;
import com.se.authserver.v1.common.domain.exception.NotFoundException;
import com.se.authserver.v1.resource_metadata.domain.model.Resource;
import com.se.authserver.v1.resource_metadata.domain.model.ResourceMetadata;
import com.se.authserver.v1.resource_metadata_app_mapping.domain.model.IsEssentialInfo;
import com.se.authserver.v1.resource_metadata_app_mapping.domain.model.ResourceMetadataAppMapping;
import java.lang.reflect.Field;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AppReadServiceTest {

  @Mock
  AppRepositoryProtocol appRepositoryProtocol;

  @InjectMocks
  AppReadService appReadService;

  @BeforeEach
  void setUp() throws NoSuchFieldException, IllegalAccessException {
    Field algorithmField = appReadService.getClass().getDeclaredField("ALGORITHM");
    algorithmField.setAccessible(true);
    algorithmField.set(appReadService, "AES/CBC/PKCS5Padding");

    Field keyField = appReadService.getClass().getDeclaredField("KEY");
    keyField.setAccessible(true);
    keyField.set(appReadService, "CLIENTSECRETKEYINSEAUTHSERVER7540");
  }

  @Test
  void 애플리케이션_조회_성공() {
    // given
    Long id = 1L;
    App app = new App("app", "client_id", "Vf2VbwmOq/FX+4cNxsM/zgCLlV+LC5KEBRAYjvhUQ5jGelfFZXad0JcWvhGR2JXe", DevelopProgress.FINISHED);
    app.setCallbackUrls(Arrays.asList(new CallbackUrl(1L, "callback_url")));
    app.setResourceMetadataAppMappings(
        Arrays.asList(
            new ResourceMetadataAppMapping(
                new ResourceMetadata("resource_metadata_app_mapping"
                    , Resource.ACCOUNT)
                , id
                , IsEssentialInfo.ESSENTIAL)));
    given(appRepositoryProtocol.findById(id)).willReturn(java.util.Optional.of(app));
    // when
    AppReadDto.Response response = appReadService.read(id);

    // then
    assertAll(
        () -> assertThat(response.getAppName(), is("app"))
        , () -> assertThat(response.getClientId(), is("client_id"))
        , () -> assertThat(response.getClientSecret(), is("700e88d0-c2ac-4a86-9e0b-2a5e2ac9304e"))
        , () -> assertThat(response.getDevelopProgress(), is(DevelopProgress.FINISHED))
        , () -> assertThat(response.getCallbackUrls().get(0).getUrl(), is("callback_url"))
        , () -> assertThat(response.getResourceMetadataDtos().get(0).getResourceMetadataName(),
            is("resource_metadata_app_mapping")));
  }

  @Test
  void 존재하지_않는_애플리케이션() {
    // given
    Long id = 1L;

    // when
    NotFoundException notFoundException
        = assertThrows(NotFoundException.class, () -> appReadService.read(id));

    // then
    assertThat(notFoundException.getMessage(), is("존재하지 않는 애플리케이션입니다."));
  }

  @Test
  void 복호화_중_문제_발생() {
    // given
    Long id = 1L;
    App app = new App("app", "client_id", "client_secret", DevelopProgress.FINISHED);
       app.setCallbackUrls(Arrays.asList(new CallbackUrl(1L, "callback_url")));
       app.setResourceMetadataAppMappings(
           Arrays.asList(
               new ResourceMetadataAppMapping(
                   new ResourceMetadata("resource_metadata_app_mapping"
                       , Resource.ACCOUNT)
                   , id
                   , IsEssentialInfo.ESSENTIAL)));
       given(appRepositoryProtocol.findById(id)).willReturn(java.util.Optional.of(app));

    // when
    EncryptionAndDecryptionException encryptionAndDecryptionException
        = assertThrows(EncryptionAndDecryptionException.class, () -> appReadService.read(id));

    // then
    assertThat(encryptionAndDecryptionException.getMessage(), is("Client Secret Key 처리 중 오류가 발생하였습니다."));
  }
}
