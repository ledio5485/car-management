package com.cars.carmanagement.api;

import com.cars.carmanagement.TestConfig;
import com.cars.carmanagement.service.CarService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CarResource.class)
@Import({TestConfig.class})
class CarResourceTest {
    private static final ZonedDateTime NOW = ZonedDateTime.now();

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private CarService carService;

    @Nested
    @DisplayName("Tests for the method createCar")
    class CreateCar {
        @Test
        @DisplayName("should get BAD_REQUEST when creating new car with missing mandatory field")
        void shouldGetBadRequestWhenCreatingNewCarWithMissingMandatoryField() throws Exception {
            CreateCarCommand command = new CreateCarCommand("", "B-12345", "Daimler", "Berlin", Status.AVAILABLE);

            mockMvc.perform(post("/cars")
                            .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                            .content(objectMapper.writeValueAsString(command)))
                    .andExpect(status().isBadRequest());
        }

        @Test
        void shouldCreateCar() throws Exception {
            CreateCarCommand command = new CreateCarCommand("Benz", "B-12345", "Daimler", "Berlin", Status.AVAILABLE);
            CarDTO expected = new CarDTO(UUID.randomUUID(), "Benz", "B-12345", "Daimler", "Berlin", Status.AVAILABLE, NOW, NOW);

            when(carService.createCar(command))
                    .thenReturn(expected);

            MvcResult mvcResult = mockMvc.perform(post("/cars")
                            .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                            .content(objectMapper.writeValueAsString(command)))
                    .andExpect(status().isCreated())
                    .andReturn();

            String actualResponseBody = mvcResult.getResponse().getContentAsString();
            String expectedResponseBody = objectMapper.writeValueAsString(expected);
            assertThat(actualResponseBody).isEqualToIgnoringWhitespace(expectedResponseBody);
        }
    }

    @Nested
    @DisplayName("Tests for the method getCars")
    class GetCars {
        @Test
        void shouldGetEmptyCollectionWhenNoCarsAreCreatedYet() throws Exception {
            when(carService.getCars())
                    .thenReturn(List.of());

            MvcResult mvcResult = mockMvc.perform(get("/cars")
                            .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                    .andExpect(status().isOk())
                    .andReturn();

            String actualResponseBody = mvcResult.getResponse().getContentAsString();
            String expectedResponseBody = objectMapper.writeValueAsString(List.of());
            assertThat(actualResponseBody).isEqualToIgnoringWhitespace(expectedResponseBody);
        }

        @Test
        void shouldGetCars() throws Exception {
            CarDTO car1 = new CarDTO(UUID.randomUUID(), "Benz", "B-12345", "Daimler", "Berlin", Status.AVAILABLE, NOW, NOW);
            CarDTO car2 = new CarDTO(UUID.randomUUID(), "Benz", "B-54321", "Daimler", "Berlin", Status.AVAILABLE, NOW, NOW);

            when(carService.getCars())
                    .thenReturn(List.of(car1, car2));

            MvcResult mvcResult = mockMvc.perform(get("/cars")
                            .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                    .andExpect(status().isOk())
                    .andReturn();

            String actualResponseBody = mvcResult.getResponse().getContentAsString();
            String expectedResponseBody = objectMapper.writeValueAsString(List.of(car1, car2));
            assertThat(actualResponseBody).isEqualToIgnoringWhitespace(expectedResponseBody);
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
            CarDTO expectedCar = new CarDTO(UUID.randomUUID(), "Benz", "B-12345", "Daimler", "Berlin", Status.AVAILABLE, NOW, NOW);

            when(carService.findCarById(expectedCar.id()))
                    .thenReturn(Optional.of(expectedCar));

            MvcResult mvcResult = mockMvc.perform(get("/cars/{id}", expectedCar.id())
                            .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                    .andExpect(status().isOk())
                    .andReturn();

            String actualResponseBody = mvcResult.getResponse().getContentAsString();
            String expectedResponseBody = objectMapper.writeValueAsString(expectedCar);
            assertThat(actualResponseBody).isEqualToIgnoringWhitespace(expectedResponseBody);
        }
    }
}