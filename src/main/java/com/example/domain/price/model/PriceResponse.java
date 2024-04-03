package com.example.domain.price.model;

import java.time.Instant;
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

  private Instant startDate;

  private Instant endDate;

  private Integer priority;

  private Float price;

  private String curr;
}
