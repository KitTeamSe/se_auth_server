package com.se.authserver.v1.app.domain.repository;

import com.se.authserver.v1.app.domain.model.App;
import java.util.Optional;

public interface AppRepositoryProtocol {

  App save(App app);

  App findByClientId(String clientId);

  Optional<App> findById(Long id);

  void delete(App id);
}
