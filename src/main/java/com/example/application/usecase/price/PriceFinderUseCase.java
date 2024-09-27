package com.example.application.usecase.price;

import com.example.application.port.out.price.PriceFinder;
import com.example.domain.exception.ErrorsEnum;
import com.example.domain.exception.ProductNotFoundException;
import com.example.domain.price.PriceQueries;
import com.example.domain.price.model.PriceRequest;
import com.example.domain.price.model.PriceResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class PriceFinderUseCase implements PriceFinder {

  private final PriceQueries priceQueries;

  public PriceResponse findPriceByRequest(@NonNull PriceRequest priceRequest) {
    log.debug(
        "Searching a price with parameter 'brandId': {}, 'productId': {}, 'dateApplication': {}",
        priceRequest.getBrandId(),
        priceRequest.getProductId(),
        priceRequest.getDateApplication());
    return priceQueries
        .findPriceByRequest(priceRequest)
        .orElseThrow(() -> new ProductNotFoundException(ErrorsEnum.PRICE_NOT_FOUND));
  }
}
