package com.se.authserver.v1.account.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.se.authserver.v1.account.domain.model.Country;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@ApiModel("회원 가입 요청")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountSignUpDto {

  @ApiModelProperty(example = "user@email.com", notes = "이메일(로그인아이디)")
  @NotNull
  @Email
  private String email;

  @ApiModelProperty(example = "password", notes = "비밀번호")
  @NotNull
  private String password;

  @ApiModelProperty(example = "이지우", notes = "이름(성명)")
  @NotNull
  private String name;

  @ApiModelProperty(example = "2021-01-01", notes = "생년월일")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @NotNull
  private LocalDate birth;

  @ApiModelProperty(example = "KOR", notes = "국적")
  @NotNull
  private Country country;

  @ApiModelProperty(example = "경북 구미시", notes = "주소")
  private String address;

  @ApiModelProperty(example = "20170000", notes = "학번")
  private String studentId;

  @ApiModelProperty(example = "user@email.com", notes = "인증된 이메일")
  @Email
  private String authorizedEmail;

  public AccountSignUpDto(String email, String password, String name, LocalDate birth,
      Country country, String address, String studentId, String authorizedEmail) {
    this.email = email;
    this.password = password;
    this.name = name;
    this.birth = birth;
    this.country = country;
    this.address = address;
    this.studentId = studentId;
    this.authorizedEmail = authorizedEmail;
  }

  //  @ApiModelProperty(example = "01022223333", notes = "전화번호(당장은 사용 예정이 없는 컬럼입니다)")
  //  private String phone;
}
