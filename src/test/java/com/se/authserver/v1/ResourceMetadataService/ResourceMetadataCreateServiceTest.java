package com.se.authserver.v1.ResourceMetadataService;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.se.authserver.v1.common.domain.exception.UniqueValueAlreadyExistsException;
import com.se.authserver.v1.resource_metadata.application.dto.request.ResourceMetadataCreateRequest;
import com.se.authserver.v1.resource_metadata.application.service.ResourceMetadataCreateService;
import com.se.authserver.v1.resource_metadata.domain.model.Resource;
import com.se.authserver.v1.resource_metadata.domain.model.ResourceMetadata;
import com.se.authserver.v1.resource_metadata.domain.repository.MetadataRepositoryProtocol;

@ExtendWith(MockitoExtension.class)
public class ResourceMetadataCreateServiceTest {

  @Mock
  MetadataRepositoryProtocol metadataRepositoryProtocol;

  @InjectMocks
  ResourceMetadataCreateService resourceMetadataCreateService;

  @Test
  void 리소스_별_데이터_등록_성공() {
    // given
    ResourceMetadataCreateRequest request
        = new ResourceMetadataCreateRequest("name", Resource.ACCOUNT);
    given(metadataRepositoryProtocol.readOne(request.getName(), request.getResource()))
        .willReturn(null);
    given(metadataRepositoryProtocol.save(any(ResourceMetadata.class)))
        .willReturn(new ResourceMetadata(request.getName(), request.getResource()));
    // when, then
    assertDoesNotThrow(() -> resourceMetadataCreateService.create(request));
  }

  @Test
  void 리소스_내_중복된_데이터_등록_실패() {
    // given
    ResourceMetadataCreateRequest request
        = new ResourceMetadataCreateRequest("name", Resource.ACCOUNT);
    given(metadataRepositoryProtocol.readOne(request.getName(), request.getResource()))
        .willReturn(new ResourceMetadata("name", Resource.ACCOUNT));
    // when
    UniqueValueAlreadyExistsException uniqueValueAlreadyExistsException
        = assertThrows(UniqueValueAlreadyExistsException.class,
        () -> resourceMetadataCreateService.create(request));

    // then
    assertThat(uniqueValueAlreadyExistsException.getMessage(), is("이미 등록된 데이터입니다."));
  }
}
