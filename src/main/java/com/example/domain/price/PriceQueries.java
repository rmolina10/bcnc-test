package com.example.domain.price;

import com.example.domain.price.model.PriceRequest;
import com.example.domain.price.model.PriceResponse;
import java.util.List;

public interface PriceQueries {

  List<PriceResponse> findAllByRequest(PriceRequest priceRequest);
}
