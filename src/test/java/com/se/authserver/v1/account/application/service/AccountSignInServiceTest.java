package com.se.authserver.v1.account.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.se.authserver.v1.account.application.dto.AccountSignInDto;
import com.se.authserver.v1.account.domain.model.Account;
import com.se.authserver.v1.account.domain.repository.AccountRepositoryProtocol;
import com.se.authserver.v1.common.domain.exception.NotFoundException;
import com.se.authserver.v1.common.domain.exception.UnauthenticatedException;
import com.se.authserver.v1.common.infra.security.provider.JwtTokenResolver;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class AccountSignInServiceTest {

  @Mock
  private AccountRepositoryProtocol accountRepositoryProtocol;
  @Mock
  private PasswordEncoder passwordEncoder;
  @Mock
  private JwtTokenResolver jwtTokenResolver;

  @InjectMocks
  private AccountSignInService accountSignInService;

  private static AccountSignInDto request = new AccountSignInDto("idString", "password");

  @Test
  public void 로그인_성공 () throws Exception{
    // given
    Long accountId = 12345678L;
    Account account = mock(Account.class);
    when(account.getPassword()).thenReturn(request.getPassword());
    when(account.getAccountId()).thenReturn(accountId);

    when(accountRepositoryProtocol.findByIdString(anyString())).thenReturn(Optional.ofNullable(account));
    when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
    when(jwtTokenResolver.createToken(anyString())).then(AdditionalAnswers.returnsFirstArg());
    // when
    String token = accountSignInService.signIn(request);
    assertEquals(accountId.toString(), token);
    // then
  }

  @Test
  public void 존재하지_않는_아이디() throws Exception{
    // given
    // when
    NotFoundException exception = assertThrows(NotFoundException.class, () -> accountSignInService.signIn(request));
    // then
    assertEquals("존재하지 않는 아이디", exception.getMessage());
  }

  @Test
  public void 비밀번호_오류 () throws Exception{
    // given
    when(accountRepositoryProtocol.findByIdString(anyString())).thenReturn(Optional.ofNullable(mock(Account.class)));
    // when
    UnauthenticatedException exception = assertThrows(UnauthenticatedException.class, () -> accountSignInService.signIn(request));
    // then
    assertEquals("비밀번호 오류", exception.getMessage());
  }
}