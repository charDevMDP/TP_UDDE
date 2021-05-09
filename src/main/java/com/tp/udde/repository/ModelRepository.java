package com.tp.udde.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.tp.udde.domain.Model;

@Repository
public interface ModelRepository extends JpaRepository<Model, Integer> {
}