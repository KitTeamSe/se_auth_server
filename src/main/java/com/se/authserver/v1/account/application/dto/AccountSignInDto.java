package com.se.authserver.v1.account.application.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountSignInDto {
  private String idString;
  private String password;

  public AccountSignInDto(String idString, String password) {
    this.idString = idString;
    this.password = password;
  }
}
