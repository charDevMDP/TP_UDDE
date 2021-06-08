package com.tp.udde.domain;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity(name = "meters")
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Data
@Builder
public class Meter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name="id_address",foreignKey = @ForeignKey(name = "id_address"))
    private Address address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference(value="id_model")
    @JoinColumn(name="id_model",foreignKey = @ForeignKey(name = "id_model"))
    private Model model;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference(value="id_rate")
    @JoinColumn(name="id_rate",foreignKey = @ForeignKey(name = "id_rate"))
    private Rate rate;

    @Column(name = "number", unique = true)
    private Integer number;

    @Column(name = "password", nullable = false)
    private String password;

}