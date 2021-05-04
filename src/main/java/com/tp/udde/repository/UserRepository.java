package com.tp.udde.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.tp.udde.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}