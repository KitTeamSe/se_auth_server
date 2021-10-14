package com.se.authserver.v1.account.domain.model;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import com.se.authserver.v1.common.domain.model.BaseEntity;
import javax.validation.constraints.Email;

@Entity
public class Account extends BaseEntity {

  @Id
  @GeneratedValue
  private Long accountId;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false, unique = true)
  private String identifier;  //uuid

  @Column
  private String phone;

  @Email
  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false)
  private LocalDateTime birth;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private Country country;

  @Column
  private String address;

  @Column
  private String studentId;

  @Column(unique = true)
  private String authorizedEmail;

}
