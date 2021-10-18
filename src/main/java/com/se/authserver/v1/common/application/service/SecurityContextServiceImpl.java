package com.se.authserver.v1.common.application.service;

import com.se.authserver.v1.account.domain.model.Account;
import com.se.authserver.v1.account.infra.repository.AccountJpaRepository;
import com.se.authserver.v1.common.domain.exception.NotFoundException;
import java.util.Arrays;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityContextServiceImpl implements SecurityContextService{

  private final AccountJpaRepository accountJpaRepository;

  public SecurityContextServiceImpl(
      AccountJpaRepository accountJpaRepository) {
    this.accountJpaRepository = accountJpaRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Account account = accountJpaRepository.findById(Long.parseLong(username))
        .orElseThrow(() -> new NotFoundException("일치하는 username이 존재하지 않습니다."));
    List<GrantedAuthority> grantedAuthorities = Arrays.asList(new SimpleGrantedAuthority(account.getAuthority().toString()));
    return new User(String.valueOf(account.getAccountId()), account.getPassword(), grantedAuthorities);
  }

}
