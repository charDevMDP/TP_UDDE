package com.tp.udde.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.tp.udde.domain.Rate;

@Repository
public interface RateRepository extends JpaRepository<Rate, Integer> {
}