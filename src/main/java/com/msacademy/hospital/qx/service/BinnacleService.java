package com.msacademy.hospital.qx.service;

import com.msacademy.hospital.qx.model.entity.Binnacle;
import com.msacademy.hospital.qx.model.entity.Response;
import com.msacademy.hospital.qx.model.repository.BinnacleRepository;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
public class BinnacleService {

    @Autowired
    private BinnacleRepository binnacleRepository;

    @HystrixCommand(fallbackMethod = "getOneBinnacleFallback", threadPoolKey = "getThreadPool")
    public ResponseEntity getBinnacle(int idBinnacle){
        try{
            Binnacle binnacle = binnacleRepository.findById(idBinnacle);
            if(binnacle == null){
                return new ResponseEntity<>(
                        new Response(
                                HttpStatus.NOT_FOUND,"Cannot find Binnacle with id: " + idBinnacle), HttpStatus.NOT_FOUND);
            }else
                return new ResponseEntity(binnacle, HttpStatus.OK);

        }
        catch(Exception e){
            return new ResponseEntity<>(
                    new Response(
                            HttpStatus.BAD_REQUEST ,"An error has been occurred."), HttpStatus.BAD_REQUEST);
        }
    }

    @HystrixCommand(fallbackMethod = "getBinnaclesFallback", threadPoolKey = "getThreadPool")
    public ResponseEntity getBinnacles(){
        try{
            List<Binnacle> binnacles = binnacleRepository.findAll();
            if(binnacles.isEmpty()){
                return new ResponseEntity<>(
                        new Response(
                                HttpStatus.NOT_FOUND,"No Binnacles can be found"), HttpStatus.NOT_FOUND);
            }else
                return new ResponseEntity(binnacles, HttpStatus.OK);

        }
        catch(Exception e){
            return new ResponseEntity<>(
                    new Response(
                            HttpStatus.BAD_REQUEST ,"An error has been occurred."), HttpStatus.BAD_REQUEST);
        }
    }

    @HystrixCommand(fallbackMethod = "updateBinnacleFallback", threadPoolKey = "putThreadPool")
    public ResponseEntity updateBinnacle(int idBinnacle, Binnacle newBinnacle){
        Binnacle oldBinnacle = binnacleRepository.findById(idBinnacle);
        if(oldBinnacle == null){
            return new ResponseEntity<>(
                    new Response(
                            HttpStatus.NOT_FOUND,"Cannot find Binnacle with id: " + idBinnacle), HttpStatus.NOT_FOUND);
        }
        try{
            binnacleRepository.save(updateBinnacle(oldBinnacle, newBinnacle));
        }catch(Exception e){
            return new ResponseEntity<>(
                    new Response(
                            HttpStatus.BAD_REQUEST,"Cannot upgrade Binnacle with id: " + idBinnacle), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(
                new Response(
                        HttpStatus.ACCEPTED ,"Binnacle updated successfully"), HttpStatus.ACCEPTED);
    }

    //  --------   Fall back ------------------
    public ResponseEntity getOneBinnacleFallback (int idBinnacle){
        return new ResponseEntity<>("Request fails. It takes long time to response", HttpStatus.REQUEST_TIMEOUT);
    }

    public ResponseEntity getBinnaclesFallback (){
        return new ResponseEntity<>("Request fails. It takes long time to response", HttpStatus.REQUEST_TIMEOUT);
    }

    public ResponseEntity updateBinnacleFallback (int idBinnacle, Binnacle newBinnacle){
        return new ResponseEntity<>("Request fails. It takes long time to response", HttpStatus.REQUEST_TIMEOUT);
    }

    // ----------  Update  --------------------
    private Binnacle updateBinnacle(Binnacle oldBinnacle, Binnacle newBinnacle){
        if(newBinnacle.getEntryTimeMedicalStaff() != null)
            oldBinnacle.setEntryTimeMedicalStaff(newBinnacle.getEntryTimeMedicalStaff());
        if(newBinnacle.getEntryTimePatient() != null)
            oldBinnacle.setEntryTimePatient(newBinnacle.getEntryTimePatient());
        if(newBinnacle.getDepartureTimeMedicalStaff() != null)
            oldBinnacle.setDepartureTimeMedicalStaff(newBinnacle.getDepartureTimeMedicalStaff());
        if(newBinnacle.getDepartureTimePatient() != null)
            oldBinnacle.setDepartureTimePatient(newBinnacle.getDepartureTimePatient());
        if(newBinnacle.getStartTimeSurgery() != null)
            oldBinnacle.setStartTimeSurgery(newBinnacle.getStartTimeSurgery());
        if(newBinnacle.getEndTimeSurgery() != null)
            oldBinnacle.setEndTimeSurgery(newBinnacle.getEndTimeSurgery());
        return oldBinnacle;
    }

}
