package com.se.authserver.v1.ResourceMetadataService;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

import com.se.authserver.v1.common.domain.exception.NotFoundException;
import com.se.authserver.v1.metadata.domain.model.Resource;
import com.se.authserver.v1.metadata.domain.model.ResourceMetadata;
import com.se.authserver.v1.metadata.domain.repository.MetadataRepositoryProtocol;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.se.authserver.v1.metadata.application.dto.request.ResourceMetadataUpdateRequest;
import com.se.authserver.v1.metadata.application.service.ResourceMetadataUpdateService;

@ExtendWith(MockitoExtension.class)
public class ResourceMetadataUpdateServiceTest {

  @Mock
  MetadataRepositoryProtocol metadataRepositoryProtocol;

  @InjectMocks
  ResourceMetadataUpdateService resourceMetadataUpdateService;

  @Test
  void 메타_데이터_수정_성공() {
    // given
    ResourceMetadataUpdateRequest request
        = new ResourceMetadataUpdateRequest(1L, "name2", Resource.ACCOUNT);
    ResourceMetadata resourceMetadata = new ResourceMetadata("name", Resource.ACCOUNT);
    given(metadataRepositoryProtocol.readById(request.getMetadataId())).willReturn(
        java.util.Optional.of(resourceMetadata));
    given(metadataRepositoryProtocol.save(resourceMetadata))
        .willReturn(new ResourceMetadata("name2", Resource.ACCOUNT));

    // when, then
    assertDoesNotThrow(() -> resourceMetadataUpdateService.update(request));
  }

  @Test
  void 존재하지_않는_메타_데이터() {
    // given
    ResourceMetadataUpdateRequest request
        = new ResourceMetadataUpdateRequest(1L, "name", Resource.ACCOUNT);

    given(metadataRepositoryProtocol.readById(request.getMetadataId()))
        .willThrow(new NotFoundException("존재하지 않는 메타 데이터입니다."));

    // when
    NotFoundException notFoundException
        = assertThrows(NotFoundException.class,
        () -> resourceMetadataUpdateService.update(request));

    // then
    assertThat(notFoundException.getMessage(), is("존재하지 않는 메타 데이터입니다."));
  }
}
