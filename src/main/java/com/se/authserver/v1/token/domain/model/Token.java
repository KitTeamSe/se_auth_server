package com.se.authserver.v1.token.domain.model;

import com.se.authserver.v1.common.domain.model.BaseEntity;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Token extends BaseEntity {

  @Id
  @GeneratedValue
  private Long tokenId;

  // TODO 필요한 속성 추가
}
