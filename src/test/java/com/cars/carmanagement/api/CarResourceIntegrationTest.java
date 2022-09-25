package com.cars.carmanagement.api;

import com.cars.carmanagement.service.CarService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class CarResourceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CarService carService;

    @Nested
    @DisplayName("Tests for the method createCar")
    class CreateCar {

        @Test
        void shouldCreateCar() throws Exception {
            assertThat(carService.getCars().size()).isEqualTo(0);
            CreateCarCommand command = new CreateCarCommand("Benz", "B-12345", "Daimler", "Berlin", Status.AVAILABLE);

            MvcResult mvcResult = mockMvc.perform(post("/cars")
                            .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                            .content(objectMapper.writeValueAsString(command)))
                    .andExpect(status().isCreated())
                    .andReturn();

            String actualResponseBody = mvcResult.getResponse().getContentAsString();
            CarDTO actual = objectMapper.readValue(actualResponseBody, CarDTO.class);

            assertThat(carService.getCars().size()).isEqualTo(1);
            CarDTO savedCar = carService.getCars().get(0);
            CarDTO expected = new CarDTO(savedCar.id(), "Benz", "B-12345", "Daimler", "Berlin", Status.AVAILABLE, savedCar.createdAt(), savedCar.lastModifiedAt());
            assertThat(actual).isEqualTo(expected);
        }
    }

    @Nested
    @DisplayName("Tests for the method getCars")
    class GetCars {
        @Test
        void shouldGetEmptyCollectionWhenNoCarsAreCreatedYet() throws Exception {
            MvcResult mvcResult = mockMvc.perform(get("/cars")
                            .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                    .andExpect(status().isOk())
                    .andReturn();

            String actualResponseBody = mvcResult.getResponse().getContentAsString();
            List<CarDTO> actual = objectMapper.readValue(actualResponseBody, new TypeReference<>() {
            });

            List<CarDTO> expected = List.of();
            assertThat(actual).isEqualTo(expected);
        }

        @Test
        void shouldGetCars() throws Exception {
            CreateCarCommand command1 = new CreateCarCommand("Benz", "B-12345", "Daimler", "Berlin", Status.AVAILABLE);
            CreateCarCommand command2 = new CreateCarCommand("Benz", "B-54321", "Daimler", "Berlin", Status.AVAILABLE);
            CarDTO car1 = carService.createCar(command1);
            CarDTO car2 = carService.createCar(command2);

            MvcResult mvcResult = mockMvc.perform(get("/cars")
                            .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                    .andExpect(status().isOk())
                    .andReturn();

            String actualResponseBody = mvcResult.getResponse().getContentAsString();
            List<CarDTO> actual = objectMapper.readValue(actualResponseBody, new TypeReference<>() {
            });

            List<CarDTO> expected = List.of(car1, car2);
            assertThat(actual).isEqualTo(expected);
        }
    }

    @Nested
    @DisplayName("Tests for the method findCarById")
    class FindCarById {
        @Test
        @DisplayName("should return NOT_FOUND when car does not exist")
        void shouldReturnNotFoundWhenCarDoesNotExist() throws Exception {
            mockMvc.perform(get("/cars/{id}", UUID.randomUUID())
                            .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                    .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("should find car when it exists")
        void shouldFindCarWhenExist() throws Exception {
            CreateCarCommand command = new CreateCarCommand("Benz", "B-12345", "Daimler", "Berlin", Status.AVAILABLE);
            CarDTO expected = carService.createCar(command);

            MvcResult mvcResult = mockMvc.perform(get("/cars/{id}", expected.id())
                            .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                    .andExpect(status().isOk())
                    .andReturn();

            String actualResponseBody = mvcResult.getResponse().getContentAsString();
            CarDTO actual = objectMapper.readValue(actualResponseBody, CarDTO.class);

            assertThat(actual).isEqualTo(expected);
        }
    }
}