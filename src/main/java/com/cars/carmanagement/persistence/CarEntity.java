package com.cars.carmanagement.persistence;

import lombok.Data;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "CAR")
@Data
public class CarEntity {
    @Id
    @GeneratedValue
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;
    private String brand;
    private String licensePlate;
    private String manufacturer;
    private String operationCity;
    private Status status;
    private ZonedDateTime createdDate;
    private ZonedDateTime lastModifiedDate;
}
