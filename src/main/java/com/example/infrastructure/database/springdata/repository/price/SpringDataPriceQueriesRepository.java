package com.example.infrastructure.database.springdata.repository.price;

import com.example.domain.exception.ErrorsEnum;
import com.example.domain.exception.ProductRepositoryException;
import com.example.domain.price.PriceQueries;
import com.example.domain.price.model.PriceRequest;
import com.example.domain.price.model.PriceResponse;
import com.example.infrastructure.database.springdata.mapper.PriceMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class SpringDataPriceQueriesRepository implements PriceQueries {

  private final PriceMapper mapper;
  private final JpaPriceRepository jpaPriceRepository;

  @Override
  public List<PriceResponse> findAllByRequest(PriceRequest priceRequest) {
    log.debug("Getting price list by request");
    try {
      return mapper.toDomain(
          jpaPriceRepository.findPricesByBrandIdAndProductIdAndDateApplication(
              priceRequest.getBrandId(),
              priceRequest.getProductId(),
              priceRequest.getDateApplication()));
    } catch (Exception exception) {
      String errorMessage = "Unable to retrieve Area list by ids from database.";
      throw new ProductRepositoryException(
          ErrorsEnum.REPOSITORY_EXCEPTION, exception, errorMessage);
    }
  }
}
