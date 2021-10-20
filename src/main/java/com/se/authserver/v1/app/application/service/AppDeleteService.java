package com.se.authserver.v1.app.application.service;

import com.se.authserver.v1.app.domain.model.App;
import com.se.authserver.v1.app.domain.repository.AppRepositoryProtocol;
import com.se.authserver.v1.common.domain.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AppDeleteService {
  private final AppRepositoryProtocol appRepositoryProtocol;

  public AppDeleteService(
      AppRepositoryProtocol appRepositoryProtocol) {
    this.appRepositoryProtocol = appRepositoryProtocol;
  }

  public void delete(Long id) {
    App app = appRepositoryProtocol.findById(id)
        .orElseThrow(() -> new NotFoundException("존재하지 않는 애플리케이션입니다."));
    appRepositoryProtocol.delete(app);
  }
}
