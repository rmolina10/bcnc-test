package com.example.application.usecase.price;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.example.domain.price.PriceQueries;
import com.example.domain.price.model.PriceRequest;
import com.example.domain.price.model.PriceResponse;
import com.example.tests.LoggingEventUtility;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PriceDtoFinderUseCaseTest {

  @InjectMocks private PriceFinderUseCase priceFinderUseCase;
  @Mock private PriceQueries priceQueries;

  private static final Integer BRAND_ID = 1;
  private static final Integer PRODUCT_ID = 100;
  private static final Instant DATE_APPLICATION =
      LocalDateTime.of(2024, 4, 1, 17, 15, 0, 0).toInstant(ZoneOffset.UTC);
  private static final Integer PRICE_LIST = 1;
  private static final Integer PRIORITY = 1;
  private static final Float PRICE = 148.98f;
  private static final String CURRENCY = "EURO";
  private static final Instant START_DATE =
      LocalDateTime.of(2024, 4, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC);
  private static final Instant END_DATE =
      LocalDateTime.of(2024, 4, 30, 23, 59, 59, 999).toInstant(ZoneOffset.UTC);

  private ListAppender<ILoggingEvent> loggingEventListAppender;

  @BeforeEach
  public void setup() {
    loggingEventListAppender =
        LoggingEventUtility.getListAppenderForClass(PriceFinderUseCase.class, Level.DEBUG);
  }

  @Test
  void findByRequest() {
    // Given
    PriceRequest priceRequest =
        PriceRequest.builder()
            .brandId(BRAND_ID)
            .productId(PRODUCT_ID)
            .dateApplication(DATE_APPLICATION)
            .build();
    List<PriceResponse> expectedPriceList =
        Collections.singletonList(
            PriceResponse.builder()
                .brandId(BRAND_ID)
                .productId(PRODUCT_ID)
                .priceList(PRICE_LIST)
                .startDate(START_DATE)
                .endDate(END_DATE)
                .priority(PRIORITY)
                .price(PRICE)
                .curr(CURRENCY)
                .build());
    when(priceQueries.findAllByRequest(priceRequest)).thenReturn(expectedPriceList);

    Level expectedLogLevel = Level.DEBUG;
    String expectedLog =
        String.format(
            "Searching prices with parameter 'brandId': %s, 'productId': %s, 'dateApplication': %s",
            BRAND_ID, PRODUCT_ID, DATE_APPLICATION);

    // When
    List<PriceResponse> priceResponseList = priceFinderUseCase.findByRequest(priceRequest);

    // Then
    assertEquals(expectedPriceList, priceResponseList);
    verify(priceQueries).findAllByRequest(priceRequest);

    // Logging
    assertEquals(1, loggingEventListAppender.list.size());
    assertEquals(expectedLogLevel, loggingEventListAppender.list.get(0).getLevel());
    assertEquals(expectedLog, loggingEventListAppender.list.get(0).getFormattedMessage());
  }
}
