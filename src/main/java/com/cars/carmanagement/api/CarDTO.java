package com.cars.carmanagement.api;

import java.time.ZonedDateTime;
import java.util.UUID;

public record CarDTO(UUID id, String brand,
                     String licensePlate,
                     String manufacturer,
                     String operationCity,
                     Status status,
                     ZonedDateTime createdAt,
                     ZonedDateTime lastModifiedAt) {
}
