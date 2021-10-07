package se.authserver.v1.account.domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import se.authserver.v1.common.domain.model.BaseEntity;

@Entity
public class Account extends BaseEntity {

  @Id
  @GeneratedValue
  private Long accountId;

  // TODO 필요한 속성 추가

}
