package com.se.authserver.v1.app.domain.repository;

import com.se.authserver.v1.app.domain.model.App;
import com.se.authserver.v1.app.infra.repository.AppJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class AppRepository implements AppRepositoryProtocol{
  private final AppJpaRepository jpa;

  public AppRepository(AppJpaRepository jpa) {
    this.jpa = jpa;
  }

  @Override
  public App save(App app) {
    return jpa.save(app);
  }

  @Override
  public App findByClientId(String clientId) {
    return jpa.findByClientId(clientId);
  }
}
