package com.example.infrastructure.database.springdata.repository.price;

import com.example.infrastructure.database.springdata.model.jpa.PriceEntity;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaPriceRepository extends JpaRepository<PriceEntity, Long> {

  @Query(
      "SELECT p FROM PriceEntity p "
          + "WHERE p.brandId = :brandId "
          + "AND p.productId = :productId "
          + "AND :dateApplication BETWEEN p.startDate AND p.endDate "
          + "ORDER BY p.priceList DESC, p.priority DESC "
          + "LIMIT 1")
  Optional<PriceEntity> findPricesByBrandIdAndProductIdAndDateApplication(
      @Param("brandId") Integer brandId,
      @Param("productId") Integer productId,
      @Param("dateApplication") LocalDateTime dateApplication);
}
