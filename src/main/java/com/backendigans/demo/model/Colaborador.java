package com.backendigans.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "colaborador")
public class Colaborador {
    private String email, contrasena, nickname;
    private int puntos, reputacion;

    public Colaborador(){
    }

    public Colaborador (String email, String contrasena, String nickname, int puntos, int reputacion){
        this.email = email;
        this.contrasena = contrasena;
        this.nickname = nickname;
        this.puntos = puntos;
        this.reputacion = reputacion;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    public String getEmail() {
        return email;
    }
    public String getContrasena() {
        return contrasena;
    }
    public String getNickname() {
        return nickname;
    }
    public int getPuntos() {
        return puntos;
    }
    public int getReputacion() {
        return reputacion;
    }
    
}
