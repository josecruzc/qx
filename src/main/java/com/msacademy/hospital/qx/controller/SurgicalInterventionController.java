package com.msacademy.hospital.qx.controller;

import com.msacademy.hospital.qx.model.entity.SurgicalIntervention;
import com.msacademy.hospital.qx.model.repository.BinnacleRepository;
import com.msacademy.hospital.qx.model.repository.BiopsyRepository;
import com.msacademy.hospital.qx.model.repository.SurgicalInterventionRepository;
import com.msacademy.hospital.qx.model.entity.Response;
import com.msacademy.hospital.qx.service.SurgicalInterventionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class SurgicalInterventionController {

    @Autowired
    private SurgicalInterventionService surgicalInterventionService;

    @GetMapping(value = "/surgical-interventions/{idSurgicalIntervention}")
    public ResponseEntity getSurgicalIntervention(@PathVariable(value = "idSurgicalIntervention") int idSurgicalIntervention){
        return surgicalInterventionService.getSurgicalInterventionById(idSurgicalIntervention);
    }

    @GetMapping(value = "/surgical-interventions")
    public ResponseEntity getSurgicalInterventions(@RequestParam Optional<String> duration, @RequestParam Optional<String> surgicalInterventionCat){
        if(duration.isPresent() == false && surgicalInterventionCat.isPresent() == false)
            return surgicalInterventionService.getAllSurgicalInterventions();
        else if(surgicalInterventionCat.isPresent() == false)
            return surgicalInterventionService.getSurgicalInterventionsByDuration(duration);
        else if(duration.isPresent() == false)
            return surgicalInterventionService.getSurgicalInterventionsBySurgicalInterventionCat(surgicalInterventionCat);
        else
            return surgicalInterventionService.getSurgicalInterventionsByDurationAndSurgicalInterventionCat(duration, surgicalInterventionCat);
    }

    @PostMapping(value = "/surgical-intervention",headers = "Accept=application/json")
    public ResponseEntity<?> setSurgicalIntervention(@Valid @RequestBody SurgicalIntervention surgicalIntervention){
        return surgicalInterventionService.setSurgicalIntervention(surgicalIntervention);
    }

    @PutMapping(value = "/surg-intervention/{idSurgicalIntervention}")
    public ResponseEntity updateSurgicalIntervention(@PathVariable int idSurgicalIntervention, @Valid @RequestBody SurgicalIntervention newSurgicalIntervention){
        return surgicalInterventionService.updateSurgicalIntervention(idSurgicalIntervention, newSurgicalIntervention);
    }

    @DeleteMapping(value = "/surgical-interv/{idSurgicalIntervention}")
    public ResponseEntity<?> deleteSurgicalIntervention(@PathVariable int idSurgicalIntervention){
        return surgicalInterventionService.deleteSurgicalIntervention(idSurgicalIntervention);
    }

}