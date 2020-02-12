package com.msacademy.hospital.qx.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "surgical_intervention")
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"}, allowGetters = true)
public class SurgicalIntervention {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idSurgicalIntervention;

    @Column(name = "intervention_procedure")
    private String interventionProcedure;

    private int duration;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="id_binnacle")
    private Binnacle binnacle;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_biopsy")
    private Biopsy biopsy;

    @JoinColumn(name="surgical_intervention_cat")
    private String surgicalInterventionCat;

    @JoinColumn(name="imaging_result")
    private String imagingResult;

    public int getIdSurgicalIntervention() {
        return idSurgicalIntervention;
    }

    public void setIdSurgicalIntervention(int idSurgicalIntervention) {
        this.idSurgicalIntervention = idSurgicalIntervention;
    }

    public String getInterventionProcedure() {
        return interventionProcedure;
    }

    public void setInterventionProcedure(String interventionProcedure) {
        this.interventionProcedure = interventionProcedure;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Binnacle getBinnacle() {
        return binnacle;
    }

    public void setBinnacle(Binnacle binnacle) {
        this.binnacle = binnacle;
    }

    public Biopsy getBiopsy() {
        return biopsy;
    }

    public void setBiopsy(Biopsy biopsy) {
        this.biopsy = biopsy;
    }

    public String getSurgicalInterventionCat() {
        return surgicalInterventionCat;
    }

    public void setSurgicalInterventionCat(String surgicalInterventionCat) {
        this.surgicalInterventionCat = surgicalInterventionCat;
    }

    public String getImagingResult() {
        return imagingResult;
    }

    public void setImagingResult(String imagingResult) {
        this.imagingResult = imagingResult;
    }
    
}