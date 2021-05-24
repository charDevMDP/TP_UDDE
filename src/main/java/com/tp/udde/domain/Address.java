package com.tp.udde.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "addresses")
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Data
@Builder
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name="id_city",foreignKey = @ForeignKey(name = "id_city"))
    private  City city;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference(value="id_user")
    @JoinColumn(name="id_user",foreignKey = @ForeignKey(name = "id_user"))
    private User user;

    @Column(name = "name")
    private String name;

    @Column(name = "number")
    private int number;

    @Column(name = "department")
    private int department;
}
