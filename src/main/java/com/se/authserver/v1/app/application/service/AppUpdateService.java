package com.se.authserver.v1.app.application.service;

import com.se.authserver.v1.app.application.dto.request.AppUpdateRequest;
import com.se.authserver.v1.app.domain.model.App;
import com.se.authserver.v1.app.domain.repository.AppRepositoryProtocol;
import com.se.authserver.v1.app_callback_url.domain.model.CallbackUrl;
import com.se.authserver.v1.common.domain.exception.NotFoundException;
import com.se.authserver.v1.common.domain.exception.PreconditionFailedException;
import com.se.authserver.v1.resource_metadata.domain.repository.MetadataRepositoryProtocol;
import com.se.authserver.v1.resource_metadata_app_mapping.domain.model.ResourceMetadataAppMapping;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AppUpdateService {

  private final AppRepositoryProtocol appRepositoryProtocol;
  private final MetadataRepositoryProtocol metadataRepositoryProtocol;

  public AppUpdateService(
      AppRepositoryProtocol appRepositoryProtocol,
      MetadataRepositoryProtocol metadataRepositoryProtocol) {
    this.appRepositoryProtocol = appRepositoryProtocol;
    this.metadataRepositoryProtocol = metadataRepositoryProtocol;
  }

  public Long update(AppUpdateRequest.Request request) {
    App app = appRepositoryProtocol.findById(request.getId())
        .orElseThrow(() -> new NotFoundException("존재하지 않는 애플리케이션입니다."));
    app.update(request.getName(), request.getDevelopProgress());
    app = appRepositoryProtocol.save(app);

    app.setCallbackUrls(getCallbackUrlsFromRequest(request, app.getAppId()));
    app.setResourceMetadataAppMappings(getResourceMetadataAppMappingFromRequest(request, app.getAppId()));

    return app.getAppId();
  }

  private List<CallbackUrl> getCallbackUrlsFromRequest
      (AppUpdateRequest.Request request, Long appId) {
    if (request.getCallbackUrls() == null || request.getCallbackUrls().size() == 0) {
     throw new PreconditionFailedException("최소 한 개 이상의 callback URL이 필요합니다.");
    }

    return request
        .getCallbackUrls()
        .stream()
        .map(u -> new CallbackUrl(appId, u)).collect(Collectors.toList());
  }

  private List<ResourceMetadataAppMapping> getResourceMetadataAppMappingFromRequest
      (AppUpdateRequest.Request request, Long appId) {
    return request.getResourceMetadataDtos()
        .stream()
        .map(dto -> new ResourceMetadataAppMapping(
            metadataRepositoryProtocol
                .readById(dto.getResourceMetadataId())
                .orElseThrow(() -> new NotFoundException("존재하지 않는 메타 데이터입니다."))
            , appId
            , dto.getIsEssentialInfo()
        )).collect(Collectors.toList());
  }
}
