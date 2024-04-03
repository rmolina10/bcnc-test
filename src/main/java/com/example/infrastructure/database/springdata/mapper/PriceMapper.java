package com.example.infrastructure.database.springdata.mapper;

import com.example.domain.price.model.PriceResponse;
import com.example.infrastructure.database.springdata.model.jpa.PriceDto;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PriceMapper {

  PriceResponse toDomain(PriceDto priceDto);

  List<PriceResponse> toDomain(List<PriceDto> priceDtoList);
}
