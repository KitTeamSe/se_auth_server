package com.se.authserver.v1.account.infra.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.se.authserver.v1.account.domain.model.Account;

public interface AccountJpaRepository extends JpaRepository<Account, Long> {

  Optional<Account> findByIdString(String idString);
  Optional<Account> findByEmail(String email);
  Optional<Account> findByAuthorizedEmail(String email);

  boolean existsByIdentifier(String uuid);

}
