package com.se.authserver.v1.account.application.service;

import com.se.authserver.v1.account.application.dto.AccountSignUpDto;
import com.se.authserver.v1.account.domain.model.Account;
import com.se.authserver.v1.account.domain.model.Authority;
import com.se.authserver.v1.account.domain.repository.AccountRepositoryProtocol;
import com.se.authserver.v1.common.domain.exception.UniqueValueAlreadyExistsException;
import java.util.UUID;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AccountSignUpService {

  private final AccountRepositoryProtocol accountRepository;
  private final PasswordEncoder passwordEncoder;

  public AccountSignUpService(
      AccountRepositoryProtocol accountRepository,
      PasswordEncoder passwordEncoder) {
    this.accountRepository = accountRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Transactional
  public Long signUp(AccountSignUpDto request) {

    validateIdString(request.getIdString());
    validateEmail(request.getEmail());
    validateAuthorizedEmail(request.getAuthorizedEmail());

    Account account = Account.builder()
        .email(request.getEmail())
        .password(passwordEncoder.encode(request.getPassword()))
        .identifier(createIdentifier())
        .name(request.getName())
        .phone(null)
        .birth(request.getBirth())
        .country(request.getCountry())
        .address(request.getAddress() == null ? null : request.getAddress())
        .studentId(request.getStudentId() == null ? null : request.getStudentId())
        .authorizedEmail(null)
        .authority(Authority.ROLE_USER)
        .build();

    accountRepository.save(account);
    return account.getAccountId();
  }

  private void validateIdString(String idString) {
    if (accountRepository.findByIdString(idString).isPresent())
      throw new UniqueValueAlreadyExistsException("동일한 아이디가 이미 존재합니다");
  }

  private void validateEmail(String email) {
    if (accountRepository.findByEmail(email).isPresent())
      throw new UniqueValueAlreadyExistsException("동일한 이메일이 이미 존재합니다");
  }

  private void validateAuthorizedEmail(String email) {
    if (email == null) return;
    if (accountRepository.findByAuthorizedEmail(email).isPresent())
      throw new UniqueValueAlreadyExistsException("동일한 인증이메일이 이미 존재합니다");
  }

  private String createIdentifier() {
    String identifier;
    do {
      identifier = UUID.randomUUID().toString().replace("-","");
    } while(hasDuplicatedIdentifier(identifier));

    return identifier;
  }

  private boolean hasDuplicatedIdentifier(String uuid) {
    return accountRepository.existsByIdentifier(uuid);
  }
}
