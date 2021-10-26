package com.backendigans.demo.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "cadena")
public class Cadena {
    private int cadenaID;
    private String nombre;

    @OneToMany (mappedBy = "cadena")
    private List<Sucursal> sucursales;

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

    public void setCadenaID(int cadenaID) {
        this.cadenaID = cadenaID;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Sucursal> getSucursales(){
        return sucursales;
    }

    public void setSucursales(List<Sucursal> sucursales){
        this.sucursales = sucursales;
    }
}
