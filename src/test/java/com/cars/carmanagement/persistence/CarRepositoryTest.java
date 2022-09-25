package com.cars.carmanagement.persistence;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CarRepositoryTest {
    @Autowired
    private CarRepository carRepository;

    @Test
    void shouldGetEmptyEntityWhenCarDoesntExist() {
        Optional<CarEntity> actual = carRepository.findById(UUID.randomUUID());
        assertThat(actual).isEmpty();
    }

    @Test
    void shouldGetCarById() {
        CarEntity car = new CarEntity();
        CarEntity expected = carRepository.save(car);

        Optional<CarEntity> actual = carRepository.findById(expected.getId());

        assertThat(actual).isEqualTo(Optional.of(expected));
    }

    @Test
    void shouldGetAllCars() {
        CarEntity car1 = new CarEntity();
        CarEntity savedCar1 = carRepository.save(car1);

        CarEntity car2 = new CarEntity();
        CarEntity savedCar2 = carRepository.save(car2);

        List<CarEntity> actual = carRepository.findAll();

        List<CarEntity> expected = List.of(savedCar1, savedCar2);
        assertThat(actual).isEqualTo(expected);
    }
}