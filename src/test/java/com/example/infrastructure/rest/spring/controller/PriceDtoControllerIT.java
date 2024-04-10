package com.example.infrastructure.rest.spring.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.infrastructure.rest.spring.dto.PriceResponseWebDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@DirtiesContext
class PriceDtoControllerIT {

  @Autowired private MockMvc mockMvc;

  private final String priceUrl = "/v1/prices";

  private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

  private static final Integer BRAND_ID = 1;
  private static final Integer PRODUCT_ID = 35455;
  private static final String CURRENCY = "EUR";

  @Test
  void getPrices_fourteenDayAndTenHour() throws Exception {
    // Given
    String brandId = "1";
    String productId = "35455";
    String dateApplication = "2020-06-14T10:00:00.000-00:00";
    List<PriceResponseWebDto> expectedPriceResponseWebDtoList =
        List.of(
            PriceResponseWebDto.builder()
                .brandId(BRAND_ID)
                .productId(PRODUCT_ID)
                .priceList(1)
                .startDate(LocalDateTime.of(2020, 6, 14, 0, 0))
                .endDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59))
                .price(55.5f)
                .curr(CURRENCY)
                .build(),
            PriceResponseWebDto.builder()
                .brandId(BRAND_ID)
                .productId(PRODUCT_ID)
                .priceList(5)
                .startDate(LocalDateTime.of(2020, 1, 15, 0, 0))
                .endDate(LocalDateTime.of(2020, 10, 31, 23, 59, 59))
                .price(175.88f)
                .curr(CURRENCY)
                .build());

    // When
    MvcResult resultGet =
        mockMvc
            .perform(
                MockMvcRequestBuilders.get(priceUrl)
                    .param("brandId", brandId)
                    .param("productId", productId)
                    .param("dateApplication", dateApplication)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();

    // Then
    assertEquals(HttpStatus.OK.value(), resultGet.getResponse().getStatus());
    List<PriceResponseWebDto> resultPriceResponseWebDtoList =
        objectMapper.readValue(
            resultGet.getResponse().getContentAsString(),
            new TypeReference<List<PriceResponseWebDto>>() {});
    assertNotNull(resultPriceResponseWebDtoList);
    assertEquals(2, resultPriceResponseWebDtoList.size());
    assertTrue(
        CollectionUtils.isEqualCollection(
            expectedPriceResponseWebDtoList, resultPriceResponseWebDtoList));
  }

  @Test
  void getPrices_fourteenDayAndSixteenHour() throws Exception {
    // Given
    String brandId = "1";
    String productId = "35455";
    String dateApplication = "2020-06-14T16:00:00.000-00:00";
    List<PriceResponseWebDto> expectedPriceResponseWebDtoList =
        List.of(
            PriceResponseWebDto.builder()
                .brandId(BRAND_ID)
                .productId(PRODUCT_ID)
                .priceList(1)
                .startDate(LocalDateTime.of(2020, 6, 14, 0, 0))
                .endDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59))
                .price(55.5f)
                .curr(CURRENCY)
                .build(),
            PriceResponseWebDto.builder()
                .brandId(BRAND_ID)
                .productId(PRODUCT_ID)
                .priceList(2)
                .startDate(LocalDateTime.of(2020, 6, 14, 15, 0))
                .endDate(LocalDateTime.of(2020, 6, 14, 18, 30, 0))
                .price(25.99f)
                .curr(CURRENCY)
                .build(),
            PriceResponseWebDto.builder()
                .brandId(BRAND_ID)
                .productId(PRODUCT_ID)
                .priceList(5)
                .startDate(LocalDateTime.of(2020, 1, 15, 0, 0))
                .endDate(LocalDateTime.of(2020, 10, 31, 23, 59, 59))
                .price(175.88f)
                .curr(CURRENCY)
                .build());

    // When
    MvcResult resultGet =
        mockMvc
            .perform(
                MockMvcRequestBuilders.get(priceUrl)
                    .param("brandId", brandId)
                    .param("productId", productId)
                    .param("dateApplication", dateApplication)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();

    // Then
    assertEquals(HttpStatus.OK.value(), resultGet.getResponse().getStatus());
    List<PriceResponseWebDto> resultPriceResponseWebDtoList =
        objectMapper.readValue(
            resultGet.getResponse().getContentAsString(),
            new TypeReference<List<PriceResponseWebDto>>() {});
    assertNotNull(resultPriceResponseWebDtoList);
    assertEquals(3, resultPriceResponseWebDtoList.size());
    assertTrue(
        CollectionUtils.isEqualCollection(
            expectedPriceResponseWebDtoList, resultPriceResponseWebDtoList));
  }

  @Test
  void getPrices_fourteenDayAndTwentyOneHour() throws Exception {
    // Given
    String brandId = "1";
    String productId = "35455";
    String dateApplication = "2020-06-14T21:00:00.000-00:00";
    List<PriceResponseWebDto> expectedPriceResponseWebDtoList =
        List.of(
            PriceResponseWebDto.builder()
                .brandId(BRAND_ID)
                .productId(PRODUCT_ID)
                .priceList(1)
                .startDate(LocalDateTime.of(2020, 6, 14, 0, 0))
                .endDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59))
                .price(55.5f)
                .curr(CURRENCY)
                .build(),
            PriceResponseWebDto.builder()
                .brandId(BRAND_ID)
                .productId(PRODUCT_ID)
                .priceList(5)
                .startDate(LocalDateTime.of(2020, 1, 15, 0, 0))
                .endDate(LocalDateTime.of(2020, 10, 31, 23, 59, 59))
                .price(175.88f)
                .curr(CURRENCY)
                .build());

    // When
    MvcResult resultGet =
        mockMvc
            .perform(
                MockMvcRequestBuilders.get(priceUrl)
                    .param("brandId", brandId)
                    .param("productId", productId)
                    .param("dateApplication", dateApplication)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();

    // Then
    assertEquals(HttpStatus.OK.value(), resultGet.getResponse().getStatus());
    List<PriceResponseWebDto> resultPriceResponseWebDtoList =
        objectMapper.readValue(
            resultGet.getResponse().getContentAsString(),
            new TypeReference<List<PriceResponseWebDto>>() {});
    assertNotNull(resultPriceResponseWebDtoList);
    assertEquals(2, resultPriceResponseWebDtoList.size());
    assertTrue(
        CollectionUtils.isEqualCollection(
            expectedPriceResponseWebDtoList, resultPriceResponseWebDtoList));
  }

  @Test
  void getPrices_fifteenDayAndTenHour() throws Exception {
    // Given
    String brandId = "1";
    String productId = "35455";
    String dateApplication = "2020-06-15T10:00:00.000-00:00";
    List<PriceResponseWebDto> expectedPriceResponseWebDtoList =
        List.of(
            PriceResponseWebDto.builder()
                .brandId(BRAND_ID)
                .productId(PRODUCT_ID)
                .priceList(1)
                .startDate(LocalDateTime.of(2020, 6, 14, 0, 0))
                .endDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59))
                .price(55.5f)
                .curr(CURRENCY)
                .build(),
            PriceResponseWebDto.builder()
                .brandId(BRAND_ID)
                .productId(PRODUCT_ID)
                .priceList(3)
                .startDate(LocalDateTime.of(2020, 6, 15, 0, 0))
                .endDate(LocalDateTime.of(2020, 6, 15, 11, 0, 0))
                .price(40.5f)
                .curr(CURRENCY)
                .build(),
            PriceResponseWebDto.builder()
                .brandId(BRAND_ID)
                .productId(PRODUCT_ID)
                .priceList(5)
                .startDate(LocalDateTime.of(2020, 1, 15, 0, 0))
                .endDate(LocalDateTime.of(2020, 10, 31, 23, 59, 59))
                .price(175.88f)
                .curr(CURRENCY)
                .build());

    // When
    MvcResult resultGet =
        mockMvc
            .perform(
                MockMvcRequestBuilders.get(priceUrl)
                    .param("brandId", brandId)
                    .param("productId", productId)
                    .param("dateApplication", dateApplication)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();

    // Then
    assertEquals(HttpStatus.OK.value(), resultGet.getResponse().getStatus());
    List<PriceResponseWebDto> resultPriceResponseWebDtoList =
        objectMapper.readValue(
            resultGet.getResponse().getContentAsString(),
            new TypeReference<List<PriceResponseWebDto>>() {});
    assertNotNull(resultPriceResponseWebDtoList);
    assertEquals(3, resultPriceResponseWebDtoList.size());
    assertTrue(
        CollectionUtils.isEqualCollection(
            expectedPriceResponseWebDtoList, resultPriceResponseWebDtoList));
  }

  @Test
  void getPrices_sixteenDayAndTwentyOneHour() throws Exception {
    // Given
    String brandId = "1";
    String productId = "35455";
    String dateApplication = "2020-06-16T21:00:00.000-00:00";
    List<PriceResponseWebDto> expectedPriceResponseWebDtoList =
        List.of(
            PriceResponseWebDto.builder()
                .brandId(BRAND_ID)
                .productId(PRODUCT_ID)
                .priceList(1)
                .startDate(LocalDateTime.of(2020, 6, 14, 0, 0))
                .endDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59))
                .price(55.5f)
                .curr(CURRENCY)
                .build(),
            PriceResponseWebDto.builder()
                .brandId(BRAND_ID)
                .productId(PRODUCT_ID)
                .priceList(4)
                .startDate(LocalDateTime.of(2020, 6, 15, 16, 0))
                .endDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59))
                .price(47.55f)
                .curr(CURRENCY)
                .build(),
            PriceResponseWebDto.builder()
                .brandId(BRAND_ID)
                .productId(PRODUCT_ID)
                .priceList(5)
                .startDate(LocalDateTime.of(2020, 1, 15, 0, 0))
                .endDate(LocalDateTime.of(2020, 10, 31, 23, 59, 59))
                .price(175.88f)
                .curr(CURRENCY)
                .build());

    // When
    MvcResult resultGet =
        mockMvc
            .perform(
                MockMvcRequestBuilders.get(priceUrl)
                    .param("brandId", brandId)
                    .param("productId", productId)
                    .param("dateApplication", dateApplication)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();

    // Then
    assertEquals(HttpStatus.OK.value(), resultGet.getResponse().getStatus());
    List<PriceResponseWebDto> resultPriceResponseWebDtoList =
        objectMapper.readValue(
            resultGet.getResponse().getContentAsString(),
            new TypeReference<List<PriceResponseWebDto>>() {});
    assertNotNull(resultPriceResponseWebDtoList);
    assertEquals(3, resultPriceResponseWebDtoList.size());
    assertTrue(
        CollectionUtils.isEqualCollection(
            expectedPriceResponseWebDtoList, resultPriceResponseWebDtoList));
  }
}
