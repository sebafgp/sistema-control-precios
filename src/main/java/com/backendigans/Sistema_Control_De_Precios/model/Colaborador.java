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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.criteria.Join;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.springframework.beans.factory.annotation.Value;

import javax.persistence.JoinColumn;

@Entity
@Table(name = "colaborador")
public class Colaborador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnoreProperties(ignoreUnknown = true, 
                          value = {"productos", "recompensas"})
    private int colaboradorID;
    @Column(name = "email")
    private String email;
    @Column(name = "contrasena")
    private String contrasena;
    @Column(name = "nickname")
    private String nickname;
    @Column(name = "puntos")
    @Value("${some.key:0}")
    @JsonIgnore
    private int puntos;
    @Column(name = "reputacion")
    @Value("${some.key:0}")
    @JsonIgnore
    private int reputacion;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "colaboradores")
	private Set <Producto> productos = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "colaborador_recompensa",
        joinColumns = {@JoinColumn(name = "colaboradorID")},
        inverseJoinColumns = {@JoinColumn(name = "recompensaID")}
    )
    private Set<Recompensa> recompensas = new HashSet<>();

    @OneToMany(mappedBy = "colaborador")
    private Set<Actualizacion> actualizacion = new HashSet<>();

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

    public Set<Producto> getProductos() {
        return this.productos;
    }

    public void setProductos(Set<Producto> productos) {
        this.productos = productos;
    }

    public Set<Recompensa> getRecompensas() {
        return this.recompensas;
    }

    public void setRecompensas(Set<Recompensa> recompensas) {
        this.recompensas = recompensas;
    }

    public Set<Actualizacion> getActualizacion() {
        return actualizacion;
    }
    public void setActualizacion(Set<Actualizacion> actualizacion) {
        this.actualizacion = actualizacion;
    }
    
}
