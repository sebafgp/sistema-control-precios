package com.backendigans.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "recompensa")
public class Recompensa {
    private int recompensaID;
    private String nombre;
    private int costo;
    private int stock;
    private String descripcion;

    public Recompensa(){

    }

    public Recompensa(int recompensaID, String nombre, int costo, int stock, String descripcion){
        this.recompensaID = recompensaID;
        this.nombre = nombre;
        this.costo = costo;
        this.stock = stock;
        this.descripcion = descripcion;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    public int getRecompensaID(){
        return recompensaID;
    }

    public String getNombre(){
        return nombre;
    }

    public int getCosto(){
        return costo;
    }

    public int getStock(){
        return stock;
    }

    public String getDescripcion(){
        return descripcion;
    }
}
