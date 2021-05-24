package com.tp.udde.repository;



import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.tp.udde.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "select u.* from users u where u.surname = ?1 and u.password = ?2", nativeQuery = true)
    User getByUsername(String surname, String password);
}
