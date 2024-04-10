package com.example.infrastructure.database.springdata.mapper;

import com.example.domain.price.model.PriceResponse;
import com.example.infrastructure.database.springdata.model.jpa.PriceEntity;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PriceMapper {

  PriceResponse toDomain(PriceEntity priceEntity);

  List<PriceResponse> toDomain(List<PriceEntity> priceEntityList);
}
