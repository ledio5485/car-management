package com.cars.carmanagement.api;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record CreateCarCommand(@NotBlank String brand,
                               @NotBlank String licensePlate,
                               @NotBlank String manufacturer,
                               @NotBlank String operationCity,
                               @NotNull Status status) {
}
