package com.backendigans.demo.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

@Entity
@Table(name = "colaborador")
public class Colaborador {
    private int colaboradorID;
    private String email;
    private String contrasena;
    private String nickname;
    private int puntos;
    private int reputacion;

    @ManyToMany (mappedBy = "colaboradores")
	private List <Producto> productos;

    @ManyToMany (cascade = {CascadeType.ALL})
    @JoinTable(
        name = "colaborador_recompensa",
        joinColumns = {@JoinColumn(name = "colaboradorID")},
        inverseJoinColumns = {@JoinColumn(name = "recompensaID")}
    )
    private List<Recompensa> recompensas;

    public Colaborador(){
    }

    public Colaborador (int colaboradorID, String email, String contrasena, String nickname, int puntos, int reputacion){
        this.colaboradorID = colaboradorID;
        this.email = email;
        this.contrasena = contrasena;
        this.nickname = nickname;
        this.puntos = puntos;
        this.reputacion = reputacion;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getColaboradorID(){
        return this.colaboradorID;
    }

    public void setColaboradorID(int idColaborador){
        this.colaboradorID = idColaborador;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasena() {
        return this.contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getPuntos() {
        return this.puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public int getReputacion() {
        return this.reputacion;
    }

    public void setReputacion(int reputacion) {
        this.reputacion = reputacion;
    }

    public List<Producto> getProductos() {
        return this.productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public List<Recompensa> getRecompensas() {
        return this.recompensas;
    }

    public void setRecompensas(List<Recompensa> recompensas) {
        this.recompensas = recompensas;
    }
    
}