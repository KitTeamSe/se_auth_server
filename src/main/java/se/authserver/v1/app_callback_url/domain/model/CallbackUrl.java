package se.authserver.v1.app_callback_url.domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import se.authserver.v1.common.domain.model.BaseEntity;

@Entity
public class CallbackUrl extends BaseEntity {

  @Id
  @GeneratedValue
  private Long callbackUrlId;

  // TODO 필요한 속성 추가

}
