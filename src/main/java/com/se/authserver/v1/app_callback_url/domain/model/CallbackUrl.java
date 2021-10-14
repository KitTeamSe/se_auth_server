package com.se.authserver.v1.app_callback_url.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class CallbackUrl {

  public CallbackUrl(Long appId, String url) {
    this.appId = appId;
    this.url = url;
  }

  @Id
  @Column(name = "callback_url_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long callbackUrlId;

  @Column(name = "app_id", nullable = false)
  private Long appId;

  @Column(nullable = false)
  private String url;
}

