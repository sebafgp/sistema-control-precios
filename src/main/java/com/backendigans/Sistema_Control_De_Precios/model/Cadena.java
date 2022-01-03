package com.backendigans.Sistema_Control_De_Precios.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name = "cadena")
public class Cadena {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cadenaID;
    private String nombre;

    @JsonView(Vista.Cadena.class)
    @OneToMany (mappedBy = "cadena")
    private Set<Sucursal> sucursales = new HashSet<>();

    public Cadena(){
    }

    public Cadena (int cadenaID, String nombre){
        this.cadenaID = cadenaID;
        this.nombre = nombre;
    }
  
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

    public Set<Sucursal> getSucursales(){
        return sucursales;
    }

    public void setSucursales(Set<Sucursal> sucursales){
        this.sucursales = sucursales;
    }

    public boolean addSucursal(Sucursal sucursal){
        return sucursales.add(sucursal);
    }
}
