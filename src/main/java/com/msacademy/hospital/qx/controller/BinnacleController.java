package com.msacademy.hospital.qx.controller;

import com.msacademy.hospital.qx.model.entity.Binnacle;
import com.msacademy.hospital.qx.model.repository.BinnacleRepository;
import com.msacademy.hospital.qx.model.entity.Response;
import com.msacademy.hospital.qx.service.BinnacleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class BinnacleController {

    @Autowired
    private BinnacleService binnacleService;

    @GetMapping(value = "binnacle/{idBinnacle}")
    public ResponseEntity getBinnacle(@PathVariable(value = "idBinnacle") int idBinnacle){
        return binnacleService.getBinnacle(idBinnacle);
    }

    @GetMapping(value = "binnacles")
    public ResponseEntity getBinnacles(){
        return binnacleService.getBinnacles();
    }

    @PutMapping(value = "binnacle/{idBinnacle}")
    public ResponseEntity updateBinnacle(@PathVariable int idBinnacle, @Valid @RequestBody Binnacle newBinnacle){
        return binnacleService.updateBinnacle(idBinnacle, newBinnacle);
    }

}