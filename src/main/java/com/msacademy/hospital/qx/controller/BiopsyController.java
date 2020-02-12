package com.msacademy.hospital.qx.controller;

import com.msacademy.hospital.qx.model.entity.Biopsy;
import com.msacademy.hospital.qx.model.repository.BiopsyRepository;
import com.msacademy.hospital.qx.model.entity.Response;
import com.msacademy.hospital.qx.service.BiopsyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class BiopsyController {

    @Autowired
    private BiopsyService biopsyService;

    @GetMapping(value = "biopsy/{idBiopsy}")
    public ResponseEntity getBiopsy(@PathVariable(value = "idBiopsy") int idBiopsy){
        return biopsyService.getBiopsy(idBiopsy);
    }

    @GetMapping(value = "biopsies")
    public ResponseEntity getBiopsies(){
        return biopsyService.getBiopsies();
    }

    @PutMapping(value = "biopsy/{idBiopsy}")
    public ResponseEntity updateBiopsy(@PathVariable int idBiopsy, @Valid @RequestBody Biopsy newBiopsy){
        return biopsyService.updateBiopsy(idBiopsy, newBiopsy);
    }

}