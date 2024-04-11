package com.example.infrastructure.database.springdata.repository.price;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.example.domain.exception.ErrorsEnum;
import com.example.domain.exception.ProductRepositoryException;
import com.example.domain.price.model.PriceRequest;
import com.example.domain.price.model.PriceResponse;
import com.example.infrastructure.database.springdata.mapper.PriceMapper;
import com.example.infrastructure.database.springdata.model.jpa.PriceEntity;
import com.example.tests.LoggingEventUtility;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SpringDataPriceQueriesRepositoryTest {

  @InjectMocks private SpringDataPriceQueriesRepository springDataPriceQueriesRepository;
  @Mock private PriceMapper mapper;
  @Mock private JpaPriceRepository jpaPriceRepository;

  private static final Long ID = 1L;
  private static final Integer BRAND_ID = 1;
  private static final Integer PRODUCT_ID = 100;
  private static final LocalDateTime DATE_APPLICATION = LocalDateTime.of(2024, 4, 1, 17, 15, 0, 0);
  private static final Integer PRICE_LIST = 1;
  private static final Integer PRIORITY = 1;
  private static final Float PRICE = 148.98f;
  private static final String CURRENCY = "EURO";
  private static final LocalDateTime START_DATE = LocalDateTime.of(2024, 4, 1, 0, 0, 0, 0);
  private static final LocalDateTime END_DATE = LocalDateTime.of(2024, 4, 30, 23, 59, 59, 999);
  private static final String ERROR_MESSAGE = "some error message";

  private ListAppender<ILoggingEvent> loggingEventListAppender;

  @BeforeEach
  public void setup() {
    loggingEventListAppender =
        LoggingEventUtility.getListAppenderForClass(
            SpringDataPriceQueriesRepository.class, Level.DEBUG);
  }

  @Test
  void findPriceByRequest() {
    // Given
    PriceRequest priceRequest =
        PriceRequest.builder()
            .brandId(BRAND_ID)
            .productId(PRODUCT_ID)
            .dateApplication(DATE_APPLICATION)
            .build();
    PriceEntity priceEntity = getPriceEntity();
    when(jpaPriceRepository.findPricesByBrandIdAndProductIdAndDateApplication(
            priceRequest.getBrandId(),
            priceRequest.getProductId(),
            priceRequest.getDateApplication()))
        .thenReturn(Optional.of(priceEntity));

    Optional<PriceResponse> expectedPriceResponse = getPriceResponseFromPriceEntity(priceEntity);
    when(mapper.toDomain(priceEntity)).thenReturn(expectedPriceResponse.get());

    Level expectedLogLevel = Level.DEBUG;
    String expectedLog = "Getting a price by request";

    // When
    Optional<PriceResponse> priceResponse =
        springDataPriceQueriesRepository.findPriceByRequest(priceRequest);

    // Then
    assertEquals(expectedPriceResponse, priceResponse);

    verify(jpaPriceRepository)
        .findPricesByBrandIdAndProductIdAndDateApplication(
            priceRequest.getBrandId(),
            priceRequest.getProductId(),
            priceRequest.getDateApplication());
    verify(mapper).toDomain(priceEntity);

    // Logging
    assertEquals(1, loggingEventListAppender.list.size());
    assertEquals(expectedLogLevel, loggingEventListAppender.list.get(0).getLevel());
    assertEquals(expectedLog, loggingEventListAppender.list.get(0).getFormattedMessage());
  }

  @Test
  void findPriceByRequest_emptyResponse() {
    // Given
    PriceRequest priceRequest =
        PriceRequest.builder()
            .brandId(BRAND_ID)
            .productId(PRODUCT_ID)
            .dateApplication(DATE_APPLICATION)
            .build();
    when(jpaPriceRepository.findPricesByBrandIdAndProductIdAndDateApplication(
            priceRequest.getBrandId(),
            priceRequest.getProductId(),
            priceRequest.getDateApplication()))
        .thenReturn(Optional.empty());

    Level expectedLogLevel = Level.DEBUG;
    String expectedLog = "Getting a price by request";

    // When
    Optional<PriceResponse> priceResponse =
        springDataPriceQueriesRepository.findPriceByRequest(priceRequest);

    // Then
    assertEquals(Optional.empty(), priceResponse);

    verify(jpaPriceRepository)
        .findPricesByBrandIdAndProductIdAndDateApplication(
            priceRequest.getBrandId(),
            priceRequest.getProductId(),
            priceRequest.getDateApplication());
    verifyNoInteractions(mapper);

    // Logging
    assertEquals(1, loggingEventListAppender.list.size());
    assertEquals(expectedLogLevel, loggingEventListAppender.list.get(0).getLevel());
    assertEquals(expectedLog, loggingEventListAppender.list.get(0).getFormattedMessage());
  }

  @Test
  void findPriceByRequest_throwsProductRepositoryException() {
    // Given
    Exception runtimeException = new RuntimeException(ERROR_MESSAGE);
    PriceRequest priceRequest =
        PriceRequest.builder()
            .brandId(BRAND_ID)
            .productId(PRODUCT_ID)
            .dateApplication(DATE_APPLICATION)
            .build();
    doThrow(runtimeException)
        .when(jpaPriceRepository)
        .findPricesByBrandIdAndProductIdAndDateApplication(
            priceRequest.getBrandId(),
            priceRequest.getProductId(),
            priceRequest.getDateApplication());

    Level expectedLogLevel = Level.DEBUG;
    String expectedLog = "Getting a price by request";

    try {
      // When
      springDataPriceQueriesRepository.findPriceByRequest(priceRequest);
      fail();
    } catch (ProductRepositoryException exception) {
      // Then
      assertEquals(ErrorsEnum.REPOSITORY_EXCEPTION.getMessage(), exception.getMessage());
      assertEquals(ErrorsEnum.REPOSITORY_EXCEPTION, exception.getError());
      assertEquals(runtimeException, exception.getCause());

      verify(jpaPriceRepository)
          .findPricesByBrandIdAndProductIdAndDateApplication(
              priceRequest.getBrandId(),
              priceRequest.getProductId(),
              priceRequest.getDateApplication());
      verifyNoInteractions(mapper);

      // Logging
      assertEquals(1, loggingEventListAppender.list.size());
      assertEquals(expectedLogLevel, loggingEventListAppender.list.get(0).getLevel());
      assertEquals(expectedLog, loggingEventListAppender.list.get(0).getFormattedMessage());
    }
  }

  private Optional<PriceResponse> getPriceResponseFromPriceEntity(PriceEntity priceEntity) {
    return Optional.of(
        PriceResponse.builder()
            .brandId(priceEntity.getBrandId())
            .productId(priceEntity.getProductId())
            .priceList(priceEntity.getPriceList())
            .startDate(priceEntity.getStartDate())
            .endDate(priceEntity.getEndDate())
            .priority(priceEntity.getPriority())
            .price(priceEntity.getPrice())
            .curr(priceEntity.getCurr())
            .build());
  }

  private PriceEntity getPriceEntity() {
    return new PriceEntity(
        ID, BRAND_ID, START_DATE, END_DATE, PRICE_LIST, PRODUCT_ID, PRIORITY, PRICE, CURRENCY);
  }
}
