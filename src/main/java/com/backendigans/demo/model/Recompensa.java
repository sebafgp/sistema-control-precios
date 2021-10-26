package com.backendigans.demo.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "recompensa")
public class Recompensa {
    private int recompensaID;
    private String nombre;
    private int costo;
    private int stock;
    private String descripcion;

    @ManyToMany (mappedBy = "recompensas")
	private List <Colaborador> colaboradores;

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
    public int getRecompensaID() {
        return this.recompensaID;
    }

    public void setRecompensaID(int recompensaID) {
        this.recompensaID = recompensaID;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCosto() {
        return this.costo;
    }

    public void setCosto(int costo) {
        this.costo = costo;
    }

    public int getStock() {
        return this.stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Colaborador> getColaboradores() {
        return this.colaboradores;
    }

    public void setColaboradores(List<Colaborador> colaboradores) {
        this.colaboradores = colaboradores;
    }

}
