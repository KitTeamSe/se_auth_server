package com.se.authserver.v1.common.application.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface SecurityContextService extends UserDetailsService {
  UserDetails loadUserByUsername(String username);
}
