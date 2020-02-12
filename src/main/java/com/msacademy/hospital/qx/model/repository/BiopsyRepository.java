package com.msacademy.hospital.qx.model.repository;

import com.msacademy.hospital.qx.model.entity.Biopsy;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BiopsyRepository extends CrudRepository<Biopsy,Integer> {

    @Query("SELECT s FROM Biopsy s WHERE s.idBiopsy = ?1")
    Biopsy findById(@Param("idBiopsy") int idBiopsy);

    @Query("SELECT s FROM Biopsy s WHERE s.idBiopsy > 0")
    List<Biopsy> findAll();

}