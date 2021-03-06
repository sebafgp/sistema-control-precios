package com.backendigans.Sistema_Control_De_Precios.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.persistence.criteria.Join;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

import org.springframework.beans.factory.annotation.Value;

@Entity
@Table(name = "colaborador")
public class Colaborador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @JsonView(Vista.Colaborador.class)
    @OneToMany (mappedBy = "colaborador")
	private Set <Producto> productos = new HashSet<>();

   /* @JsonView(Vista.Colaborador.class)
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "colaborador_recompensa",
        joinColumns = {@JoinColumn(name = "colaboradorID")},
        inverseJoinColumns = {@JoinColumn(name = "recompensaID")}
    )
    private Set<Recompensa> recompensas = new HashSet<>(); */
    
    @JsonIgnore
    @OneToMany(mappedBy = "colaborador")
    private Set<Actualizacion> actualizaciones = new HashSet<>();

    @OneToMany(mappedBy = "colaborador")
    private Set<Canje> canjes = new HashSet<>();

    public Colaborador(){
    }

    public Colaborador(String email, String contrasena, String nickname) {
        this.email = email;
        this.contrasena = contrasena;
        this.nickname = nickname;
        this.puntos = 0;
        this.reputacion = 0;
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

    @JsonProperty
    public int getPuntos() {
        return this.puntos;
    }

    @JsonIgnore
    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public void addPuntos(int puntosAdicionales){
        if (puntosAdicionales < 0) {
            return;
        }
        this.puntos = this.puntos + puntosAdicionales;
    }

    @JsonProperty
    public int getReputacion() {
        return this.reputacion;
    }

    @JsonIgnore
    public void setReputacion(int reputacion) {
        this.reputacion = reputacion;
    }

    public Set<Producto> getProductos() {
        return this.productos;
    }

    public void setProductos(Set<Producto> productos) {
        this.productos = productos;
    }

    /*public Set<Recompensa> getRecompensas() {
        return this.recompensas;
    }

    public void setRecompensas(Set<Recompensa> recompensas) {
        this.recompensas = recompensas;
    } */
    public Set<Actualizacion> getActualizaciones() {
        return actualizaciones;
    }
    public void setActualizacion(Set<Actualizacion> actualizaciones) {
        this.actualizaciones = actualizaciones;
    }    
    public Set<Canje> getCanjes() {
        return canjes;
    }
    public void setCanjes(Set<Canje> canjes) {
        this.canjes = canjes;
    }    
    public void addCanje(Canje canje) {
        this.canjes.add(canje);
    }

    public void disminuirPuntos(int i) {
        this.puntos = this.puntos-i;
    }

    public boolean addProducto(Producto producto){
        return productos.add(producto);
    }
}
