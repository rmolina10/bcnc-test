package com.example.config.usecase.price;

import com.example.application.usecase.price.PriceFinderUseCase;
import com.example.domain.price.PriceQueries;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PriceUseCaseConfig {

  private final PriceQueries priceQueries;

  public PriceUseCaseConfig(PriceQueries priceQueries) {
    this.priceQueries = priceQueries;
  }

  @Bean
  public PriceFinderUseCase areaFinderUseCase() {
    return new PriceFinderUseCase(priceQueries);
  }
}
