package com.msacademy.hospital.qx.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "binnacle")
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"}, allowGetters = true)
public class Binnacle {
    @Id
    @Column(name = "id_binnacle")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idBinnacle;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date entryTimeMedicalStaff;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date entryTimePatient;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date departureTimeMedicalStaff;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date departureTimePatient;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date startTimeSurgery;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date endTimeSurgery;

    public int getIdBinnacle() {
        return idBinnacle;
    }

    public void setIdBinnacle(int idBinnacle) {
        this.idBinnacle = idBinnacle;
    }

    public Date getEntryTimeMedicalStaff() {
        return entryTimeMedicalStaff;
    }

    public void setEntryTimeMedicalStaff(Date entryTimeMedicalStaff) {
        this.entryTimeMedicalStaff = entryTimeMedicalStaff;
    }

    public Date getEntryTimePatient() {
        return entryTimePatient;
    }

    public void setEntryTimePatient(Date entryTimePatient) {
        this.entryTimePatient = entryTimePatient;
    }

    public Date getDepartureTimeMedicalStaff() {
        return departureTimeMedicalStaff;
    }

    public void setDepartureTimeMedicalStaff(Date departureTimeMedicalStaff) {
        this.departureTimeMedicalStaff = departureTimeMedicalStaff;
    }

    public Date getDepartureTimePatient() {
        return departureTimePatient;
    }

    public void setDepartureTimePatient(Date departureTimePatient) {
        this.departureTimePatient = departureTimePatient;
    }

    public Date getStartTimeSurgery() {
        return startTimeSurgery;
    }

    public void setStartTimeSurgery(Date startTimeSurgery) {
        this.startTimeSurgery = startTimeSurgery;
    }

    public Date getEndTimeSurgery() {
        return endTimeSurgery;
    }

    public void setEndTimeSurgery(Date endTimeSurgery) {
        this.endTimeSurgery = endTimeSurgery;
    }

}