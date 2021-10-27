package com.se.authserver.v1.account.domain.repository;

import com.se.authserver.v1.account.domain.model.Account;
import com.se.authserver.v1.account.infra.repository.AccountJpaRepository;
import java.util.Optional;
import org.springframework.stereotype.Repository;


@Repository
public class AccountRepositoryProtocolImpl implements AccountRepositoryProtocol{

  private final AccountJpaRepository jpa;

  public AccountRepositoryProtocolImpl(
      AccountJpaRepository jpa) {
    this.jpa = jpa;
  }

  @Override
  public Account save(Account account) {
    return jpa.save(account);
  }

  @Override
  public Optional<Account> findById(Long accountId) {
    return jpa.findById(accountId);
  }

  @Override
  public Optional<Account> findByIdString(String idString) {
    return jpa.findByIdString(idString);
  }

  @Override
  public Optional<Account> findByEmail(String email) {
    return jpa.findByEmail(email);
  }

  @Override
  public Optional<Account> findByAuthorizedEmail(String email) {
    return jpa.findByAuthorizedEmail(email);
  }

  @Override
  public boolean existsByIdentifier(String uuid) {
    return jpa.existsByIdentifier(uuid);
  }

}
