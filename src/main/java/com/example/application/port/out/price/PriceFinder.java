package com.example.application.port.out.price;

import com.example.domain.price.model.PriceRequest;
import com.example.domain.price.model.PriceResponse;
import lombok.NonNull;

public interface PriceFinder {

  PriceResponse findPriceByRequest(@NonNull PriceRequest priceRequest);
}
