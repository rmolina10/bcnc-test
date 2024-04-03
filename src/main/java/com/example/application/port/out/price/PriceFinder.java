package com.example.application.port.out.price;

import com.example.domain.price.model.PriceRequest;
import com.example.domain.price.model.PriceResponse;
import java.util.List;
import lombok.NonNull;

public interface PriceFinder {

  List<PriceResponse> findByRequest(@NonNull PriceRequest priceRequest);
}
