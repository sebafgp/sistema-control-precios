package com.backendigans.Sistema_Control_De_Precios.model;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.beans.factory.annotation.Value;

@Entity
@Table(name = "actualizacion")
@IdClass(ActualizacionID.class)
public class Actualizacion {

    @Id
    @ManyToOne
    @JoinColumn(name = "colaboradorID", referencedColumnName = "colaboradorID")
    private Colaborador colaborador;

    @Id
    @ManyToOne
    @JoinColumn(name = "productoID", referencedColumnName = "productoID")
    private Producto producto;

    private int precio;

    @Column(insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    Date fecha_actualizacion;


    @Value("${some.key:0}")
    private int valoracion;


    public Actualizacion(){

    }

    public Actualizacion (Colaborador colaborador, Producto producto, int precio, Date fecha_actualizacion, int valoracion){
        this.colaborador = colaborador;
        this.producto = producto;
        this.precio = precio;
        this.fecha_actualizacion = fecha_actualizacion;
        this.valoracion = valoracion;
    }

    public Colaborador getColaborador() {
        return colaborador;
    }
    public Producto getProducto() {
        return producto;
    }
    public int getPrecio() {
        return precio;
    }
    public int getValoracion() {
        return valoracion;
    }
    public void setColaborador(Colaborador colaborador) {
        this.colaborador = colaborador;
    }
    public void setProducto(Producto producto) {
        this.producto = producto;
    }
    public void setPrecio(int precio) {
        this.precio = precio;
    }
    public void setValoracion(int valoracion) {
        this.valoracion = valoracion;
    }

}
