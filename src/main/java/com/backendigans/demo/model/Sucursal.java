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
        return sucursalID;
    }

    public int getCadenaID(){
        return cadenaID;
    }

    public String getCiudad(){
        return ciudad;
    }

    public String getCalle(){
        return calle;
    }

    public int getNumero(){
        return numero;
    }
}
