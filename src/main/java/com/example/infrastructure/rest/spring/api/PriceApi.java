package com.example.infrastructure.rest.spring.api;

import com.example.infrastructure.rest.spring.dto.ErrorResponseWebDto;
import com.example.infrastructure.rest.spring.dto.PriceResponseWebDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Prices")
@RequestMapping("/v1/prices")
public interface PriceApi {

  @Operation(summary = "Get inference result by data pattern")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "OK",
            content =
                @Content(
                    array =
                        @ArraySchema(
                            schema = @Schema(implementation = PriceResponseWebDto.class)))),
        @ApiResponse(
            responseCode = "401",
            description = "Unauthorized",
            content = @Content(schema = @Schema(implementation = ErrorResponseWebDto.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Not found",
            content = @Content(schema = @Schema(implementation = ErrorResponseWebDto.class))),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content(schema = @Schema(implementation = ErrorResponseWebDto.class)))
      })
  @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<List<PriceResponseWebDto>> getPrice(
      @Parameter(name = "brandId", description = "Brand identifier", example = "1")
          @RequestParam(name = "brandId", required = true)
          Integer brandId,
      @Parameter(name = "productId", description = "Product id", example = "35455")
          @RequestParam(name = "productId", required = true)
          Integer productId,
      @Parameter(
              name = "dateApplication",
              description = "Date Application",
              example = "2020-06-14T00:00:00.000-00:00")
          @RequestParam(name = "dateApplication", required = true)
          String dateApplication);
}
