package com.msacademy.hospital.qx.service;

import com.msacademy.hospital.qx.model.entity.Biopsy;
import com.msacademy.hospital.qx.model.entity.Response;
import com.msacademy.hospital.qx.model.repository.BiopsyRepository;
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
public class BiopsyService {

    @Autowired
    private BiopsyRepository biopsyRepository;

    @HystrixCommand(fallbackMethod = "getOneBinnacleFallback", threadPoolKey = "getThreadPool")
    public ResponseEntity getBiopsy(int idBiopsy){
        try{
            Biopsy biopsy = biopsyRepository.findById(idBiopsy);
            if(biopsy == null){
                return new ResponseEntity<>(
                        new Response(
                                HttpStatus.NOT_FOUND,"Cannot find Biopsy with id: " + idBiopsy), HttpStatus.NOT_FOUND);
            }
            else
                return new ResponseEntity(biopsy, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(
                    new Response(
                            HttpStatus.BAD_REQUEST ,"An error has been occurred."), HttpStatus.BAD_REQUEST);
        }
    }

    @HystrixCommand(fallbackMethod = "getBiopsiesFallback", threadPoolKey = "getThreadPool")
    public ResponseEntity getBiopsies(){
        try{
            List<Biopsy> biopsies = biopsyRepository.findAll();
            if(biopsies.isEmpty()){
                return new ResponseEntity<>(
                        new Response(
                                HttpStatus.NOT_FOUND,"No Biopsies can be found"), HttpStatus.NOT_FOUND);
            }
            else
                return new ResponseEntity(biopsies, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(
                    new Response(
                            HttpStatus.BAD_REQUEST ,"An error has been occurred."), HttpStatus.BAD_REQUEST);
        }
    }

    @HystrixCommand(fallbackMethod = "updateBiopsyFallback", threadPoolKey = "putThreadPool")
    public ResponseEntity updateBiopsy(int idBiopsy, Biopsy newBiopsy){
        Biopsy oldBiopsy = biopsyRepository.findById(idBiopsy);
        if(oldBiopsy == null){
            return new ResponseEntity<>(
                    new Response(
                            HttpStatus.NOT_FOUND,"Cannot find Biopsy with id: " + idBiopsy), HttpStatus.NOT_FOUND);
        }
        try{
            biopsyRepository.save(updateBiopsy(oldBiopsy, newBiopsy));
        }catch(Exception e){
            return new ResponseEntity<>(
                    new Response(
                            HttpStatus.BAD_REQUEST,"Cannot upgrade Biopsy with id: " + idBiopsy), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(
                new Response(
                        HttpStatus.ACCEPTED ,"Biopsy updated successfully"), HttpStatus.ACCEPTED);
    }

    // -------- Fallback ---------------
    public ResponseEntity getOneBinnacleFallback (int idBiopsy){
        return new ResponseEntity<>("Request fails. It takes long time to response", HttpStatus.REQUEST_TIMEOUT);
    }

    public ResponseEntity getBiopsiesFallback (){
        return new ResponseEntity<>("Request fails. It takes long time to response", HttpStatus.REQUEST_TIMEOUT);
    }

    public ResponseEntity updateBiopsyFallback (int idBinnacle, Biopsy newBiopsy){
        return new ResponseEntity<>("Request fails. It takes long time to response", HttpStatus.REQUEST_TIMEOUT);
    }

    // -------- Update -----------------
    private Biopsy updateBiopsy(Biopsy oldBiopsy, Biopsy newBiopsy){

        if(newBiopsy.getType() != null)
            oldBiopsy.setType(newBiopsy.getType());
        if(newBiopsy.getDescription() != null)
            oldBiopsy.setDescription(newBiopsy.getDescription());

        return oldBiopsy;
    }

}
