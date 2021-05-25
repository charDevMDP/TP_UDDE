package com.tp.udde.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tp.udde.domain.enums.UserType;
import lombok.*;
import org.springframework.lang.Nullable;
import javax.persistence.*;

@Entity(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Data
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Integer id;

    @NonNull
    @Column(name = "name", nullable = false)
    private String name;

    @NonNull
    @Column(name = "surname", nullable = false)
    private String surname;

    @NonNull
    @Column(name = "dni", nullable = false, unique = true)
    private String dni;

    @NonNull
    @JsonIgnore
    @Column(name = "password",nullable = false)
    private String password;

    @Nullable
    @Column(name = "email",nullable = false,unique = true)
    private String email;

    @Column(name = "user_type", columnDefinition = "varchar(20) default 'CLIENT'")
    @Enumerated(value = EnumType.STRING)
    private UserType userType;

}
