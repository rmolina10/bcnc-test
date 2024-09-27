package com.example.domain.price;

import com.example.domain.price.model.PriceRequest;
import com.example.domain.price.model.PriceResponse;
import java.util.Optional;

public interface PriceQueries {

  Optional<PriceResponse> findPriceByRequest(PriceRequest priceRequest);
}
