package com.tp.udde.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.tp.udde.domain.Meters;

@Repository
public interface MetersRepository extends JpaRepository<Meters, Integer> {
}