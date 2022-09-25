package com.cars.carmanagement.persistence;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.UUID;

@Entity
@EntityListeners(AuditingEntityListener.class)
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
    @CreatedDate
    private long createdDate;
    @LastModifiedDate
    private long lastModifiedDate;
}
