package com.example.infrastructure.database.springdata.model.jpa;

import java.time.Instant;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "PRICES")
public class PriceDto {

  @Id private String id;

  @Field("BRAND_ID")
  private Integer brandId;

  @Field("START_DATE")
  private Instant startDate;

  @Field("END_DATE")
  private Instant endDate;

  @Field("PRICE_LIST")
  private Integer priceList;

  @Field("PRODUCT_ID")
  private Integer productId;

  @Field("PRIORITY")
  private Integer priority;

  @Field("PRICE")
  private Float price;

  @Field("CURR")
  private String curr;
}
