package com.example.infrastructure.rest.spring.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import com.example.application.port.out.price.PriceFinder;
import com.example.domain.price.model.PriceRequest;
import com.example.domain.price.model.PriceResponse;
import com.example.infrastructure.rest.spring.dto.PriceResponseWebDto;
import com.example.infrastructure.rest.spring.mapper.PriceWebMapper;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class PriceDtoControllerTest {
  @InjectMocks private PriceController priceController;

  @Mock private PriceFinder priceFinder;
  @Mock private PriceWebMapper priceWebMapper;

  private static PriceRequest priceRequest;
  private static PriceResponse priceResponse;
  private static PriceResponseWebDto priceResponseWebDto;
  private static final Integer BRAND_ID = 1;
  private static final Integer PRODUCT_ID = 100;
  private static final LocalDateTime DATE_APPLICATION = LocalDateTime.of(2024, 4, 1, 17, 15, 0, 0);
  private static final Integer PRICE_LIST = 1;
  private static final Integer PRIORITY = 1;
  private static final Float PRICE = 148.98f;
  private static final String CURRENCY = "EURO";
  private static final LocalDateTime START_DATE = LocalDateTime.of(2024, 4, 1, 0, 0, 0, 0);
  private static final LocalDateTime END_DATE = LocalDateTime.of(2024, 4, 30, 23, 59, 59, 999);

  @BeforeAll
  static void setUp() {
    priceRequest =
        PriceRequest.builder()
            .brandId(BRAND_ID)
            .productId(PRODUCT_ID)
            .dateApplication(DATE_APPLICATION)
            .build();
    priceResponse =
        PriceResponse.builder()
            .brandId(BRAND_ID)
            .productId(PRODUCT_ID)
            .priceList(PRICE_LIST)
            .startDate(START_DATE)
            .endDate(END_DATE)
            .priority(PRIORITY)
            .price(PRICE)
            .curr(CURRENCY)
            .build();
    priceResponseWebDto =
        PriceResponseWebDto.builder()
            .brandId(BRAND_ID)
            .productId(PRODUCT_ID)
            .priceList(PRICE_LIST)
            .startDate(START_DATE)
            .endDate(END_DATE)
            .price(PRICE)
            .curr(CURRENCY)
            .build();
  }

  @Test
  void getPrice() {
    // GIVEN
    doReturn(priceRequest).when(priceWebMapper).toDomain(anyInt(), anyInt(), anyString());
    doReturn(priceResponse).when(priceFinder).findPriceByRequest(any(PriceRequest.class));
    doReturn(priceResponseWebDto).when(priceWebMapper).toDto(any(PriceResponse.class));

    // WHEN
    ResponseEntity<PriceResponseWebDto> result =
        priceController.getPrice(BRAND_ID, PRODUCT_ID, DATE_APPLICATION.toString());

    // THEN
    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertNotNull(result.getBody());
    assertEquals(priceResponseWebDto, result.getBody());
    verify(priceWebMapper).toDomain(BRAND_ID, PRODUCT_ID, DATE_APPLICATION.toString());
    verify(priceFinder).findPriceByRequest(priceRequest);
    verify(priceWebMapper).toDto(priceResponse);
  }
}
