package com.example.infrastructure.database.springdata.repository.price;

import com.example.infrastructure.database.springdata.model.jpa.PriceEntity;
import java.time.LocalDateTime;
import java.util.List;
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
          + "AND (p.priceList, p.priority) IN ("
          + "   SELECT p2.priceList, MAX(p2.priority) "
          + "   FROM PriceEntity p2 "
          + "   WHERE p2.brandId = :brandId "
          + "   AND p2.productId = :productId "
          + "   AND :dateApplication BETWEEN p2.startDate AND p2.endDate "
          + "   GROUP BY p2.priceList)")
  List<PriceEntity> findPricesByBrandIdAndProductIdAndDateApplication(
      @Param("brandId") Integer brandId,
      @Param("productId") Integer productId,
      @Param("dateApplication") LocalDateTime dateApplication);
}
