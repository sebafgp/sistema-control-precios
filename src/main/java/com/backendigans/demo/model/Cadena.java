package com.backendigans.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cadena")
public class Cadena {
    private int cadenaID;
    private String nombre;

    public Cadena(){
    }

    public Cadena (int cadenaID, String nombre){
        this.cadenaID = cadenaID;
        this.nombre = nombre;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    public int getCadenaID() {
        return cadenaID;
    }

    public String getNombre() {
        return nombre;
    }
}
