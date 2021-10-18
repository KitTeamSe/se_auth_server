package com.se.authserver.v1.common.infra.security.util;

import com.se.authserver.v1.common.application.service.SecurityContextService;
import java.util.Set;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SecurityContextUtil {

  public Long getCurrentAccountId() {
    return Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
  }

  public boolean hasAuthority(String auth) {
    Set<String> authorities = AuthorityUtils.authorityListToSet(SecurityContextHolder.getContext().getAuthentication()
        .getAuthorities());
    return authorities.contains(auth);
  }

  public boolean isOwner(Long accountId) {
    return getCurrentAccountId() == accountId;
  }

  public boolean isSignIn() {
    return SecurityContextHolder.getContext().getAuthentication() != null;
  }

}
