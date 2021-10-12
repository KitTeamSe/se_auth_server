package com.se.authserver.v1.ResourceMetadataService;

import static org.mockito.BDDMockito.given;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

import org.mockito.junit.jupiter.MockitoExtension;
import com.se.authserver.v1.metadata.application.dto.ResourceMetadataReadDto;
import com.se.authserver.v1.metadata.application.service.ResourceMetadataReadService;
import com.se.authserver.v1.metadata.domain.model.Resource;
import com.se.authserver.v1.metadata.domain.model.ResourceMetadata;
import com.se.authserver.v1.metadata.domain.repository.MetadataRepositoryProtocol;

@ExtendWith(MockitoExtension.class)
public class ResourceMetadataReadServiceTest {

  @Mock
  MetadataRepositoryProtocol metadataRepositoryProtocol;

  @InjectMocks
  ResourceMetadataReadService resourceMetadataReadService;

  @Test
  void 리소스_별_데이터_목록_조회() {
    // given
    List<ResourceMetadata> metadataList = Arrays.asList(
        new ResourceMetadata("name1", Resource.ACCOUNT)
    );
    given(metadataRepositoryProtocol.readAllByResource(Resource.ACCOUNT)).willReturn(metadataList);

    // when
    List<ResourceMetadataReadDto> result
        = resourceMetadataReadService.readAllByResource(Resource.ACCOUNT);

    // then
    assertThat(result.size(), is(1));
    assertThat(result.get(0).getResource(), is(Resource.ACCOUNT));
  }
}
