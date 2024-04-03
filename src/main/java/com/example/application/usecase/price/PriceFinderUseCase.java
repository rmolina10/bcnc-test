package com.example.application.usecase.price;

import com.example.application.port.out.price.PriceFinder;
import com.example.domain.price.PriceQueries;
import com.example.domain.price.model.PriceRequest;
import com.example.domain.price.model.PriceResponse;
import java.util.Comparator;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class PriceFinderUseCase implements PriceFinder {

  private final PriceQueries priceQueries;

  public List<PriceResponse> findByRequest(@NonNull PriceRequest priceRequest) {
    log.debug(
        "Searching prices with parameter 'brandId': {}, 'productId': {}, 'dateApplication': {}",
        priceRequest.getBrandId(),
        priceRequest.getProductId(),
        priceRequest.getDateApplication());
    List<PriceResponse> priceResponseList = priceQueries.findAllByRequest(priceRequest);
    return priceResponseList.stream()
        .collect(
            Collectors.toMap(
                PriceResponse::getPriceList,
                Function.identity(),
                BinaryOperator.maxBy(Comparator.comparingInt(PriceResponse::getPriority))))
        .values()
        .stream()
        .toList();
  }
}
