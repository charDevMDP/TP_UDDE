package com.tp.udde.domain;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity(name = "meters_for_measurement")
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Data
@Builder
public class MetersForMeasurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference(value="id_meters")
    @JoinColumn(name="id_meters",foreignKey = @ForeignKey(name = "id"))
    private Meters meters;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference(value="id_measurement")
    @JoinColumn(name="id_measurement",foreignKey = @ForeignKey(name = "id"))
    private Measurement measurement;
}