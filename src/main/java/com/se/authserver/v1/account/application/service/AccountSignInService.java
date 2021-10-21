package com.se.authserver.v1.account.application.service;

import com.se.authserver.v1.account.application.dto.AccountSignInDto;
import com.se.authserver.v1.account.domain.model.Account;
import com.se.authserver.v1.account.domain.repository.AccountRepositoryProtocol;
import com.se.authserver.v1.common.domain.exception.NotFoundException;
import com.se.authserver.v1.common.domain.exception.UnauthenticatedException;
import com.se.authserver.v1.common.infra.security.provider.JwtTokenResolver;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccountSignInService {

  private final AccountRepositoryProtocol accountRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtTokenResolver jwtTokenResolver;

  public AccountSignInService(
      AccountRepositoryProtocol accountRepository,
      PasswordEncoder passwordEncoder,
      JwtTokenResolver jwtTokenResolver) {
    this.accountRepository = accountRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtTokenResolver = jwtTokenResolver;
  }

  public String signIn(AccountSignInDto request) {
    Account account = accountRepository.findByIdString(request.getIdString())
        .orElseThrow(() -> new NotFoundException("존재하지 않는 아이디"));

    if (!passwordEncoder.matches(request.getPassword(), account.getPassword()))
      throw new UnauthenticatedException("비밀번호 오류");

    String token = jwtTokenResolver.createToken(account.getAccountId().toString());
    return token;
  }
}
