package com.se.authserver.v1.app_callback_url.infra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.se.authserver.v1.app_callback_url.domain.model.CallbackUrl;

public interface CallbackUrlJpaRepository extends JpaRepository<CallbackUrl, Long> {

}
