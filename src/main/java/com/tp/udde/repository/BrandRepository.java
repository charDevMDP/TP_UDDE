package com.tp.udde.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.tp.udde.domain.Brand;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> {
}