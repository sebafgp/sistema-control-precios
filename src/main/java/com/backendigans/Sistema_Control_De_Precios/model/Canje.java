package com.backendigans.Sistema_Control_De_Precios.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "canje")
public class Canje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int canjeID;
    @Column(name = "fechaCanje")
    private LocalDateTime fechaCanje;

    @ManyToOne
    @JoinColumn(name = "colaboradorID" , referencedColumnName = "colaboradorID")
    private Colaborador colaborador;

    @ManyToOne
    @JoinColumn(name = "recompensaID" , referencedColumnName = "recompensaID")
    private Recompensa recompensa;

    public Canje() {
    }

    public Canje(int canjeID, LocalDateTime fechaCanje, Colaborador colaborador, Recompensa recompensa) {
        this.canjeID = canjeID;
        this.fechaCanje = fechaCanje;
        this.colaborador = colaborador;
        this.recompensa = recompensa;
    }

    public Canje(Colaborador colaborador, Recompensa recompensa) {
        this.colaborador = colaborador;
        this.recompensa = recompensa;
        this.fechaCanje = LocalDateTime.now();
    }

    public int getCanjeID() {
        return this.canjeID;
    }

    public void setCanjeID(int canjeID) {
        this.canjeID = canjeID;
    }

    public LocalDateTime getFechaCanje() {
        return this.fechaCanje;
    }

    public void setFechaCanje(LocalDateTime fechaCanje) {
        this.fechaCanje = fechaCanje;
    }

    public Colaborador getColaborador() {
        return this.colaborador;
    }

    public void setColaborador(Colaborador colaborador) {
        this.colaborador = colaborador;
    }

    public Recompensa getRecompensa() {
        return this.recompensa;
    }

    public void setRecompensa(Recompensa recompensa) {
        this.recompensa = recompensa;
    }

}
