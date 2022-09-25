package com.cars.carmanagement.api;

import com.cars.carmanagement.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cars")
@RequiredArgsConstructor
public class CarResource {
    private final CarService carService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CarDTO createCar(@RequestBody @Valid CreateCarCommand command) {
        return carService.createCar(command);
    }

    @GetMapping
    public List<CarDTO> getCars() {
        return carService.getCars();
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<CarDTO> findCarById(@PathVariable("id") @NotNull UUID carId) {
        return ResponseEntity.of(carService.findCarById(carId));
    }
}
