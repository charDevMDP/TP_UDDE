package com.tp.udde.repository;

import com.tp.udde.projections.MeterUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.tp.udde.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "select * from users u", nativeQuery = true)
    Page<User> getUsers(Pageable pageable);

    @Query(value = "select u.* from users u where u.username = ?1 and u.password = ?2", nativeQuery = true)
    User getByUsernameAndPassword(String username, String password);

    // traer el medidor que corresponde al usuario
    @Query(value = "SELECT us.name as name, me.number as numberMeter from users us "+
            "inner join addresses ad on ad.id_user = us.id "+
            "inner join meters me on me.id_address = ad.id "+
            "WHERE us.id = ?1", nativeQuery = true)
    MeterUser getMeterUser(Integer user_id);
}
