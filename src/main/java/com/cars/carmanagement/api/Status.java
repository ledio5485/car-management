package com.cars.carmanagement.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Status {
    @JsonProperty("available")
    AVAILABLE,
    @JsonProperty("in-maintenance")
    IN_MAINTENANCE,
    @JsonProperty("out-of-service")
    OUT_OF_SERVICE
}
