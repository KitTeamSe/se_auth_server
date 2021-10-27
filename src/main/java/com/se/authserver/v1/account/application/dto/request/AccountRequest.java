package com.se.authserver.v1.account.application.dto.request;

import com.se.authserver.v1.common.application.dto.request.BaseRequest;

public class AccountRequest<T> extends BaseRequest<T> {

  public AccountRequest() {
  }

  public AccountRequest(T dto) {
    super(dto);
  }

}
