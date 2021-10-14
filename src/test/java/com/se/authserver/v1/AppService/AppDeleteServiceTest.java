package com.se.authserver.v1.AppService;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import com.se.authserver.v1.app.application.service.AppDeleteService;
import com.se.authserver.v1.app.domain.model.App;
import com.se.authserver.v1.app.domain.model.DevelopProgress;
import com.se.authserver.v1.app.domain.repository.AppRepositoryProtocol;
import com.se.authserver.v1.app_callback_url.domain.model.CallbackUrl;
import com.se.authserver.v1.common.domain.exception.NotFoundException;
import com.se.authserver.v1.resource_metadata.domain.model.Resource;
import com.se.authserver.v1.resource_metadata.domain.model.ResourceMetadata;
import com.se.authserver.v1.resource_metadata_app_mapping.domain.model.IsEssentialInfo;
import com.se.authserver.v1.resource_metadata_app_mapping.domain.model.ResourceMetadataAppMapping;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AppDeleteServiceTest {

  @Mock
  AppRepositoryProtocol appRepositoryProtocol;

  @InjectMocks
  AppDeleteService appDeleteService;

  @Test
  void 애플리케이션_삭제_성공() {
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
    given(appRepositoryProtocol.findById(1L)).willReturn(java.util.Optional.of(app));

    // when, then
    assertDoesNotThrow(() -> appDeleteService.delete(id));
  }

  @Test
  void 존재하지_않는_애플리케이션() {
    // given
    Long id = 1L;

    // when
    NotFoundException notFoundException
        = assertThrows(NotFoundException.class, () -> appDeleteService.delete(id));

    // then
    assertThat(notFoundException.getMessage(), is("존재하지 않는 애플리케이션입니다."));
  }
}
