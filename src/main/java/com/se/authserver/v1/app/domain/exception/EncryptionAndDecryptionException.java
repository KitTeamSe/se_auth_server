package com.se.authserver.v1.app.domain.exception;

import com.se.authserver.v1.common.domain.exception.SeException;
import org.springframework.http.HttpStatus;

/**
 * 암복호화 시 예외가 발생한 경우.
 */
public class EncryptionAndDecryptionException extends SeException {

  public EncryptionAndDecryptionException(String message) {
    super(HttpStatus.INTERNAL_SERVER_ERROR, message);
  }

  public EncryptionAndDecryptionException(String message, Throwable cause) {
    super(HttpStatus.INTERNAL_SERVER_ERROR, message, cause);
  }

  public EncryptionAndDecryptionException(Throwable cause) {
    super(HttpStatus.INTERNAL_SERVER_ERROR, cause);
  }
}
