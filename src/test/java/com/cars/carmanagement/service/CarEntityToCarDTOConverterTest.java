package com.cars.carmanagement.service;

import com.cars.carmanagement.api.CarDTO;
import com.cars.carmanagement.api.Status;
import com.cars.carmanagement.persistence.CarEntity;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class CarEntityToCarDTOConverterTest {
    private final CarEntityToCarDTOConverter converter = new CarEntityToCarDTOConverter();

    @Test
    void shouldConvertCarEntityToDTO() {
        UUID id = UUID.randomUUID();
        long now = System.currentTimeMillis();
        ZonedDateTime dateTime = ZonedDateTime.ofInstant(Instant.ofEpochMilli(now), ZoneId.of("UTC"));
        CarEntity entity = new CarEntity();
        entity.setId(id);
        entity.setBrand("Benz");
        entity.setLicensePlate("B-12345");
        entity.setManufacturer("Daimler");
        entity.setOperationCity("Berlin");
        entity.setStatus(com.cars.carmanagement.persistence.Status.AVAILABLE);
        entity.setCreatedDate(now);
        entity.setLastModifiedDate(now);

        CarDTO actual = converter.convert(entity);

        CarDTO expected = new CarDTO(id, "Benz", "B-12345", "Daimler", "Berlin", Status.AVAILABLE, dateTime, dateTime);
        assertThat(actual).isEqualTo(expected);
    }
}