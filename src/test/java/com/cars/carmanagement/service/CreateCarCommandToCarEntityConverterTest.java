package com.cars.carmanagement.service;

import com.cars.carmanagement.api.CreateCarCommand;
import com.cars.carmanagement.api.Status;
import com.cars.carmanagement.persistence.CarEntity;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class CreateCarCommandToCarEntityConverterTest {
    private final Clock clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
    private final CreateCarCommandToCarEntityConverter converter = new CreateCarCommandToCarEntityConverter(clock);

    @Test
    void shouldConvertCommandToCarEntity() {
        CreateCarCommand command = new CreateCarCommand("Benz", "B-12345", "Daimler", "Berlin", Status.AVAILABLE);

        CarEntity actual = converter.convert(command);

        CarEntity expected = new CarEntity();
        expected.setBrand("Benz");
        expected.setLicensePlate("B-12345");
        expected.setManufacturer("Daimler");
        expected.setOperationCity("Berlin");
        expected.setStatus(com.cars.carmanagement.persistence.Status.AVAILABLE);
        expected.setCreatedDate(ZonedDateTime.now(clock));
        expected.setLastModifiedDate(ZonedDateTime.now(clock));
        assertThat(actual).isEqualTo(expected);
    }
}