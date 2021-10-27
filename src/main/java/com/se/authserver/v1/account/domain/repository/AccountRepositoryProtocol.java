package com.se.authserver.v1.account.domain.repository;

import com.se.authserver.v1.account.domain.model.Account;
import java.util.Optional;

public interface AccountRepositoryProtocol {

  Account save(Account account);

  Optional<Account> findById(Long accountId);
  Optional<Account> findByIdString(String idString);
  Optional<Account> findByEmail(String email);
  Optional<Account> findByAuthorizedEmail(String email);

  boolean existsByIdentifier(String uuid);

}
