package com.example.infrastructure.database.springdata.repository.price;

import com.example.infrastructure.database.springdata.model.jpa.PriceDto;
import java.time.Instant;
import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaPriceRepository extends MongoRepository<PriceDto, ObjectId> {

  @Query(
      "{ 'brandId' : ?0, 'productId' : ?1, 'startDate' : { $lte: ?2 }, 'endDate' : { $gte: ?2 } }")
  List<PriceDto> findPricesByBrandIdAndProductIdAndDateApplication(
      Integer brandId, Integer productId, Instant dateApplication);
}
