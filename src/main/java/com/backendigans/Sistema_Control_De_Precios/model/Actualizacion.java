package com.backendigans.Sistema_Control_De_Precios.model;

import java.time.LocalDateTime;
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
public class Actualizacion {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int actualizacionID;

    @ManyToOne
    @JoinColumn(name = "colaboradorID", referencedColumnName = "colaboradorID")
    private Colaborador colaborador;

    @ManyToOne
    @JoinColumn(name = "productoID", referencedColumnName = "productoID")
    private Producto producto;

    private int precio;

    private LocalDateTime fechaActualizacion;


    @Value("${some.key:0}")
    private int valoracion;


    public Actualizacion(){

    }

    public Actualizacion (int actualizacionID, Colaborador colaborador, Producto producto, int precio, LocalDateTime fechaActualizacion, int valoracion){

        this.actualizacionID = actualizacionID;
        this.colaborador = colaborador;
        this.producto = producto;
        this.precio = precio;
        this.fechaActualizacion = fechaActualizacion;
        this.valoracion = valoracion;
    }

    public Actualizacion (Colaborador colaborador, Producto producto, int precio){

        this.actualizacionID = 0;
        this.colaborador = colaborador;
        this.producto = producto;
        this.precio = precio;
        this.fechaActualizacion = LocalDateTime.now();
        this.valoracion = 0;
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
    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
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

	public void addPuntos(int i) {
        this.valoracion = this.valoracion + i;
	}

}
