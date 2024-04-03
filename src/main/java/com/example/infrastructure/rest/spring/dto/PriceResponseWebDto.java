package com.example.infrastructure.rest.spring.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import lombok.*;

@Generated
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PriceResponseWebDto {

  @Schema(description = "Brand identification", example = "1")
  private Integer brandId;

  @Schema(description = "Product identification", example = "1000")
  private Integer productId;

  @Schema(description = "Price list", example = "1")
  private Integer priceList;

  @Schema(description = "Start Date", example = "2023-12-01T00:00:00.000")
  private Instant startDate;

  @Schema(description = "End Date", example = "2023-12-31T23:37:08.875")
  private Instant endDate;

  @Schema(description = "Price", example = "100.45")
  private Float price;

  @Schema(description = "Currency", example = "EUR")
  private String curr;
}
