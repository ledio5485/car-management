package com.cars.carmanagement.service;

import com.cars.carmanagement.api.CarDTO;
import com.cars.carmanagement.api.Status;
import com.cars.carmanagement.persistence.CarEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Component
public class CarEntityToCarDTOConverter implements Converter<CarEntity, CarDTO> {

    @Override
    public CarDTO convert(CarEntity entity) {
        Status status = Status.valueOf(entity.getStatus().name());
        return new CarDTO(entity.getId(), entity.getBrand(), entity.getLicensePlate(), entity.getManufacturer(), entity.getOperationCity(), status, toUTCDateTime(entity.getCreatedDate()), toUTCDateTime(entity.getLastModifiedDate()));
    }

    private ZonedDateTime toUTCDateTime(long entity) {
        return ZonedDateTime.ofInstant(Instant.ofEpochMilli(entity), ZoneId.of("UTC"));
    }
}
