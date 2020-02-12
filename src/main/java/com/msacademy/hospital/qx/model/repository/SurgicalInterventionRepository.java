package com.msacademy.hospital.qx.model.repository;

import com.msacademy.hospital.qx.model.entity.SurgicalIntervention;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SurgicalInterventionRepository extends CrudRepository<SurgicalIntervention,Integer> {

    @Query("SELECT s FROM SurgicalIntervention s WHERE s.idSurgicalIntervention = ?1")
    SurgicalIntervention findById(@Param("idSurgicalIntervention") int idSurgicalIntervention);

    @Query("SELECT s FROM SurgicalIntervention s WHERE s.idSurgicalIntervention > 0")
    List<SurgicalIntervention> findAll();

    @Query("SELECT s FROM SurgicalIntervention s WHERE s.duration = ?1")
    List<SurgicalIntervention> findByDuration(@Param("duration") int duration);

    @Query("SELECT s FROM SurgicalIntervention s WHERE s.surgicalInterventionCat = ?1")
    List<SurgicalIntervention> findBySurgicalInterventionCat(@Param("surgicalInterventionCat") String surgicalInterventionCat);

    /*@Query("SELECT s FROM SurgicalIntervention s WHERE s.imagingResult = ?1")
    List<SurgicalIntervention> findByPatient(@Param("patient") String patient);¨*/

    @Query("SELECT s FROM SurgicalIntervention s WHERE s.duration = ?1 AND s.surgicalInterventionCat = ?2")
    List<SurgicalIntervention> findByDurationAndSurgicalInterventionCat(@Param("duration") int duration, @Param("surgicalInterventionCat") String surgicalInterventionCat);

}