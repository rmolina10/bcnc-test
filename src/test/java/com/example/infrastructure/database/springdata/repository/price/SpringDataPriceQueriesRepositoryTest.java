package com.example.infrastructure.database.springdata.repository.price;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
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
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
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
  void findAllByRequest() {
    // Given
    PriceRequest priceRequest =
        PriceRequest.builder()
            .brandId(BRAND_ID)
            .productId(PRODUCT_ID)
            .dateApplication(DATE_APPLICATION)
            .build();
    List<PriceEntity> PriceEntityList = getPriceList();
    when(jpaPriceRepository.findPricesByBrandIdAndProductIdAndDateApplication(
            priceRequest.getBrandId(),
            priceRequest.getProductId(),
            priceRequest.getDateApplication()))
        .thenReturn(PriceEntityList);

    List<PriceResponse> expectedPriceResponseList =
        getPriceResponseListFromAreaList(PriceEntityList);
    when(mapper.toDomain(PriceEntityList)).thenReturn(expectedPriceResponseList);

    Level expectedLogLevel = Level.DEBUG;
    String expectedLog = "Getting price list by request";

    // When
    List<PriceResponse> responsePriceList =
        springDataPriceQueriesRepository.findAllByRequest(priceRequest);

    // Then
    assertEquals(expectedPriceResponseList, responsePriceList);

    verify(jpaPriceRepository)
        .findPricesByBrandIdAndProductIdAndDateApplication(
            priceRequest.getBrandId(),
            priceRequest.getProductId(),
            priceRequest.getDateApplication());
    verify(mapper).toDomain(PriceEntityList);

    // Logging
    assertEquals(1, loggingEventListAppender.list.size());
    assertEquals(expectedLogLevel, loggingEventListAppender.list.get(0).getLevel());
    assertEquals(expectedLog, loggingEventListAppender.list.get(0).getFormattedMessage());
  }

  @Test
  void findAllByRequest_throwsProductRepositoryException() {
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
    String expectedLog = "Getting price list by request";

    try {
      // When
      springDataPriceQueriesRepository.findAllByRequest(priceRequest);
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

  private List<PriceResponse> getPriceResponseListFromAreaList(List<PriceEntity> PriceEntityList) {
    return PriceEntityList.stream()
        .map(
            areaEntity ->
                PriceResponse.builder()
                    .brandId(BRAND_ID)
                    .productId(PRODUCT_ID)
                    .priceList(PRICE_LIST)
                    .startDate(START_DATE)
                    .endDate(END_DATE)
                    .priority(PRIORITY)
                    .price(PRICE)
                    .curr(CURRENCY)
                    .build())
        .collect(Collectors.toList());
  }

  private List<PriceEntity> getPriceList() {
    return Collections.singletonList(getPrice());
  }

  private PriceEntity getPrice() {
    return new PriceEntity(
        ID, BRAND_ID, START_DATE, END_DATE, PRICE_LIST, PRODUCT_ID, PRIORITY, PRICE, CURRENCY);
  }
}
