package com.tp.udde.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.tp.udde.domain.Measurer;

@Repository
public interface MeasurerRepository extends JpaRepository<Measurer, Integer> {
}