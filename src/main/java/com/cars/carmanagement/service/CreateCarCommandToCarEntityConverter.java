package com.cars.carmanagement.service;

import com.cars.carmanagement.api.CreateCarCommand;
import com.cars.carmanagement.persistence.CarEntity;
import com.cars.carmanagement.persistence.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateCarCommandToCarEntityConverter implements Converter<CreateCarCommand, CarEntity> {
    @Override
    public CarEntity convert(CreateCarCommand command) {
        CarEntity car = new CarEntity();
        car.setBrand(command.brand());
        car.setLicensePlate(command.licensePlate());
        car.setManufacturer(command.manufacturer());
        car.setOperationCity(command.operationCity());
        car.setStatus(Status.valueOf(command.status().name()));

        return car;
    }
}
