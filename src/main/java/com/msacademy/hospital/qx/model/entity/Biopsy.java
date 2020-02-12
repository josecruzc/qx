package com.msacademy.hospital.qx.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "biopsy")
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"}, allowGetters = true)
public class Biopsy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_biopsy")
    private int idBiopsy;

    private String type;
    private String description;

    public int getIdBiopsy() {
        return idBiopsy;
    }

    public void setIdBiopsy(int idBiopsy) {
        this.idBiopsy = idBiopsy;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}