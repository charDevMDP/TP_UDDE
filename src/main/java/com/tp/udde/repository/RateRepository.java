package com.tp.udde.repository;

import com.tp.udde.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.tp.udde.domain.Rate;

@Repository
public interface RateRepository extends JpaRepository<Rate, Integer> {

    @Query(value = "select * from rates", nativeQuery = true)
    Page<Rate> getAll(Pageable pageable);

}