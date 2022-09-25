package com.cars.carmanagement.service;

import com.cars.carmanagement.api.CarDTO;
import com.cars.carmanagement.api.CreateCarCommand;
import com.cars.carmanagement.persistence.CarEntity;
import com.cars.carmanagement.persistence.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CarService {
    private final CarRepository carRepository;
    private final ConversionService conversionService;

    public CarDTO createCar(CreateCarCommand command) {
        CarEntity car = conversionService.convert(command, CarEntity.class);
        CarEntity savedCar = carRepository.save(car);

        return conversionService.convert(savedCar, CarDTO.class);
    }

    public List<CarDTO> getCars() {
        return carRepository.findAll().stream()
                .map(car -> conversionService.convert(car, CarDTO.class))
                .toList();
    }

    public Optional<CarDTO> findCarById(UUID carId) {
        return carRepository.findById(carId)
                .map(car -> conversionService.convert(car, CarDTO.class));
    }
}
