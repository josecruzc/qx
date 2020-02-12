package com.msacademy.hospital.qx.model.repository;

import com.msacademy.hospital.qx.model.entity.Binnacle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BinnacleRepository extends CrudRepository <Binnacle,Integer> {

    @Query("SELECT s FROM Binnacle s WHERE s.idBinnacle = ?1")
    Binnacle findById(@Param("idBinnacle") int idBinnacle);

    @Query("SELECT s FROM Binnacle s WHERE s.idBinnacle > 0")
    List<Binnacle> findAll();

}