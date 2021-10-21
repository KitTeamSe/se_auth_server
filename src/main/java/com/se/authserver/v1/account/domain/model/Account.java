package com.se.authserver.v1.account.domain.model;

import java.time.LocalDate;
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
public class Account extends BaseEntity {

  @Id
  @GeneratedValue
  private Long accountId;

  @Column(nullable = false, unique = true)
  @Size(min = 4, max = 20)
  private String idString;    //로그인 아이디

  @Column(nullable = false)
  private String password;

  @Column(nullable = false, unique = true)
  private String identifier;  //uuid

  @Size(min = 2, max = 20)
  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private LocalDate birth;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Country country;

  @Column
  private String phone;

  @Column
  private String address;

  @Column
  private String studentId;

  @Column(nullable = false, unique = true)
  @Email
  private String email;

  @Column(unique = true)
  @Email
  private String authorizedEmail;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Authority authority;

  @Builder
  public Account(String idString, String password, String identifier, String name,
      LocalDate birth, Country country, String phone, String address, String studentId,
      String email, String authorizedEmail,
      Authority authority) {
    this.idString = idString;
    this.password = password;
    this.identifier = identifier;
    this.name = name;
    this.birth = birth;
    this.country = country;
    this.phone = phone;
    this.address = address;
    this.studentId = studentId;
    this.email = email;
    this.authorizedEmail = authorizedEmail;
    this.authority = authority;
  }
}
