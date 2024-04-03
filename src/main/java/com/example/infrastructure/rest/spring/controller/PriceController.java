package com.example.infrastructure.rest.spring.controller;

import com.example.application.port.out.price.PriceFinder;
import com.example.infrastructure.rest.spring.api.PriceApi;
import com.example.infrastructure.rest.spring.dto.PriceResponseWebDto;
import com.example.infrastructure.rest.spring.mapper.PriceWebMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
public class PriceController implements PriceApi {

  private final PriceFinder priceFinder;
  private final PriceWebMapper priceWebMapper;

  @Override
  public ResponseEntity<List<PriceResponseWebDto>> getPrice(
      Integer brandId, Integer productId, String dateApplication) {
    return ResponseEntity.ok(
        priceWebMapper.toDto(
            priceFinder.findByRequest(
                priceWebMapper.toDomain(brandId, productId, dateApplication))));
  }
}
