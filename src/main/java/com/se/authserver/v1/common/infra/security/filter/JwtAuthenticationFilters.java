package com.se.authserver.v1.common.infra.security.filter;

import com.se.authserver.v1.common.domain.exception.InvalidTokenException;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import com.se.authserver.v1.common.infra.security.provider.JwtTokenResolver;

public class JwtAuthenticationFilters extends GenericFilterBean {

  private final JwtTokenResolver jwtTokenResolver;

  @Autowired
  public JwtAuthenticationFilters(JwtTokenResolver jwtTokenResolver) {
    this.jwtTokenResolver = jwtTokenResolver;
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    String token = jwtTokenResolver.resolveToken((HttpServletRequest) request);
    Authentication auth = null;
    if (jwtTokenResolver.validateToken(token)) {
      auth = jwtTokenResolver.getAuthentication(token);
    }
    //else throw new InvalidTokenException("유효하지 않은 토큰입니다");


//    if (token != null && jwtTokenResolver.validateToken(token)) {
//      auth = jwtTokenResolver.getAuthentication(token);
//    } else {
//      auth = jwtTokenResolver.getDefaultAuthentication();
//    }

    SecurityContextHolder.getContext().setAuthentication(auth);
    chain.doFilter(request, response);
  }
}
