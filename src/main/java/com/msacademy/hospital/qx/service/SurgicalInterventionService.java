package com.msacademy.hospital.qx.service;


import com.msacademy.hospital.qx.model.entity.Response;
import com.msacademy.hospital.qx.model.entity.SurgicalIntervention;
import com.msacademy.hospital.qx.model.repository.BinnacleRepository;
import com.msacademy.hospital.qx.model.repository.BiopsyRepository;
import com.msacademy.hospital.qx.model.repository.SurgicalInterventionRepository;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@DefaultProperties(
        threadPoolProperties = {
                @HystrixProperty( name = "coreSize", value = "10" ),
                @HystrixProperty( name = "maxQueueSize", value = "15"),
                @HystrixProperty( name = "queueSizeRejectionThreshold", value = "100")},
        commandProperties = {
                @HystrixProperty( name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000"),
                @HystrixProperty( name = "circuitBreaker.requestVolumeThreshold", value = "10"),
                @HystrixProperty( name = "circuitBreaker.errorThresholdPercentage", value = "50"),
                @HystrixProperty( name = "circuitBreaker.sleepWindowInMilliseconds", value = "6000"),
                @HystrixProperty( name = "metrics.rollingStats.timeInMilliseconds", value = "6000"),
                @HystrixProperty( name = "metrics.rollingStats.numBuckets", value = "10") } )
public class SurgicalInterventionService {

    static final String httpsSurgicalInterventionCat = "https://qx-catalogue.herokuapp.com/surgeries/";
    static final String httpsImagingResult = "http://msacademy-studyrecords.herokuapp.com/imaging/studyRecord/";

    @Autowired
    private SurgicalInterventionRepository surgicalInterventionRepository;

    @Autowired
    private BiopsyRepository biopsyRepository;

    @Autowired
    private BinnacleRepository binnacleRepository;

    //////////////////////////////GET SURGICAL INTERVENTIONS BY ID///////////////////////////////////////////
    @HystrixCommand(fallbackMethod = "getOneSurgicalInterventionFallback", threadPoolKey = "getThreadPool")
    public ResponseEntity getSurgicalInterventionById(int idSurgicalIntervention) {
        try {
            SurgicalIntervention surgicalIntervention = surgicalInterventionRepository.findById(idSurgicalIntervention);
            if (surgicalIntervention == null) {
                return new ResponseEntity<>(
                        new Response(
                                HttpStatus.NOT_FOUND, "Cannot find Surgical Intervention with id: " + idSurgicalIntervention), HttpStatus.NOT_FOUND);
            } else
                return new ResponseEntity(surgicalIntervention, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new Response(
                            HttpStatus.BAD_REQUEST, "An error has been occurred."), HttpStatus.BAD_REQUEST);
        }
    }

    //////////////////////////////GET All SURGICAL INTERVENTIONS///////////////////////////////////////////
    @HystrixCommand(fallbackMethod = "getSurgicalInterventionsFallback", threadPoolKey = "getAllThreadPool")
    public ResponseEntity getAllSurgicalInterventions() {
        try {
            List<SurgicalIntervention> surgicalInterventions = surgicalInterventionRepository.findAll();
            if (surgicalInterventions.isEmpty()) {
                return new ResponseEntity<>(
                        new Response(
                                HttpStatus.NOT_FOUND, "No Surgical Interventions can be found"), HttpStatus.NOT_FOUND);
            } else
                return new ResponseEntity(surgicalInterventions, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new Response(
                            HttpStatus.BAD_REQUEST, "An error has been occurred."), HttpStatus.BAD_REQUEST);
        }
    }

    //////////////////////////////GET SURGICAL INTERVENTIONS BY DURATION///////////////////////////////////////////
    @HystrixCommand(fallbackMethod = "getSurgicalInterventionsFallback", threadPoolKey = "getAllThreadPool")
    public ResponseEntity getSurgicalInterventionsByDuration(Optional<String> duration) {
        int dur = Integer.parseInt(Integer.valueOf(duration.get()).toString());
        try {
            List<SurgicalIntervention> surgicalInterventions = surgicalInterventionRepository.findByDuration(dur);
            if (surgicalInterventions.isEmpty()) {
                return new ResponseEntity<>(
                        new Response(
                                HttpStatus.NOT_FOUND, "Cannot find Surgical Interventions with duration: " + dur), HttpStatus.NOT_FOUND);
            }else
                return new ResponseEntity(surgicalInterventions, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new Response(
                            HttpStatus.BAD_REQUEST, "An error has been occurred."), HttpStatus.BAD_REQUEST);
        }
    }

    //////////////////////////////GET SURGICAL INTERVENTIONS BY SURGICAL INTERVENTION CAT///////////////////////////////////////////
    @HystrixCommand(fallbackMethod = "getSurgicalInterventionsFallback", threadPoolKey = "getAllThreadPool")
    public ResponseEntity getSurgicalInterventionsBySurgicalInterventionCat(Optional<String> surgicalInterventionCat) {
        String https = httpsSurgicalInterventionCat + surgicalInterventionCat.get().toString();
        try {
            List<SurgicalIntervention> surgicalInterventions = surgicalInterventionRepository.findBySurgicalInterventionCat(https);
            if (surgicalInterventions.isEmpty()) {
                return new ResponseEntity<>(
                        new Response(
                                HttpStatus.NOT_FOUND, "Cannot find Surgical Interventions with surgicalInterventionCat: " + surgicalInterventionCat.get().toString()), HttpStatus.NOT_FOUND);
            }else {
                return new ResponseEntity(surgicalInterventions, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new Response(
                            HttpStatus.BAD_REQUEST, "An error has been occurred."), HttpStatus.BAD_REQUEST);
        }
    }

    //////////////////////////////GET SURGICAL INTERVENTIONS BY DURATION AND SURGICAL INTERVENTION CAT///////////////////////////////////////////
    @HystrixCommand(fallbackMethod = "getSurgicalInterventionsFallback", threadPoolKey = "getAllThreadPool")
    public ResponseEntity getSurgicalInterventionsByDurationAndSurgicalInterventionCat(Optional<String> duration, Optional<String> surgicalInterventionCat) {
        int dur = Integer.parseInt(Integer.valueOf(duration.get()).toString());
        String https = httpsSurgicalInterventionCat + surgicalInterventionCat.get().toString();
        try {
            List<SurgicalIntervention> surgicalInterventions = surgicalInterventionRepository.findByDurationAndSurgicalInterventionCat(dur, https);
            if (surgicalInterventions.isEmpty()) {
                return new ResponseEntity<>(
                        new Response(
                                HttpStatus.NOT_FOUND, "Cannot find Surgical Interventions with duration: " + dur + " & surgicalInterventionCat: " + surgicalInterventionCat.get().toString()), HttpStatus.NOT_FOUND);
            } else
                return new ResponseEntity(surgicalInterventions, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new Response(
                            HttpStatus.BAD_REQUEST, "An error has been occurred."), HttpStatus.BAD_REQUEST);
        }
    }

    @HystrixCommand(fallbackMethod = "setSurgicalInterventionFallback", threadPoolKey = "writeThreadPool")
    public ResponseEntity<?> setSurgicalIntervention(SurgicalIntervention surgicalIntervention) {
        try {
            surgicalIntervention.setSurgicalInterventionCat(httpsSurgicalInterventionCat + surgicalIntervention.getSurgicalInterventionCat());
            surgicalIntervention.setImagingResult(httpsImagingResult + surgicalIntervention.getImagingResult());
            surgicalInterventionRepository.save(surgicalIntervention);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new Response(
                            HttpStatus.BAD_REQUEST, "An error has been occurred."), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(
                new Response(
                        HttpStatus.CREATED, "Surgical Intervention added successfully."), HttpStatus.CREATED);
    }

    @HystrixCommand(fallbackMethod = "updateSurgicalInterventionFallback", threadPoolKey = "putThreadPool")
    public ResponseEntity updateSurgicalIntervention(int idSurgicalIntervention, SurgicalIntervention newSurgicalIntervention) {
        SurgicalIntervention oldSurgicalIntervention = surgicalInterventionRepository.findById(idSurgicalIntervention);
        if (oldSurgicalIntervention == null) {
            return new ResponseEntity<>(
                    new Response(
                            HttpStatus.NOT_FOUND, "Cannot find Surgical Intervention with id: " + idSurgicalIntervention), HttpStatus.NOT_FOUND);
        }
        try {
            surgicalInterventionRepository.save(updateSurgicalIntervention(oldSurgicalIntervention, newSurgicalIntervention));
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new Response(
                            HttpStatus.BAD_REQUEST, "Cannot upgrade Surgical Intervention with id: " + idSurgicalIntervention), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(
                new Response(
                        HttpStatus.ACCEPTED, "Surgical Intervention updated successfully"), HttpStatus.ACCEPTED);
    }

    @HystrixCommand(fallbackMethod = "deleteSurgicalInterventionFallback", threadPoolKey = "deleteThreadPool")
    public ResponseEntity<?> deleteSurgicalIntervention(int idSurgicalIntervention) {
        SurgicalIntervention surgicalIntervention = surgicalInterventionRepository.findById(idSurgicalIntervention);
        if (surgicalIntervention == null) {
            return new ResponseEntity<>(
                    new Response(
                            HttpStatus.NOT_FOUND, "Cannot find Surgical Intervention with id: " + idSurgicalIntervention), HttpStatus.NOT_FOUND);
        }
        try {
            surgicalInterventionRepository.delete(surgicalIntervention);
            biopsyRepository.delete(surgicalIntervention.getBiopsy());
            binnacleRepository.delete(surgicalIntervention.getBinnacle());
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new Response(
                            HttpStatus.BAD_REQUEST, "Cannot delete Surgical Intervention with id: " + idSurgicalIntervention), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(
                new Response(
                        HttpStatus.ACCEPTED, "Surgical Intervention deleted successfully"), HttpStatus.ACCEPTED);
    }

    private ResponseEntity getOneSurgicalInterventionFallback (int idSurgicalIntervention){
        return new ResponseEntity<>("Request fails. It takes long time to response", HttpStatus.REQUEST_TIMEOUT);
    }

    private ResponseEntity getSurgicalInterventionsFallback(){
        return new ResponseEntity<>("Request fails. It takes long time to response", HttpStatus.REQUEST_TIMEOUT);
    }

    private ResponseEntity setSurgicalInterventionFallback(SurgicalIntervention surgicalIntervention){
        return new ResponseEntity<>("Request fails. It takes long time to response", HttpStatus.REQUEST_TIMEOUT);
    }

    private ResponseEntity updateSurgicalInterventionFallback(int idSurgicalIntervention, SurgicalIntervention newSurgicalIntervention){
        return new ResponseEntity<>("Request fails. It takes long time to response", HttpStatus.REQUEST_TIMEOUT);
    }

    private ResponseEntity deleteSurgicalInterventionFallback(int idSurgicalIntervention){
        return new ResponseEntity<>("Request fails. It takes long time to response", HttpStatus.REQUEST_TIMEOUT);
    }

    private SurgicalIntervention updateSurgicalIntervention(SurgicalIntervention oldSurgicalIntervention, SurgicalIntervention newSurgicalIntervention) {

        if (newSurgicalIntervention.getInterventionProcedure() != null)
            oldSurgicalIntervention.setInterventionProcedure(newSurgicalIntervention.getInterventionProcedure());
        if (newSurgicalIntervention.getDuration() > 0)
            oldSurgicalIntervention.setDuration(newSurgicalIntervention.getDuration());

        return oldSurgicalIntervention;
    }
}
