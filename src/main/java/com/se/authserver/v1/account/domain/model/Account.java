package com.se.authserver.v1.account.domain.model;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import com.se.authserver.v1.common.domain.model.BaseEntity;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Account extends BaseEntity {

  @Id
  @GeneratedValue
  private Long accountId;

  @Email
  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false, unique = true)
  private String identifier;  //uuid

  @Column
  private String phone;

  @Column(nullable = false)
  private LocalDateTime birth;

  @Size(min = 2, max = 20)
  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Country country;

  @Column
  private String address;

  @Column
  private String studentId;

  @Column(unique = true)
  private String authorizedEmail;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Authority authority;

  public Account(String email, String password, String identifier, String phone,
      LocalDateTime birth, String name, Country country, String address, String studentId,
      String authorizedEmail, Authority authority) {
    this.email = email;
    this.password = password;
    this.identifier = identifier;
    this.phone = phone;
    this.birth = birth;
    this.name = name;
    this.country = country;
    this.address = address;
    this.studentId = studentId;
    this.authorizedEmail = authorizedEmail;
    this.authority = authority;
  }
}
