package com.se.authserver.v1.app.domain.model;

import com.se.authserver.v1.common.domain.model.BaseEntity;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class App extends BaseEntity {

  @Id
  @GeneratedValue
  private Long appId;

  // TODO 필요한 속성 추가
}
