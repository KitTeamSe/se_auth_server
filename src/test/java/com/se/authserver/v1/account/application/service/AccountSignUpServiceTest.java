package com.se.authserver.v1.account.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.se.authserver.v1.account.application.dto.AccountSignUpDto;
import com.se.authserver.v1.account.domain.model.Account;
import com.se.authserver.v1.account.domain.model.Country;
import com.se.authserver.v1.account.domain.repository.AccountRepositoryProtocol;
import com.se.authserver.v1.common.domain.exception.UniqueValueAlreadyExistsException;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class AccountSignUpServiceTest {

  @Mock
  private AccountRepositoryProtocol accountRepository;
  @Mock
  private PasswordEncoder passwordEncoder;

  @InjectMocks
  private AccountSignUpService accountSignUpService;

  @Test
  public void 회원가입_성공 () throws Exception{
    // given
    AccountSignUpDto request = new AccountSignUpDto(
        "user@email.com", "password", "이름", LocalDate.now(),
        Country.KOR, "주소", "20170000", "user@email.com"
    );
    // when
    // then
    assertDoesNotThrow(() -> accountSignUpService.signUp(request));
  }

  @Test
  public void 이메일_계정_중복 () throws Exception{
    // given
    AccountSignUpDto request = mock(AccountSignUpDto.class);
    when(request.getEmail()).thenReturn("duplicated@email.com");
    when(accountRepository.findByEmail(anyString())).thenReturn(Optional.ofNullable(mock(Account.class)));

    // when
    UniqueValueAlreadyExistsException exception =
        assertThrows(UniqueValueAlreadyExistsException.class, () -> accountSignUpService.signUp(request));
    // then
    assertEquals("동일한 이메일이 이미 존재합니다", exception.getMessage());
  }

  @Test
  public void 인증이메일_계정_중복 () throws Exception{
    // given
    AccountSignUpDto request = mock(AccountSignUpDto.class);
    when(request.getAuthorizedEmail()).thenReturn("duplicated@authemail.com");
    when(accountRepository.findByAuthorizedEmail(anyString())).thenReturn(Optional.ofNullable(mock(Account.class)));
    // when
    UniqueValueAlreadyExistsException exception =
        assertThrows(UniqueValueAlreadyExistsException.class, () -> accountSignUpService.signUp(request));
    // then
    assertEquals("동일한 인증이메일이 이미 존재합니다", exception.getMessage());
  }


}