package com.se.authserver.v1.common.infra.security.util;

import com.se.authserver.v1.common.domain.exception.UnauthenticatedException;
import java.util.Set;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SecurityContextUtil {

  public static Long getCurrentAccountId() {
    if (SecurityContextHolder.getContext().getAuthentication() == null)
      throw new UnauthenticatedException("인증 정보가 존재하지 않습니다");
    return Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
  }

  public static boolean hasAuthority(String auth) {
    Set<String> authorities = AuthorityUtils.authorityListToSet(SecurityContextHolder.getContext().getAuthentication()
        .getAuthorities());
    return authorities.contains(auth);
  }

  public static boolean isOwner(Long accountId) {
    return getCurrentAccountId() == accountId;
  }

  public static boolean isSignIn() {
    return SecurityContextHolder.getContext().getAuthentication() != null;
  }

}
