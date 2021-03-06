package com.se.authserver.v1.common.application.dto.request;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class PaginationRequest<T> extends BaseRequest<T> {
  @ApiModelProperty(example = "0", notes = "페이지, 0이상부터 가능")
  @Min(value = 0)
  private int page;
  @ApiModelProperty(example = "50", notes = "페이지의 사이즈, 10 이상 50 이하만 가능")
  @Min(value = 10)
  private int size;
  @ApiModelProperty(example = "ASC", notes = "정렬 방향")
  private Sort.Direction direction;
  @ApiModelProperty(example = "createdAt", notes = "정렬 기준(정렬할 엔티티의 속성명)")
  private String orderBy;

  public PaginationRequest(T dto, int page, int size,
      Direction direction, String orderBy) {
    super(dto);
    this.page = page;
    this.size = size;
    this.direction = direction;
    this.orderBy = orderBy;
  }

  public PageRequest of(){
    return PageRequest.of(this.page, this.size, this.direction, this.orderBy);
  }
}