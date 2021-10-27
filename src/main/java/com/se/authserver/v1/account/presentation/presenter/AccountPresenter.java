package com.se.authserver.v1.account.presentation.presenter;

//import com.se.authserver.v1.account.application.dto.AccountReadDto;
import com.se.authserver.v1.common.presentation.response.Response;

public interface AccountPresenter {

  Response<Long> signUp(Long accountId);
  Response<String> signIn(String token);
  //Response<AccountReadDto> read(AccountReadDto accountReadDto);

}
