package com.se.authserver.v1.app.domain.repository;

import com.se.authserver.v1.app.domain.model.App;

public interface AppRepositoryProtocol {

  App save(App app);

  App findByClientId(String clientId);
}
