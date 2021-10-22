package com.backendigans.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "colaborador")
public class Colaborador {
    private String email;
    private String contrasena;
    private String nickname;
    private int puntos;
    private int reputacion;

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
    
}