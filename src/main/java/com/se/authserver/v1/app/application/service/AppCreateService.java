package com.se.authserver.v1.app.application.service;

import com.se.authserver.v1.app.application.dto.request.AppCreateRequest;
import com.se.authserver.v1.app.domain.exception.EncryptionAndDecryptionException;
import com.se.authserver.v1.app.domain.model.App;
import com.se.authserver.v1.app.domain.repository.AppRepositoryProtocol;
import com.se.authserver.v1.app_callback_url.domain.model.CallbackUrl;
import com.se.authserver.v1.common.domain.exception.NotFoundException;
import com.se.authserver.v1.metadata.domain.repository.MetadataRepositoryProtocol;
import com.se.authserver.v1.resource_metadata_app_mapping.domain.model.ResourceMetadataAppMapping;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AppCreateService {

  @Value("${client-secret.info.algorithm}")
  private String ALGORITHM;

  @Value("${client-secret.info.key}")
  private String KEY;

  private final MetadataRepositoryProtocol metadataRepositoryProtocol;
  private final AppRepositoryProtocol appRepositoryProtocol;

  public AppCreateService(
      MetadataRepositoryProtocol metadataRepositoryProtocol,
      AppRepositoryProtocol appRepositoryProtocol) {
    this.metadataRepositoryProtocol = metadataRepositoryProtocol;
    this.appRepositoryProtocol = appRepositoryProtocol;
  }

  public Long create(AppCreateRequest.Request request) {
    App app = appRepositoryProtocol.save(
        new App(request.getName()
            , getClientId()
            , getClientSecret()
            , request.getDevelopProgress()));

    List<CallbackUrl> callbackUrls = getCallbackUrlsFromRequest(request, app.getAppId());
    List<ResourceMetadataAppMapping> resourceMetadataAppMappings
        = getResourceMetadataAppMappingFromRequest(request, app.getAppId());

    app.setCallbackUrls(callbackUrls);
    app.setResourceMetadataAppMappings(resourceMetadataAppMappings);
    appRepositoryProtocol.save(app);

    return app.getAppId();
  }

  private List<CallbackUrl> getCallbackUrlsFromRequest
      (AppCreateRequest.Request request, Long appId) {
    return request
        .getCallbackUrls()
        .stream()
        .map(u -> new CallbackUrl(appId, u)).collect(Collectors.toList());
  }

  private List<ResourceMetadataAppMapping> getResourceMetadataAppMappingFromRequest
      (AppCreateRequest.Request request, Long appId) {
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

  private String getClientId() {
    String clientId;
    App app;

    do {
      clientId = UUID.randomUUID().toString();
      app = appRepositoryProtocol.findByClientId(clientId);
    } while (app != null || clientId == null);

    return clientId;
  }

  private String getClientSecret() {
    return encryptClientSecret(UUID.randomUUID().toString());
  }

  private String encryptClientSecret(String clientSecret) {
    String iv
        = KEY.substring(0, 16); // 16byte

    try {
      Cipher cipher = Cipher.getInstance(ALGORITHM);
      SecretKeySpec keySpec = new SecretKeySpec(iv.getBytes(), "AES");
      IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes());
      cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);

      byte[] encrypted = cipher.doFinal(clientSecret.getBytes(StandardCharsets.UTF_8));
      return Base64.getEncoder().encodeToString(encrypted);
    } catch (Exception e) {
      throw new EncryptionAndDecryptionException("Client Secret Key 처리 중 오류가 발생하였습니다.");
    }
  }
}
