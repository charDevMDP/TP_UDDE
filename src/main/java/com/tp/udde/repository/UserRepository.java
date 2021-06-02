package com.tp.udde.repository;



import com.tp.udde.domain.Meter;
import com.tp.udde.domain.dto.MeterUserDto;
import com.tp.udde.projections.MeterUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.tp.udde.domain.User;

import java.util.List;
import java.util.Map;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "select u.* from users u where u.surname = ?1 and u.password = ?2", nativeQuery = true)
    User getByUsername(String surname, String password);

    // traer el medidor que corresponde al usuario
    @Query(value = "SELECT us.name as name, me.number as numberMeter from users us "+
            "inner join addresses ad on ad.id_user = us.id "+
            "inner join meters me on me.id_address = ad.id "+
            "WHERE us.id = ?1", nativeQuery = true)
    MeterUser getMeterUser(Integer user_id);
}
