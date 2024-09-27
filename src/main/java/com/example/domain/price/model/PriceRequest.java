package com.example.domain.price.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceRequest implements Serializable {

  private Integer brandId;

  private Integer productId;

  private LocalDateTime dateApplication;
}
