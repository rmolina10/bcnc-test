package com.example.infrastructure.rest.spring.mapper;

import com.example.domain.exception.ErrorsEnum;
import com.example.domain.exception.ProductValidationException;
import com.example.domain.price.model.PriceRequest;
import com.example.domain.price.model.PriceResponse;
import com.example.infrastructure.rest.spring.dto.PriceResponseWebDto;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface PriceWebMapper {

  @Mapping(source = "brandId", target = "brandId")
  @Mapping(source = "productId", target = "productId")
  @Mapping(
      source = "dateApplication",
      target = "dateApplication",
      qualifiedByName = "stringToLocalDateTime")
  PriceRequest toDomain(Integer brandId, Integer productId, String dateApplication);

  PriceResponseWebDto toDto(PriceResponse priceResponse);

  List<PriceResponseWebDto> toDto(List<PriceResponse> priceResponseList);

  @Named("stringToLocalDateTime")
  default LocalDateTime stringToLocalDateTime(String dateApplication) {
    try {
      ZonedDateTime zonedDateTime =
          ZonedDateTime.parse(dateApplication, DateTimeFormatter.ISO_DATE_TIME);
      return zonedDateTime.withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime();
    } catch (DateTimeParseException e) {
      throw new ProductValidationException(
          ErrorsEnum.VALIDATION_EXCEPTION_INVALID_DATE_APPLICATION, e);
    }
  }
}
