package org.carlisting.workfloworchestrator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarListingRequest {
    private Long userId;
    private String make;
    private String model;
    private Integer year;
    private BigDecimal price;
    private String description;
}
