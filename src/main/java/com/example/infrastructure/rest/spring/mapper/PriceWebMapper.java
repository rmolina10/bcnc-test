package com.example.infrastructure.rest.spring.mapper;

import com.example.domain.exception.ErrorsEnum;
import com.example.domain.exception.ProductValidationException;
import com.example.domain.price.model.PriceRequest;
import com.example.domain.price.model.PriceResponse;
import com.example.infrastructure.rest.spring.dto.PriceResponseWebDto;
import java.time.Instant;
import java.time.ZonedDateTime;
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
      qualifiedByName = "stringToInstant")
  PriceRequest toDomain(Integer brandId, Integer productId, String dateApplication);

  PriceResponseWebDto toDto(PriceResponse priceResponse);

  List<PriceResponseWebDto> toDto(List<PriceResponse> priceResponseList);

  @Named("stringToInstant")
  default Instant stringToInstant(String dateApplication) {
    try {
      ZonedDateTime zonedDateTime = ZonedDateTime.parse(dateApplication);
      return zonedDateTime.toInstant();
    } catch (DateTimeParseException e) {
      throw new ProductValidationException(
          ErrorsEnum.VALIDATION_EXCEPTION_INVALID_DATE_APPLICATION, e);
    }
  }
}
