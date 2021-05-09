package com.tp.udde.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.tp.udde.domain.City;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {
}