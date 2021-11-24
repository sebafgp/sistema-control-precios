package com.backendigans.Sistema_Control_De_Precios.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name = "recompensa")
public class Recompensa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int recompensaID;
    private String nombre;
    private int costo;
    private int stock;
    private String descripcion;


	@JsonView(Vista.Recompensa.class)
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "recompensas")
	private Set<Colaborador> colaboradores = new HashSet<>();

    public Recompensa(){

    }

    public Recompensa(int recompensaID, String nombre, int costo, int stock, String descripcion){
        this.recompensaID = recompensaID;
        this.nombre = nombre;
        this.costo = costo;
        this.stock = stock;
        this.descripcion = descripcion;
    }

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

    public Set<Colaborador> getColaboradores() {
        return this.colaboradores;
    }

    public void setColaboradores(Set<Colaborador> colaboradores) {
        this.colaboradores = colaboradores;
    }

}
