package com.backendigans.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sucursal")
public class Sucursal {
    private int sucursalID;
    private int cadenaID;
    private String ciudad;
    private String calle;
    private int numero;

    public Sucursal(){

    }

    public Sucursal(int sucursalID, int cadenaID, String ciudad, String calle, int numero){
        this.sucursalID = sucursalID;
        this.cadenaID = cadenaID;
        this.ciudad = ciudad;
        this.calle = calle;
        this.numero = numero;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    public int getSucursalID() {
        return this.sucursalID;
    }

    public void setSucursalID(int sucursalID) {
        this.sucursalID = sucursalID;
    }

    public int getCadenaID() {
        return this.cadenaID;
    }

    public void setCadenaID(int cadenaID) {
        this.cadenaID = cadenaID;
    }

    public String getCiudad() {
        return this.ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getCalle() {
        return this.calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public int getNumero() {
        return this.numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }
}
