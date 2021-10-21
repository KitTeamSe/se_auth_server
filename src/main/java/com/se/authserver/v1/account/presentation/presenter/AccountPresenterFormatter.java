package com.se.authserver.v1.account.presentation.presenter;

import com.se.authserver.v1.account.application.dto.AccountReadDto;
import com.se.authserver.v1.common.presentation.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class AccountPresenterFormatter implements AccountPresenter{

  @Override
  public Response<Long> signUp(Long accountId) {
    return new Response(HttpStatus.OK, "성공적으로 회원가입하였습니다", accountId);
  }

  @Override
  public Response<String> signIn(String token) {
    return new Response(HttpStatus.OK, "로그인 되었습니다", token);
  }

  @Override
  public Response<AccountReadDto> read(AccountReadDto accountReadDto) {
    return new Response(HttpStatus.OK, "회원 단일 조회 성공하였습니다", accountReadDto);
  }

}
