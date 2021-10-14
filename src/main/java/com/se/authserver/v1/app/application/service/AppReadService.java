package com.se.authserver.v1.app.application.service;

import com.se.authserver.v1.app.application.dto.AppReadDto;
import com.se.authserver.v1.app.application.dto.AppReadDto.CallbackUrlDto;
import com.se.authserver.v1.app.application.dto.AppReadDto.ResourceMetadataDto;
import com.se.authserver.v1.app.domain.exception.EncryptionAndDecryptionException;
import com.se.authserver.v1.app.domain.model.App;
import com.se.authserver.v1.app.domain.repository.AppRepositoryProtocol;
import com.se.authserver.v1.common.domain.exception.NotFoundException;
import com.se.authserver.v1.resource_metadata.domain.model.ResourceMetadata;
import com.se.authserver.v1.resource_metadata_app_mapping.domain.model.ResourceMetadataAppMapping;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AppReadService {

  @Value("${client-secret.info.algorithm}")
  private String ALGORITHM;

  @Value("${client-secret.info.key}")
  private String KEY;

  private final AppRepositoryProtocol appRepositoryProtocol;

  public AppReadService(
      AppRepositoryProtocol appRepositoryProtocol) {
    this.appRepositoryProtocol = appRepositoryProtocol;
  }

  @Transactional
  public AppReadDto.Response read(Long id) {
    App app = appRepositoryProtocol.findById(id)
        .orElseThrow(() -> new NotFoundException("존재하지 않는 애플리케이션입니다."));

    return new AppReadDto.Response(app.getAppId()
        , app.getName()
        , app.getClientId()
        , decryptClientSecret(app.getClientSecret())
        , app.getDevelopProgress()
        , getCallbackUrlsFromApp(app)
        , getResourceMetadataDtosFromApp(app));
  }

  private List<CallbackUrlDto> getCallbackUrlsFromApp(App app) {
    return app.getCallbackUrls()
        .stream()
        .map(c -> new CallbackUrlDto(c.getCallbackUrlId(), c.getUrl()))
        .collect(Collectors.toList());
  }

  private List<ResourceMetadataDto> getResourceMetadataDtosFromApp(App app) {
    List<ResourceMetadataDto> resourceMetadataDtos = new ArrayList<>();
    for (ResourceMetadataAppMapping rmam : app.getResourceMetadataAppMappings()) {
      ResourceMetadata resourceMetadata = rmam.getResourceMetadata();
      resourceMetadataDtos.add(
          new ResourceMetadataDto(
              resourceMetadata.getResourceMetadataId()
              , resourceMetadata.getResource()
              , resourceMetadata.getName()
              , rmam.getIsEssentialInfo()));
    }
    return resourceMetadataDtos;
  }

  private String decryptClientSecret(String encryptedSecret) {
    String iv = KEY.substring(0, 16); // 16byte

    try {
      Cipher cipher = Cipher.getInstance(ALGORITHM);
      SecretKeySpec keySpec = new SecretKeySpec(iv.getBytes(), "AES");
      IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes());
      cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamSpec);

      byte[] decodedBytes = Base64.getDecoder().decode(encryptedSecret);
      byte[] decrypted = cipher.doFinal(decodedBytes);
      return new String(decrypted, StandardCharsets.UTF_8);
    } catch (Exception e) {
      throw new EncryptionAndDecryptionException("Client Secret Key 처리 중 오류가 발생하였습니다.");
    }
  }
}
