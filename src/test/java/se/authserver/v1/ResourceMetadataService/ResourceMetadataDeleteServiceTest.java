package se.authserver.v1.ResourceMetadataService;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.authserver.v1.common.domain.exception.NotFoundException;
import se.authserver.v1.metadata.application.service.ResourceMetadataDeleteService;
import se.authserver.v1.metadata.domain.model.Resource;
import se.authserver.v1.metadata.domain.model.ResourceMetadata;
import se.authserver.v1.metadata.domain.repository.MetadataRepositoryProtocol;

@ExtendWith(MockitoExtension.class)
public class ResourceMetadataDeleteServiceTest {
  @Mock
  MetadataRepositoryProtocol metadataRepositoryProtocol;

  @InjectMocks
  ResourceMetadataDeleteService resourceMetadataDeleteService;

  @Test
  void 메타_데이터_삭제_성공() {
    // given
    Long id = 1L;
    ResourceMetadata resourceMetadata = new ResourceMetadata("name", Resource.ACCOUNT);
    given(metadataRepositoryProtocol.readById(id)).willReturn(
        java.util.Optional.of(resourceMetadata));
    willDoNothing().given(metadataRepositoryProtocol).delete(resourceMetadata);

    // when, then
    assertDoesNotThrow(() -> resourceMetadataDeleteService.delete(id));
  }

  @Test
  void 존재하지_않는_메타_데이터() {
    // given
    Long id = 1L;
    given(metadataRepositoryProtocol.readById(id)).willThrow(new NotFoundException("존재하지 않는 메타 데이터입니다."));

    // when
    NotFoundException notFoundException
        = assertThrows(NotFoundException.class, () -> resourceMetadataDeleteService.delete(id));

    // then
    assertThat(notFoundException.getMessage(), is("존재하지 않는 메타 데이터입니다."));
  }
}
