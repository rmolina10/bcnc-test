package com.example.domain.price.model;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PriceResponse {

  private Integer brandId;

  private Integer productId;

  private Integer priceList;

  private LocalDateTime startDate;

  private LocalDateTime endDate;

  private Integer priority;

  private Float price;

  private String curr;
}
